package lt.ktu.locationapp.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import lt.ktu.locationapp.data.local.MyDatabase
import lt.ktu.locationapp.data.local.ObservationDao
import lt.ktu.locationapp.data.repository.ObservationRepository
import lt.ktu.locationapp.domain.usecases.CreateObservationUseCase
import lt.ktu.locationapp.domain.usecases.DeleteAllUseCase
import lt.ktu.locationapp.domain.usecases.DeleteObservationUseCase
import lt.ktu.locationapp.domain.usecases.GetAllObservationsUseCase
import lt.ktu.locationapp.domain.usecases.GetObservationUseCase
import lt.ktu.locationapp.domain.usecases.UpdateObservationUseCase
import lt.ktu.locationapp.presentation.viewmodels.DatabaseManager

object DependencyProvider {

    // Lazy initialization of the database
    private var database: MyDatabase? = null

    // Provide the DAO
    private fun provideDao(context: Context): ObservationDao {
        return provideDatabase(context).observationDao()
    }

    // Provide the Database (Room)
    private fun provideDatabase(context: Context): MyDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "database.db"
            ).allowMainThreadQueries().build() // TODO: remove allow main thread
            database = instance
            instance
        }
    }

    // Provide the Repository
    fun provideObservationRepository(context: Context): ObservationRepository {
        return ObservationRepository(provideDao(context))
    }

    // Provide UseCases
    fun provideGetAllObservationsUseCase(context: Context): GetAllObservationsUseCase {
        return GetAllObservationsUseCase(provideObservationRepository(context))
    }
    fun provideGetObservationUseCase(context: Context): GetObservationUseCase {
        return GetObservationUseCase(provideObservationRepository(context))
    }
    fun provideCreateObservationUseCase(context: Context): CreateObservationUseCase {
        return CreateObservationUseCase(provideObservationRepository(context))
    }
    fun provideDeleteObservationUseCase(context: Context): DeleteObservationUseCase {
        return DeleteObservationUseCase(provideObservationRepository(context))
    }
    fun provideUpdateObservationUseCase(context: Context): UpdateObservationUseCase {
        return UpdateObservationUseCase(provideObservationRepository(context))
    }

    // Provide the ViewModel Factory
    fun provideDatabaseViewModelFactory(context: Context): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = provideObservationRepository(context)
                val getAllObservationsUseCase = GetAllObservationsUseCase(repository)
                val getObservationUseCase     = GetObservationUseCase(repository)
                val createObservationUseCase  = CreateObservationUseCase(repository)
                val deleteObservationUseCase  = DeleteObservationUseCase(repository)
                val updateObservationUseCase  = UpdateObservationUseCase(repository)
                val deleteAllUseCase          = DeleteAllUseCase(repository)
                return DatabaseManager(
                    getAllObservationsUseCase = getAllObservationsUseCase,
                    getObservationUseCase     = getObservationUseCase,
                    createObservationUseCase  = createObservationUseCase,
                    deleteObservationUseCase  = deleteObservationUseCase,
                    updateObservationUseCase  = updateObservationUseCase,
                    deleteAllUseCase          = deleteAllUseCase,
                    context = context
                ) as T
            }
        }
    }
}
