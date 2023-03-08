package drodobyte.android.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Parcelable

class Go(private val context: Context) {

    val arg by lazy { Arg((context as Activity).intent) }

    class Arg(private val intent: Intent) {
        fun int(key: String, default: Int = 0) = intent.getIntExtra(key, default)
        fun string(key: String) = intent.getStringExtra(key)
    }

    fun go(
        activity: Class<out Activity>,
        clear: Boolean = false,
        vararg args: Pair<String?, Any>
    ) =
        Intent(context, activity).let {
            if (clear)
                it.addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK)
            args.onEach { (key, value) -> it.put(key, value) }
            context.startActivity(it)
        }

    fun back() = (context as Activity).finish()

    private fun Intent.put(key: String?, value: Any) {
        if (key != null)
            when (value) {
                is Int -> putExtra(key, value)
                is String -> putExtra(key, value)
                is Parcelable -> putExtra(key, value)
                else -> TODO("Go intent arg ${value.javaClass} class not implemented")
            }
    }
}
