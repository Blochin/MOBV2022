package sk.stu.fei.mobv2022.data.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sk.stu.fei.mobv2022.data.api.*
import sk.stu.fei.mobv2022.data.database.LocalCache
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.data.database.model.FriendItem
import sk.stu.fei.mobv2022.ui.viewmodels.Sort
import sk.stu.fei.mobv2022.ui.viewmodels.data.MyLocation
import sk.stu.fei.mobv2022.ui.viewmodels.data.NearbyBar
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
        if (sort == Sort.NAME) {
            return getAllOrderByName(sortBy)
        } else if (sort == Sort.COUNT) {
            return getAllOrderByUsers(sortBy)
        } else if (sort == Sort.DISTANCE) {
            return dbBars()
        } else {
            return dbBars()
        }
    }

    fun dbBars(): LiveData<List<BarItem>?> {
        return cache.getBars()
    }

    suspend fun dbBarById(id: String): BarItem {
        return cache.getBarById(id)
    }

    fun getAllOrderByName(orderBy: Boolean): LiveData<List<BarItem>?> {
        return cache.getAllOrderByName(orderBy)
    }

    fun getAllOrderByUsers(orderBy: Boolean): LiveData<List<BarItem>?> {
        return cache.getAllOrderByUsers(orderBy)
    }

    suspend fun apiBarDetail(
        id: String,
        onError: (error: String) -> Unit
    ): NearbyBar? {
        var nearby: NearbyBar? = null
        try {
            val q = "[out:json];node($id);out body;>;out skel;"
            val resp = service.barDetail(q)
            if (resp.isSuccessful) {
                resp.body()?.let { bars ->
                    if (bars.elements.isNotEmpty()) {
                        val b = bars.elements.get(0)
                        nearby = NearbyBar(
                            b.id,
                            b.tags.name,
                            b.tags.amenity,
                            b.lat,
                            b.lon,
                            b.tags
                        )
                    }
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
        return nearby
    }

    suspend fun addFriend(
        name: String, onResolved: (response: String) -> Unit, onError: (response: String) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val resp = service.addFriend(AddFriendRequest(name))
                if (resp.isSuccessful) {
                    onResolved("Friend $name successfully added.")
                } else {
                    onError("Failed to add a friend, try a different name.")
                }
            } catch (ex: IOException) {
                onError("Failed to add a friend, check internet connection")
            } catch (ex: Exception) {
                onError("Failed to add a friend, error.")
            }
        }
    }

    suspend fun getFriends(onError: (error: String) -> Unit){
        try {
            val resp = service.getFriends()
            if (resp.isSuccessful){
                resp.body()?.let { friends ->
                    val f = friends.map{
                        FriendItem(
                            it.user_id,
                            it.user_name,
                            it.bar_id,
                            it.bar_name,
                            it.time,
                            it.bar_lat,
                            it.bar_lon
                        )
                    }
                    cache.deleteAllFriends()
                    cache.insertAllFriend(f)
                }
            }
        }catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to load friends, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Failed to load friends, error.")
        }
    }

    fun dbFriends(): LiveData<List<FriendItem>?> {
        return cache.getAllFriends()
    }

    suspend fun apiNearbyBars(
        lat: Double, lon: Double,
        onError: (error: String) -> Unit
    ) : List<NearbyBar> {
        var nearby = listOf<NearbyBar>()
        try {
            val q = "[out:json];node(around:250,$lat,$lon);(node(around:250)[\"amenity\"~\"^pub$|^bar$|^restaurant$|^cafe$|^fast_food$|^stripclub$|^nightclub$\"];);out body;>;out skel;"
            val resp = service.barNearby(q)
            if (resp.isSuccessful) {
                resp.body()?.let { bars ->
                    nearby = bars.elements.map {
                        NearbyBar(it.id,it.tags.name, it.tags.amenity,it.lat,it.lon,it.tags).apply {
                            distance = distanceTo(MyLocation(lat,lon))
                        }
                    }.filter { it.name?.isNotBlank() ?: false }.sortedBy { it.distance }
                } ?: onError("Failed to load bars")
            } else {
                onError("Failed to read bars")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to load bars, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError(ex.toString())
        }
        return nearby
    }

    suspend fun apiBarCheckin(
        bar: NearbyBar,
        onError: (error: String) -> Unit,
        onSuccess: (success: Boolean) -> Unit
    ) {
        try {
            val resp = service.barMessage(BarMessageRequest(bar.id.toString(),
                bar.name.toString(),bar.type.toString(),bar.lat,bar.lon))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    onSuccess(true)
                }
            } else {
                onError("Failed to login, try again later.")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Login failed, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("IDK")
        }
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