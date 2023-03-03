package drodobyte.android.rx

import drodobyte.core.rx.In

interface OnRx : drodobyte.core.rx.OnRx {
    fun <T> In<T>.render(item: (T) -> Unit) = Unit
}
