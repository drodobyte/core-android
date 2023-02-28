package drodobyte.android.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

class Go(private val context: Context) {

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

    private fun Intent.put(key: String?, value: Any) {
        if (key != null)
            when (value) {
                is Int -> putExtra(key, value)
                is String -> putExtra(key, value)
                else -> TODO("Go intent arg ${value.javaClass} class not implemented")
            }
    }
}
