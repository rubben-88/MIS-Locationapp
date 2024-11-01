package lt.ktu.locationapp.domain.usecases

import lt.ktu.locationapp.data.repository.ObservationRepository
import lt.ktu.locationapp.domain.models.Location

class DeleteObservationUseCase(private val repository: ObservationRepository) {
    fun execute(location: Location) {
        repository.deleteObservation(location.x, location.y)
    }
}