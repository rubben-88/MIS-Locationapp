package lt.ktu.locationapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ObservationEntity::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun observationDao(): ObservationDao
}