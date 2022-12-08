package sk.stu.fei.mobv2022.ui.components.friendListRecyclerView.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv2022.MainActivity
import sk.stu.fei.mobv2022.data.database.model.FriendItem
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.ui.components.friendListRecyclerView.DeleteFriendAction
import sk.stu.fei.mobv2022.ui.components.friendListRecyclerView.adapter.FriendsAdapter
import sk.stu.fei.mobv2022.ui.components.nearbyBarsRecyclerView.NearbyBarsEvents
import sk.stu.fei.mobv2022.ui.fragments.FriendListFragment
import sk.stu.fei.mobv2022.ui.viewmodels.FriendViewModel
import sk.stu.fei.mobv2022.ui.viewmodels.data.NearbyBar

class FriendsRecyclerView : RecyclerView {

    private lateinit var friendsAdapter: FriendsAdapter
    var events: DeleteFriendAction? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        init(context)
    }

    private fun init(context: Context) {
        //viewModel = ViewModelProvider(this)[FriendViewModel::class.java]
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        friendsAdapter = FriendsAdapter()

        adapter = FriendsAdapter(object :DeleteFriendAction{
            override fun onDeleteClick(name: String) {
                events?.onDeleteClick(name)
            }
        })
    }
}
@BindingAdapter(value = ["friendItems"])
fun FriendsRecyclerView.applyItems(
    friendItems: List<FriendItem>?
) {
    (adapter as FriendsAdapter).items = friendItems ?: emptyList()
}