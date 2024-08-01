package domain.usecase

import domain.GameRepository

class GenerateQrCodeUseCase(
    private val gameRepository: GameRepository
) {

    suspend fun execute(code: String) = gameRepository.generateQr(code)

}