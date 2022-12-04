package sk.stu.fei.mobv2022.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sk.stu.fei.mobv2022.data.database.model.FriendItem

@Dao
interface FriendItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(friends: List<FriendItem>)

    @Query("DELETE FROM friends")
    suspend fun deleteAll()

    @Query("SELECT * FROM friends")
    suspend fun getAllFriends()

}