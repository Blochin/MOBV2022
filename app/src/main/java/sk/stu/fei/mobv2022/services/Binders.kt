package sk.stu.fei.mobv2022.services

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(
    "showText"
)
fun applyShowText(
    textView: TextView,
    message: Event<String>?
) {
    message?.getContentIfNotHandled()?.let {
        textView.setText(it)
    }
}
