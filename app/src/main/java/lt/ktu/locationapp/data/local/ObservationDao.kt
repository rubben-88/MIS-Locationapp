package lt.ktu.locationapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ObservationDao {

    @Query("SELECT * FROM observationentity where x=:x AND y =:y")
    fun getObservation(x: Int, y: Int): LiveData<ObservationEntity?>

    @Query("SELECT * FROM observationentity")
    fun getAllObservations(): LiveData<List<ObservationEntity>>

    @Query("UPDATE observationentity SET s1=:s1, s2=:s2, s3=:s3 WHERE x=:x AND y=:y")
    fun updateObservation(x: Int, y: Int, s1: Int, s2: Int, s3: Int)

    @Insert
    fun newObservation(observation: ObservationEntity)

    @Query("DELETE FROM observationentity WHERE x=:x AND y=:y")
    fun deleteObservation(x: Int, y: Int)

    @Query("DELETE FROM observationentity")
    suspend fun deleteAll()

}