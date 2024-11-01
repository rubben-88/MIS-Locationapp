package lt.ktu.locationapp.utils

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import lt.ktu.locationapp.data.local.ObservationEntity
import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.domain.models.SignalTriplet
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager


var deleteDBlock = false

suspend fun importCSVToDatabase(
    owner: LifecycleOwner,
    context: Context,
    dbViewModel: DatabaseManager
) {

    var counter: Int? = null

    // Clear all data
    dbViewModel.getAllObservations().observe(owner) {
        if (it != null) {
            if (!deleteDBlock) {
                counter = it.size
                it.forEach {
                    dbViewModel.deleteObservation(it.xy)
                    counter = counter!! - 1
                }
                deleteDBlock = true
            }
        }
    }

    while (counter == null || counter!! > 0) {
        delay(200)
    }

    // Read CSV file
    withContext(Dispatchers.IO) {

        val inputStream = context.assets.open("grid")
        val rows: List<Map<String, String>> = csvReader().readAllWithHeader(inputStream)

        rows.forEach { row ->
            val x = row["x"]?.toIntOrNull()!!
            val y = row["y"]?.toIntOrNull()!!
            val ss_s = row["signal_strengths"]!!
            val ss = ss_s.split('.').map { it.toIntOrNull()!! }

            // Insert into Room database
            val obs = ObservationEntity(
                x = x,
                y = y,
                s1 = ss[0],
                s2 = ss[1],
                s3 = ss[2]
            )
            dbViewModel.createObservation(
                Observation(
                    Location(obs.x, obs.y),
                    SignalTriplet(arrayOf(obs.s1, obs.s2, obs.s3))
                )
            )
        }
    }
}