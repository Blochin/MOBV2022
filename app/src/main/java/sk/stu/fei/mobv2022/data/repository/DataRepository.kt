package sk.stu.fei.mobv2022.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import sk.stu.fei.mobv2022.data.api.RestApi
import sk.stu.fei.mobv2022.data.api.UserCreateRequest
import sk.stu.fei.mobv2022.data.api.UserLoginRequest
import sk.stu.fei.mobv2022.data.api.UserResponse
import sk.stu.fei.mobv2022.data.database.LocalCache
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.ui.viewmodels.Sort
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DataRepository private constructor(
    private val service: RestApi,
    private val cache: LocalCache
) {

    suspend fun apiUserCreate(
        name: String,
        password: String,
        onError: (error: String) -> Unit,
        onStatus: (success: UserResponse?) -> Unit
    ) {
        try {
            val resp = service.userCreate(UserCreateRequest(name = name, password = password))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    if (user.uid == "-1") {
                        onStatus(null)
                        onError("Name already exists. Choose another.")
                    } else {
                        onStatus(user)
                    }
                }
            } else {
                onError("Failed to sign up, try again later.")
                onStatus(null)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Sign up failed, check internet connection")
            onStatus(null)
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Sign up failed, error.")
            onStatus(null)
        }
    }

    suspend fun apiUserLogin(
        name: String,
        password: String,
        onError: (error: String) -> Unit,
        onStatus: (success: UserResponse?) -> Unit
    ) {
        try {
            val resp = service.userLogin(UserLoginRequest(name = name, password = password))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    if (user.uid == "-1") {
                        onStatus(null)
                        onError("Wrong name or password.")
                    } else {
                        onStatus(user)
                    }
                }
            } else {
                onError("Failed to login, try again later.")
                onStatus(null)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Login failed, check internet connection")
            onStatus(null)
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Login in failed, error.")
            onStatus(null)
        }
    }

    suspend fun apiBarList(
        onError: (error: String) -> Unit,
    ) {
        try {
            val resp = service.barList()
            if (resp.isSuccessful) {
                resp.body()?.let { bars ->

                    val b = bars.map {
                        BarItem(
                            it.bar_id,
                            it.bar_name,
                            it.bar_type,
                            it.lat,
                            it.lon,
                            it.users
                        )
                    }
                    cache.deleteBars()
                    cache.insertBars(b)
                } ?: onError("Failed to load bars")
            } else {
                onError("Failed to read bars")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to load bars, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Failed to load bars, error.")
        }
    }

    fun getSortedBars(sort: Sort, sortBy: Boolean): LiveData<List<BarItem>?> {
        //TODO prerobit do parametrov
        if (sort == Sort.NAME) {
            return if (sortBy) {
                dbBarsByBarNameAsc()
            } else {
                dbBarsByBarNameDesc()
            }
        } else if (sort == Sort.COUNT) {
            return if (sortBy) {
                dbBarsByUsersCountAsc()
            } else {
                dbBarsByUsersCountDesc()
            }
        } else if (sort == Sort.DISTANCE) {
            return if (sortBy) {
                dbBars()
            } else {
                dbBars()
            }
        } else {
            return dbBars()
        }
    }

    fun dbBars(): LiveData<List<BarItem>?> {
        return cache.getBars()
    }

    fun dbBarsByBarNameAsc(): LiveData<List<BarItem>?> {
        return cache.getBarsByBarNameAsc()
    }

    fun dbBarsByBarNameDesc(): LiveData<List<BarItem>?> {
        return cache.getBarsByBarNameDesc()
    }

    fun dbBarsByUsersCountAsc(): LiveData<List<BarItem>?> {
        return cache.getBarsByUsersCountAsc()
    }

    fun dbBarsByUsersCountDesc(): LiveData<List<BarItem>?> {
        return cache.getBarsByUsersCountDesc()
    }

    companion object {
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(service: RestApi, cache: LocalCache): DataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: DataRepository(service, cache).also { INSTANCE = it }
            }

        @SuppressLint("SimpleDateFormat")
        fun dateToTimeStamp(date: String): Long {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)?.time ?: 0L
        }

        @SuppressLint("SimpleDateFormat")
        fun timestampToDate(time: Long): String {
            val netDate = Date(time * 1000)
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(netDate)
        }
    }
}