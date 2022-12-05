package sk.stu.fei.mobv2022.ui.viewmodels.data

class BarDetail(
    id: String,
    name: String? = "Missing name",
    type: String? = "",
    lat: Double,
    long: Double,
    tags: Tag?,
    distance: Double = 0.0
) : NearbyBar(id, name, type, lat, long, tags, distance) {
    var users: Int? = 0;
}