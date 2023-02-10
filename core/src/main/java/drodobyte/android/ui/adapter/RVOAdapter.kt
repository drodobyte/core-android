package drodobyte.android.ui.adapter

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class RVOAdapter<T>(layoutId: Int) : BaseRVAdapter<T>(layoutId) {
    private var onClick = PublishSubject.create<T>()

    fun onClickObservable() =
        onClick as Observable<T>

    fun emitClick(item: T) {
        onClick.onNext(item)
    }
}
