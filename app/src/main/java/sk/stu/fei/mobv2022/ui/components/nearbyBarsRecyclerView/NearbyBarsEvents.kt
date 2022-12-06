package sk.stu.fei.mobv2022.ui.components.nearbyBarsRecyclerView

import sk.stu.fei.mobv2022.ui.viewmodels.data.NearbyBar


interface NearbyBarsEvents {
    fun onBarClick(nearbyBar: NearbyBar)
}