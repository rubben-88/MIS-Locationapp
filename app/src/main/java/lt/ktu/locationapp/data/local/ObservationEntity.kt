package lt.ktu.locationapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ObservationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val x: Int,
    val y: Int,
    val s1: Int,
    val s2: Int,
    val s3: Int
)