package sk.stu.fei.mobv2022.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
class FriendItem (
    @PrimaryKey(autoGenerate = true) val localId: Int = 0,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "pub_id") val pubId: String?,
    @ColumnInfo(name = "pub_name") val pubName: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendItem

        if (localId != other.localId) return false
        if (userId != other.userId) return false
        if (userName != other.userName) return false
        if (pubId != other.pubId) return false
        if (pubName != other.pubName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = localId
        result = 31 * result + userId.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + (pubId?.hashCode() ?: 0)
        result = 31 * result + (pubName?.hashCode() ?: 0)
        return result
    }
}