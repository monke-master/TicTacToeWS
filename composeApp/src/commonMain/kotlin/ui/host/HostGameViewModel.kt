package ui.host

import com.adeo.kviewmodel.BaseSharedViewModel
import data.ServerResponse
import domain.models.GameSession
import domain.usecase.GenerateQrCodeUseCase
import domain.usecase.HostGameUseCase
import domain.usecase.OnOpponentLeftUseCase
import domain.usecase.QuitGameUseCase
import domain.usecase.StartGameUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private const val TAG = "GameRepositoryImpl"

class HostGameViewModel:
    BaseSharedViewModel<HostGameState, HostGameAction, HostGameEvent>(HostGameState.Idle), KoinComponent {

    private val hostGameUseCase: HostGameUseCase by inject()
    private val startGameUseCase: StartGameUseCase by inject()
    private val generateQrCodeUseCase: GenerateQrCodeUseCase by inject()
    private val quitGameUseCase: QuitGameUseCase by inject()
    private var lastRequest: (() -> Job)? = null
    private var session: GameSession? = null


    override fun obtainEvent(viewEvent: HostGameEvent) {
        when (viewEvent) {
            HostGameEvent.CreateGame -> lastRequest = createGame().apply { invoke() }
            HostGameEvent.StartGame -> lastRequest = startGame().apply { invoke() }
            HostGameEvent.QuitGame -> quitGame()
            HostGameEvent.RetryRequest -> lastRequest?.invoke()
        }
    }

    private fun createGame(): () -> Job = {
        viewModelScope.launch {
            viewState = HostGameState.Loading

            val flow = hostGameUseCase.execute().getOrElse {
                viewState = HostGameState.Error(it)

                return@launch
            }

            flow.collect { response ->
                if (response == null) return@collect
                when(response) {
                    is ServerResponse.Error -> {
                        viewAction = HostGameAction.ShowErrorDialog(
                            message = response.errorMessage,
                            onDismissed = { viewAction = HostGameAction.ExitScreen }
                        )
                        quitGameUseCase.execute()
                    }
                    is ServerResponse.Success -> {
                        val qrCode = generateQrCodeUseCase.execute(response.gameSession.code).getOrElse { error ->
                            viewState = HostGameState.Error(error)
                            return@collect
                        }
                        viewState = HostGameState.Success(response.gameSession, qrCode)
                        session = response.gameSession
                    }
                }
            }
        }
    }

    private fun startGame(): () -> Job = {
        viewModelScope.launch {
            val gameSession = session ?: return@launch

            startGameUseCase.execute(gameSession).getOrElse {
                viewState = HostGameState.Error(it)
                return@launch
            }

            viewAction = HostGameAction.StartGameScreen
        }
    }

    private fun quitGame() {
        viewModelScope.launch {
            quitGameUseCase.execute()
        }
    }

}
