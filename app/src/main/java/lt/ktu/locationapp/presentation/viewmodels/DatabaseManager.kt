package lt.ktu.locationapp.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lt.ktu.locationapp.data.remote.DBLoader
import lt.ktu.locationapp.domain.models.Location
import lt.ktu.locationapp.domain.models.Observation
import lt.ktu.locationapp.domain.models.SignalTriplet
import lt.ktu.locationapp.domain.usecases.CreateObservationUseCase
import lt.ktu.locationapp.domain.usecases.DeleteAllUseCase
import lt.ktu.locationapp.domain.usecases.DeleteObservationUseCase
import lt.ktu.locationapp.domain.usecases.GetAllObservationsUseCase
import lt.ktu.locationapp.domain.usecases.GetObservationUseCase
import lt.ktu.locationapp.domain.usecases.UpdateObservationUseCase

class DatabaseManager(
    private val getObservationUseCase: GetObservationUseCase,
    private val getAllObservationsUseCase: GetAllObservationsUseCase,
    private val createObservationUseCase: CreateObservationUseCase,
    private val deleteObservationUseCase: DeleteObservationUseCase,
    private val updateObservationUseCase: UpdateObservationUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
    private val context: Context
) : ViewModel() {

    private val dbLoader = DBLoader(this)

    suspend fun reloadDatabase(owner: LifecycleOwner) {


        deleteAllUseCase.execute()

        dbLoader.execute()

        //deleteDBlock = false
        //
        //importCSVToDatabase(
        //    owner = owner,
        //    context = context,
        //    dbViewModel = this
        //)
    }

    /* logic */
    fun getObservation(location: Location): LiveData<Observation> = getObservationUseCase.execute(location)
    fun getAllObservations(): LiveData<List<Observation>> = getAllObservationsUseCase.execute()
    fun createObservation(observation: Observation) = createObservationUseCase.execute(observation)
    fun updateObservation(observation: Observation) = updateObservationUseCase.execute(observation)
    fun deleteObservation(location: Location) = deleteObservationUseCase.execute(location)

    fun getMac(): String {
        val pref = context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )
        return pref.getString("mac", "00:B0:D0:63:C2:26")!!
    }
    fun setMac(value: String) {
        val pref = context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        ).edit()
        pref.putString("mac", value)
        pref.apply()
    }
    fun getSelfSignals(): SignalTriplet {
        val pref = context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        )
        return Json.decodeFromString(
            pref.getString("selfsignals", Json.encodeToString(
                SignalTriplet(arrayOf(0,0,0))
            ))!!
        )
    }
    fun setSelfSignals(value: SignalTriplet) {
        val pref = context.getSharedPreferences(
            context.packageName,
            Context.MODE_PRIVATE
        ).edit()
        pref.putString("selfsignals", Json.encodeToString(value))
        pref.apply()
    }

}