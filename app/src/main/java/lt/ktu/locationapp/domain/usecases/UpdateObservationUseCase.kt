package lt.ktu.locationapp.domain.usecases

import lt.ktu.locationapp.data.local.ObservationEntity
import lt.ktu.locationapp.data.repository.ObservationRepository
import lt.ktu.locationapp.domain.models.Observation

class UpdateObservationUseCase(private val repository: ObservationRepository) {
    fun execute(observation: Observation) {
        repository.updateObservation(
            observation = ObservationEntity(
                x = observation.xy.x,
                y = observation.xy.y,
                s1 = observation.ss.get(0),
                s2 = observation.ss.get(1),
                s3 = observation.ss.get(2)
            )
        )
    }
}