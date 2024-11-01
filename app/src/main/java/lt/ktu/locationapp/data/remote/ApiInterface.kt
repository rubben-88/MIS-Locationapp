package lt.ktu.locationapp.data.remote

import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @get:GET("observations.php")
    val observations: Call<List<RemoteObservationEntity>>
}