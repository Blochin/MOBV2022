package sk.stu.fei.mobv2022.ui.components.friendListRecyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.data.database.model.FriendItem
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.services.autoNotify
import sk.stu.fei.mobv2022.ui.components.friendListRecyclerView.DeleteFriendAction
import sk.stu.fei.mobv2022.ui.components.nearbyBarsRecyclerView.NearbyBarsEvents
import sk.stu.fei.mobv2022.ui.fragments.BarDetailFragmentDirections
import sk.stu.fei.mobv2022.ui.viewmodels.FriendViewModel
import kotlin.properties.Delegates

class FriendsAdapter(val events: DeleteFriendAction? = null) : RecyclerView.Adapter<FriendsAdapter.ItemViewHolder>() {

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
        val deleteButton: AppCompatButton = view.findViewById(R.id.deleteFriend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        val barId = item.barId?: "-1"

        holder.nameView.text = item.name
        holder.barNameView.text = item.barName

        holder.deleteButton.setOnClickListener{
            events?.onDeleteClick(item.name)
        }

        holder.item.setOnClickListener{
            if(barId != "-1"){
                Navigation.findNavController(it).navigate(BarDetailFragmentDirections.actionToBarDetail(barId))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}