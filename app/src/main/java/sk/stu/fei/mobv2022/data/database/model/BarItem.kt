package sk.stu.fei.mobv2022.data.database.model

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.stu.fei.mobv2022.ui.viewmodels.data.MyLocation


@Entity(tableName = "bars")
class BarItem(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val lat: Double,
    val lon: Double,
    var users: Int
) {

    override fun toString(): String {
        return "BarItem(id='$id', name='$name', type='$type', lat=$lat, lon=$lon, users=$users)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BarItem) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (lat != other.lat) return false
        if (lon != other.lon) return false
        if (users != other.users) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lon.hashCode()
        result = 31 * result + users
        return result
    }

    fun distanceTo(myLocation: MyLocation): Double {
        var distance =  Location("").apply {
            latitude = lat
            longitude = lon
        }.distanceTo(Location("").apply {
            latitude = myLocation.lat
            longitude = myLocation.lon
        }).toDouble()

        return distance

    }
}