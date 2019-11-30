package com.drodobyte.coreandroid.x

import android.view.View
import android.widget.TextView
import java.text.DateFormat
import java.util.*

fun Date.xFormatted(): String =
    DateFormat.getDateInstance().format(this)

fun TextView.xDate(date: Date) {
    text = date.xFormatted()
}

fun View.xShow() {
    visibility = View.VISIBLE
}

fun View.xHide() {
    visibility = View.INVISIBLE
}

fun View.xGone() {
    visibility = View.GONE
}

fun View.xShow(show: Boolean) {
    if (show) xShow() else xHide()
}
