package sk.stu.fei.mobv2022.ui.components.barsRecyclerView.widget

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.ui.components.barsRecyclerView.adapter.BarsAdapter

class BarsRecyclerView : RecyclerView {
    private lateinit var barsAdapter : BarsAdapter

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        init(context)
    }

    private fun init(context: Context) {
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        barsAdapter = BarsAdapter()
        adapter = barsAdapter
    }
}

@BindingAdapter(value = ["barItems"])
fun BarsRecyclerView.applyItems(
    barItems: List<BarItem>?
) {
    (adapter as BarsAdapter).items = barItems ?: emptyList()
}