package sk.stu.fei.mobv2022.data.database

import androidx.lifecycle.LiveData
import androidx.room.Query
import sk.stu.fei.mobv2022.data.database.model.BarItem

class LocalCache(private val dao: DbDao) {
    suspend fun insertBars(bars: List<BarItem>) {
        dao.insertBars(bars)
    }

    suspend fun deleteBars() {
        dao.deleteBars()
    }

    fun getBars(): LiveData<List<BarItem>?> = dao.getBars()
    fun getBarsByBarNameAsc(): LiveData<List<BarItem>?> = dao.getBarsByBarNameAsc()
    fun getBarsByBarNameDesc(): LiveData<List<BarItem>?> = dao.getBarsByBarNameDesc()
    fun getBarsByUsersCountAsc(): LiveData<List<BarItem>?> = dao.getBarsByUsersCountAsc()
    fun getBarsByUsersCountDesc(): LiveData<List<BarItem>?> = dao.getBarsByUsersCountDesc()
}