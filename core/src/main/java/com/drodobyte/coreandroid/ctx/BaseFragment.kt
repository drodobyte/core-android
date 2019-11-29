package com.drodobyte.coreandroid.ctx

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

//    val presenter: P by lazy {
//        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
//        ViewModelProviders.of(this, app.viewModelFactory).get(type as Class<P>)
//    }

    final override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(menu() != 0)
        return inflater.inflate(layout(), container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menu() != 0)
            inflater.inflate(menu(), menu)
    }

    abstract fun layout(): Int
    open fun menu(): Int = 0

    private val disposables = CompositeDisposable()

    fun <T> Observable<T>.xSubscribe(onNext: (T) -> Unit) {
        disposables.add(subscribe(onNext))
    }

    fun <T> Observable<T>.xSubscribe(onNext: () -> Unit) {
        disposables.add(subscribe { onNext() })
    }

    fun <T> Maybe<T>.xSubscribe(onNext: (T) -> Unit) {
        disposables.add(subscribe(onNext))
    }

    fun <T> Single<T>.xSubscribe(onNext: (T) -> Unit) {
        disposables.add(subscribe(onNext))
    }

    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}
