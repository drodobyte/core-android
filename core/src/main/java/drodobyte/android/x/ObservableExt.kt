package drodobyte.android.x

import drodobyte.core.rx.In
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io

val <T> In<T>.onIO: In<T> get() = subscribeOn(io()).observeOn(mainThread())
val <T> Maybe<T>.onIO get() = subscribeOn(io()).observeOn(mainThread())
val <T> Single<T>.onIO get() = subscribeOn(io()).observeOn(mainThread())
