package drodobyte.android.prefs

import android.content.Context
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.rxjava2.rxPreferencesDataStore
import drodobyte.android.x.InOutSubject
import drodobyte.core.rx.Dispose
import drodobyte.core.rx.In
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
open class Prefs(private val context: Context, name: String) : Dispose {
    private val Context.store by rxPreferencesDataStore(name)
    private val keys = Keys()

    fun int(key: String, def: Int = 0) = io(key, def)
    fun long(key: String, def: Long = 0L) = io(key, def)
    fun bool(key: String, def: Boolean = false) = io(key, def)
    fun str(key: String, def: String = "") = io(key, def)
    fun float(key: String, def: Float = 0f) = io(key, def)
    fun double(key: String, def: Double = 0.0) = io(key, def)
    fun strings(key: String, def: Set<String> = emptySet()) = io(key, def)
    fun bytes(key: String, def: ByteArray = ByteArray(0)) = io(key, def)

    protected fun <T : Any> set(key: Key<T>, value: T) = context.store.updateDataAsync {
        Single.fromCallable { it.toMutablePreferences().apply { set(key, value) } }
    }

    protected fun <T : Any> get(key: Key<T>, or: T): In<T> =
        context.store.data().toObservable().map { it[key] ?: or }

    override fun dispose() = context.store.dispose()

    @Suppress("unchecked_cast")
    private fun <T : Any> io(key: String, def: T): InOutSubject<T> =
        keys.get(key, def)
            .let { InOutSubject({ get(it as Key<T>, def) }, { value -> set(it, value) }) }
}
