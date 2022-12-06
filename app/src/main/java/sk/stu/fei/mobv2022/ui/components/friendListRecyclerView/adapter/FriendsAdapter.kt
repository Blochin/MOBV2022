package sk.stu.fei.mobv2022.ui.components.friendListRecyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.data.database.model.FriendItem
import sk.stu.fei.mobv2022.services.autoNotify
import kotlin.properties.Delegates

class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.ItemViewHolder>() {

    var items: List<FriendItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.id.compareTo(n.id) == 0
        }
    }

    class ItemViewHolder(
        private val parent : ViewGroup, view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.friend_item, parent,false)
    ) : RecyclerView.ViewHolder(view) {
        val item : ConstraintLayout = view.findViewById(R.id.friend_item)
        val nameView: TextView = view.findViewById(R.id.friends_name_text)
        val barNameView: TextView = view.findViewById(R.id.bar_name_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        val id = item.id

        holder.nameView.text = item.name
        holder.barNameView.text = item.barName

    }

    override fun getItemCount(): Int {
        return items.size
    }
}