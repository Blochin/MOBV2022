package sk.stu.fei.mobv2022.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sk.stu.fei.mobv2022.data.database.model.BarItem

@Dao
interface DbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBars(bars: List<BarItem>)

    @Query("DELETE FROM bars")
    suspend fun deleteBars()

    @Query("SELECT * FROM bars order by users DESC, name ASC")
    fun getBars(): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars ORDER BY CASE WHEN :isAsc = 1 THEN users END ASC, CASE WHEN :isAsc = 0 THEN users END DESC")
    fun getAllOrderByUsers(isAsc: Boolean): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars ORDER BY CASE WHEN :isAsc = 1 THEN name END ASC, CASE WHEN :isAsc = 0 THEN name END DESC")
    fun getAllOrderByName(isAsc: Boolean): LiveData<List<BarItem>?>

}