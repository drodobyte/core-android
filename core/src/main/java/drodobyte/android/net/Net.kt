package drodobyte.android.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import drodobyte.core.rx.In
import drodobyte.core.rx.plus
import io.reactivex.subjects.BehaviorSubject

class Net(context: Context) {

    data class What(val on: Boolean)

    val what: In<What> by lazy { init(context); BehaviorSubject.create() }

    private fun init(context: Context) =
        (context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager)
            .registerDefaultNetworkCallback(object : NetworkCallback() {
                override fun onAvailable(net: Network) = what merge What(on = true)
                override fun onLost(net: Network) = what merge What(on = false)
                override fun onCapabilitiesChanged(net: Network, cap: NetworkCapabilities) {
                    //                capabilities.hasCapability(NET_CAPABILITY_NOT_METERED)
                }
            })

    private infix fun In<What>.merge(what: What) =
        (this as BehaviorSubject).let {
            it + (it.value?.copy(on = what.on) ?: what)
            Unit
        }
}
