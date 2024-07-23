package ui.host

import com.adeo.kviewmodel.BaseSharedViewModel
import domain.HostGameUseCase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

private const val TAG = "GameRepositoryImpl"

class HostGameViewModel:
    BaseSharedViewModel<HostGameState, HostGameAction, HostGameEvent>(HostGameState.Idle), KoinComponent {

    private val hostGameUseCase: HostGameUseCase by inject()


    override fun obtainEvent(viewEvent: HostGameEvent) {
        when (viewEvent) {
            HostGameEvent.CreateGame -> createGame()
            HostGameEvent.StartGame -> {}
        }
    }

    private fun createGame() {
        viewModelScope.launch {
            viewState = HostGameState.Loading

            val flow = hostGameUseCase.execute().getOrElse {
                viewState = HostGameState.Error(it)
                logging(TAG).d { "error" }
                return@launch
            }
            logging(TAG).d { "subscribing" }
            flow.collect { session ->
                session?.let { viewState = HostGameState.Success(it) }
            }
        }
    }

    private fun startGame() {
        viewModelScope.launch {

        }
    }

}
