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

    @Query("SELECT * FROM bars order by name ASC")
    fun getBarsByBarNameAsc(): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars order by name DESC")
    fun getBarsByBarNameDesc(): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars order by users ASC")
    fun getBarsByUsersCountAsc(): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars order by users DESC")
    fun getBarsByUsersCountDesc(): LiveData<List<BarItem>?>
}