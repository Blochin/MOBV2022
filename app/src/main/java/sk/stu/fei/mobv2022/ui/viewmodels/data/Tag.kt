package sk.stu.fei.mobv2022.ui.viewmodels.data

import com.google.gson.annotations.SerializedName

data class Tag(
    val name: String,
    val amenity: String,
    val website: String,
    @SerializedName("opening_hours") val openingHours: String,
    val phone: String,
)