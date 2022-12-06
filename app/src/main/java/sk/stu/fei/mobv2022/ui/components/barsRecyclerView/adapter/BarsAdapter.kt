package sk.stu.fei.mobv2022.ui.components.barsRecyclerView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.services.autoNotify
import sk.stu.fei.mobv2022.ui.fragments.BarDetailFragmentDirections
import kotlin.properties.Delegates

class BarsAdapter() : RecyclerView.Adapter<BarsAdapter.ItemViewHolder>() {

    var items: List<BarItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.id.compareTo(n.id) == 0
        }
    }

    class ItemViewHolder(
        private val parent : ViewGroup, view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.bar_item, parent,false)
    ) : RecyclerView.ViewHolder(view) {
        val item : ConstraintLayout  = view.findViewById(R.id.bar_item)
        val nameView: TextView = view.findViewById(R.id.tv_name)
        val typeView: TextView = view.findViewById(R.id.c_type)
        val userView: TextView = view.findViewById(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        val id = item.id

        holder.nameView.text = item.name
        holder.typeView.text = item.type
        holder.userView.text = item.users.toString()
        holder.item.setOnClickListener{
            Navigation.findNavController(it).navigate(BarDetailFragmentDirections.actionToBarDetail(id))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}