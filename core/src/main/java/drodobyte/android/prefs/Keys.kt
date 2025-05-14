package drodobyte.android.prefs

import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

internal class Keys {

    @Suppress("unchecked_cast")
    fun get(name: String, def: Any) =
        (map[name] ?: def.type(name).let { map[name] = it; it }) as Key<Any>

    private val map = mutableMapOf<String, Key<out Any>>()

    private fun Any.type(key: String): Key<out Any> = when (this) {
        is Boolean -> booleanPreferencesKey(key)
        is Int -> intPreferencesKey(key)
        is String -> stringPreferencesKey(key)
        is Long -> longPreferencesKey(key)
        is Float -> floatPreferencesKey(key)
        is Double -> doublePreferencesKey(key)
        is Set<*> -> stringSetPreferencesKey(key)
        is ByteArray -> byteArrayPreferencesKey(key)
        else -> error("not <$javaClass> implementation found")
    }
}
