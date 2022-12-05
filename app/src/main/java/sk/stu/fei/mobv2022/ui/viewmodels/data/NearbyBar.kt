package sk.stu.fei.mobv2022.ui.viewmodels.data

import android.location.Location
import sk.stu.fei.mobv2022.services.GpsLocation


data class MyLocation(
    var lat: Double,
    var lon: Double
)

open class NearbyBar(
    val id: String,
    val name: String? = "Missing name",
    val type: String? = "",
    val lat: Double,
    val lon: Double,
    val tags: Tag?,
    var distance: Double = 0.0,
    var isPinned: Boolean = false
){

    fun distanceTo(toLocation: GpsLocation) {
        this.distance =  Location("").apply {
            latitude = lat
            longitude = lon
        }.distanceTo(Location("").apply {
            latitude = toLocation.lat
            longitude = toLocation.long
        }).toDouble()
    }


}