package sk.stu.fei.mobv2022.services

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import sk.stu.fei.mobv2022.data.api.RestApi
import sk.stu.fei.mobv2022.data.database.AppRoomDatabase
import sk.stu.fei.mobv2022.data.database.LocalCache
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.factory.ViewModelFactory

object Injection {
    private fun provideCache(context: Context): LocalCache {
        val database = AppRoomDatabase.getInstance(context)
        return LocalCache(database.appDao())
    }

    fun provideDataRepository(context: Context): DataRepository {
        return DataRepository.getInstance(RestApi.create(context), provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(
            provideDataRepository(
                context
            )
        )
    }
}