package lt.ktu.locationapp.data.repository

import androidx.lifecycle.LiveData
import lt.ktu.locationapp.data.local.ObservationDao
import lt.ktu.locationapp.data.local.ObservationEntity

class ObservationRepository(private val observationDao: ObservationDao) {

    fun getObservation(x: Int, y: Int): LiveData<ObservationEntity?> {
        return observationDao.getObservation(x, y)
    }

    fun getAllObservations(): LiveData<List<ObservationEntity>> {
        return observationDao.getAllObservations()
    }

    fun updateObservation(observation: ObservationEntity) {
        observationDao.updateObservation(
            observation.x,
            observation.y,
            observation.s1,
            observation.s2,
            observation.s3
        )
    }

    fun newObservation(observation: ObservationEntity) {
        observationDao.newObservation(observation)
    }

    fun deleteObservation(x: Int, y: Int) {
        observationDao.deleteObservation(x, y)
    }

    suspend fun deleteAll() = observationDao.deleteAll()

}

