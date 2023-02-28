package drodobyte.android.x

import android.view.View
import android.widget.TextView
import drodobyte.core.util.formatted
import java.util.*

fun TextView.fromDate(date: Date) {
    text = date.formatted
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.show(show: Boolean) {
    if (show) show() else gone()
}

fun View.gone(gone: Boolean) {
    if (gone) gone() else show()
}
