package drodobyte.android.x

import android.view.View
import android.widget.TextView
import java.text.DateFormat
import java.util.*

fun Date.formatted(): String =
    DateFormat.getDateInstance().format(this)

fun TextView.fromDate(date: Date) {
    text = date.formatted()
}

fun String.asDate(onError: Date = Date()): Date =
    try {
        DateFormat.getDateInstance().parse(this)!!
    } catch (t: Throwable) {
        onError
    }

fun CharSequence.asDate(onError: Date = Date()) =
    toString().asDate(onError)

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
    if (show) show() else hide()
}
