package sk.stu.fei.mobv2022.data.api

import sk.stu.fei.mobv2022.ui.viewmodels.data.Tag


data class UserResponse(
    val uid: String,
    val access: String,
    val refresh: String
)

data class BarListResponse(
    val bar_id: String,
    val bar_name: String,
    val bar_type: String,
    val lat: Double,
    var lon: Double,
    var users: Int
)

data class BarDetailItemResponse(
    val type: String,
    val id: String,
    val lat: Double,
    val lon: Double,
    val tags: Tag
)

data class BarDetailResponse(
    val elements: List<BarDetailItemResponse>
)

data class FriendsListResponse(
    val user_id: String,
    val user_name: String,
    val bar_id: String?,
    val bar_name: String?,
    val time: String?,
    val bar_lat: Double?,
    val bar_lon: Double?
)