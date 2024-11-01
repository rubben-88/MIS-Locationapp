package lt.ktu.locationapp.domain.usecases

import lt.ktu.locationapp.data.repository.ObservationRepository

class DeleteAllUseCase(private val repository: ObservationRepository) {
    suspend fun execute() = repository.deleteAll()
}