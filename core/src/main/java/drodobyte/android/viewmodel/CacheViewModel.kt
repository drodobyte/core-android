package drodobyte.android.viewmodel

import androidx.lifecycle.ViewModel
import drodobyte.android.util.Cache

class CacheViewModel<T> : ViewModel(), Cache<T> {
    private var item: T? = null

    override fun put(item: T) {
        this.item = item
    }

    override fun get(): T =
        item!!

    override fun clear() {
        item = null
    }

    override fun isCleared() =
        item == null
}
