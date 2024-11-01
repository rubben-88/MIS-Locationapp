package lt.ktu.locationapp.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import lt.ktu.locationapp.data.repository.ObservationRepository
import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.domain.models.SignalTriplet

class GetObservationUseCase(private val repository: ObservationRepository) {
    fun execute(location: Location): LiveData<Observation> {
        return repository.getObservation(
            x = location.x,
            y = location.y
        ).map {
            Observation(
            xy = Location(it?.x ?: -100 , it?.y ?: -100),
            ss = SignalTriplet((arrayOf(it?.s1 ?: 0, it?.s2 ?: 0, it?.s3 ?: 0)))
        ) }
    }
}