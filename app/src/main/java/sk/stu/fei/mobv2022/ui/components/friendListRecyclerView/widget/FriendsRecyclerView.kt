package sk.stu.fei.mobv2022.ui.components.friendListRecyclerView.widget

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv2022.data.database.model.FriendItem
import sk.stu.fei.mobv2022.ui.components.friendListRecyclerView.adapter.FriendsAdapter

class FriendsRecyclerView : RecyclerView {

    private lateinit var friendsAdapter: FriendsAdapter

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        init(context)
    }

    private fun init(context: Context) {
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        friendsAdapter = FriendsAdapter()
        adapter = friendsAdapter
    }
}
@BindingAdapter(value = ["friendItems"])
fun FriendsRecyclerView.applyItems(
    friendItems: List<FriendItem>?
) {
    (adapter as FriendsAdapter).items = friendItems ?: emptyList()
}