package drodobyte.android.rx

import android.util.Log
import drodobyte.core.rx.In
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread

open class Rx(private val owner: Any? = null) : drodobyte.core.rx.Rx(), OnRx {

    fun <T> onRender(`in`: In<T>, item: (T) -> Unit) = `in`.render(item)
    override fun <T> In<T>.render(item: (T) -> Unit) = subscribeToSched(item, mainThread())

    override fun log(it: Throwable) {
        Log.e("Rx", "subscription error, owner:${owner ?: this}", it)
    }
}
