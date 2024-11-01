package lt.ktu.locationapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    // file located at C:\xampp\htdocs
    private const val BASE_URL = "http://10.155.3.40/locationapp/" /* http://10.0.2.2/locationapp/ */
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}