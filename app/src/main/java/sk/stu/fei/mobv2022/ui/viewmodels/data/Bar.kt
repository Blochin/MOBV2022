package sk.stu.fei.mobv2022.ui.viewmodels.data

data class Bar(
    val id: String,
    val name: String,
    val type: String,
    val lat: Double,
    val lon: Double,
    var users: Int,
    val distance: Double
)