package lt.ktu.locationapp.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import lt.ktu.locationapp.data.repository.ObservationRepository
import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.domain.models.SignalTriplet

class GetAllObservationsUseCase(private val repository: ObservationRepository) {
    fun execute(): LiveData<List<Observation>> {
        val result = MediatorLiveData<List<Observation>>()

        result.addSource(repository.getAllObservations()) { observations ->
            result.value = observations.map { Observation(
                xy = Location(it.x, it.y),
                ss = SignalTriplet(arrayOf(it.s1, it.s2, it.s3))
            ) }
        }

        return result
    }
}