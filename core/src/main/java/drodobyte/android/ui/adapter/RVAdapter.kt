package drodobyte.android.ui.adapter

abstract class RVAdapter<T>(
    layoutId: Int, open val listener: (T) -> Unit
) : BaseRVAdapter<T>(layoutId)
