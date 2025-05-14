package drodobyte.android.x

import drodobyte.core.rx.In
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject

open class InOutSubject<T : Any>(i: () -> In<T>, val o: (T) -> Unit) : Subject<T>() {

    private val s = ReplaySubject.create<T>()

    init {
        i().subscribe(s)
    }

    override fun subscribeActual(observer: Observer<in T>) = s.subscribe(observer)
    override fun hasObservers() = s.hasObservers()
    override fun hasThrowable() = s.hasThrowable()
    override fun hasComplete() = s.hasComplete()
    override fun getThrowable() = s.throwable
    override fun onSubscribe(d: Disposable) = s.onSubscribe(d)
    override fun onNext(t: T) = o(t)
    override fun onError(e: Throwable) = s.onError(e)
    override fun onComplete() = s.onComplete()
}
