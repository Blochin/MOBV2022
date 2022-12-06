
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.services.autoNotify
import sk.stu.fei.mobv2022.ui.components.nearbyBarsRecyclerView.NearbyBarsEvents
import sk.stu.fei.mobv2022.ui.viewmodels.data.NearbyBar
import kotlin.properties.Delegates

class NearbyBarsAdapter(val events: NearbyBarsEvents? = null) :
    RecyclerView.Adapter<NearbyBarsAdapter.NearbyBarItemViewHolder>() {
    var items: List<NearbyBar> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id.compareTo(n.id) == 0 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyBarItemViewHolder {
        return NearbyBarItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NearbyBarItemViewHolder, position: Int) {
        holder.bind(items[position], events)
    }

    class NearbyBarItemViewHolder(
        private val parent: ViewGroup,
        itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.nearby_bar_item,
            parent,
            false)
        ) : RecyclerView.ViewHolder(itemView){

        fun bind(item: NearbyBar, events: NearbyBarsEvents? = null) {
            itemView.findViewById<TextView>(R.id.name).text = item.name
            itemView.findViewById<TextView>(R.id.distance).text = "%.2f m".format(item.distance)
            itemView.findViewById<Chip>(R.id.type).text = item.type

            itemView.setOnClickListener { events?.onBarClick(item) }
        }
    }
}