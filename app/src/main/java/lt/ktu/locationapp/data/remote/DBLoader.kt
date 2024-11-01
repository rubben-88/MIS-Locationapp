package lt.ktu.locationapp.data.remote

import android.util.Log
import lt.ktu.locationapp.data.remote.ApiClient.client
import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.domain.models.SignalTriplet
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DBLoader(val dbManager: DatabaseManager) {

    fun execute() {
        val apiService = client!!.create(ApiService::class.java)
        val call: Call<List<RemoteObservationEntity>> = apiService.observations

        call.enqueue(object : Callback<List<RemoteObservationEntity>> {
            override fun onResponse(
                call: Call<List<RemoteObservationEntity>>,
                response: Response<List<RemoteObservationEntity>>
            ) {
                if (response.isSuccessful()) {
                    val measurements: List<RemoteObservationEntity?>? = response.body()
                    if (measurements != null) {
                        for (m in measurements) {
                            if (m != null) {
                                dbManager.createObservation(
                                    Observation(
                                        xy = Location(m.x, m.y),
                                        ss = SignalTriplet(arrayOf(m.s1, m.s2, m.s3))
                                    )
                                )
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<RemoteObservationEntity>>, t: Throwable) {
                Log.e("DBLoader", "Error! $t")
            }
        })
    }

}