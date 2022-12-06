package sk.stu.fei.mobv2022.data.database

import androidx.lifecycle.LiveData
import androidx.room.Query
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.data.database.model.FriendItem

class LocalCache(private val dao: DbDao) {
    suspend fun insertBars(bars: List<BarItem>) { dao.insertBars(bars) }
    suspend fun deleteBars() { dao.deleteBars() }
    suspend fun insertAllFriend(friends: List<FriendItem>) = dao.insertFriends(friends)
    suspend fun deleteAllFriends(){dao.deleteAllFriends()}

    suspend fun getBars(): List<BarItem>? = dao.getBars()
    suspend fun getBarById(id: String): BarItem = dao.getBarById(id)
    suspend fun getAllOrderByName(orderBy: Boolean): List<BarItem>? = dao.getAllOrderByName(orderBy)
    suspend fun getAllOrderByUsers(orderBy: Boolean): List<BarItem>? = dao.getAllOrderByUsers(orderBy)
    fun getAllFriends(): LiveData<List<FriendItem>?> = dao.getAllFriends()
}