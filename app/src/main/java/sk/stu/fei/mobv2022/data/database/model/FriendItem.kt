package sk.stu.fei.mobv2022.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
class FriendItem (
    @PrimaryKey val id: String,
    val name:String,
    val barId:String?,
    val barName:String?,
    val time:String?,
    val barLat:Double?,
    val barLon:Double?
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendItem

        if (id != other.id) return false
        if (name != other.name) return false
        if (barId != other.barId) return false
        if (barName != other.barName) return false
        if (time != other.time) return false
        if (barLat != other.barLat) return false
        if (barLon != other.barLon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (barId?.hashCode() ?: 0)
        result = 31 * result + (barName?.hashCode() ?: 0)
        result = 31 * result + time.hashCode()
        result = 31 * result + barLat.hashCode()
        result = 31 * result + barLon.hashCode()
        return result
    }

    override fun toString(): String {
        return "FriendItem(id='$id', name='$name', barId=$barId, barName=$barName, time='$time', barLat=$barLat, barLon=$barLon)"
    }


}