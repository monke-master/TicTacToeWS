package ui.host

import com.adeo.kviewmodel.BaseSharedViewModel
import data.ServerResponse
import domain.usecase.GenerateQrCodeUseCase
import domain.usecase.HostGameUseCase
import domain.usecase.OnOpponentLeftUseCase
import domain.usecase.StartGameUseCase
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
    private val onOpponentLeftUseCase: OnOpponentLeftUseCase by inject()


    override fun obtainEvent(viewEvent: HostGameEvent) {
        when (viewEvent) {
            HostGameEvent.CreateGame -> createGame()
            HostGameEvent.StartGame -> startGame()
        }
    }

    private fun createGame() {
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
                    }
                    is ServerResponse.Success -> {
                        val qrCode = generateQrCodeUseCase.execute(response.gameSession.code).getOrElse { error ->
                            viewState = HostGameState.Error(error)
                            return@collect
                        }
                        viewState = HostGameState.Success(response.gameSession, qrCode)
                    }
                }
            }
        }
    }

    private fun startGame() {
        viewModelScope.launch {
            val gameSession = (viewState as HostGameState.Success).session

            startGameUseCase.execute(gameSession).getOrElse {
                viewState = HostGameState.Error(it)
                return@launch
            }

            viewAction = HostGameAction.StartGameScreen
        }
    }

}
