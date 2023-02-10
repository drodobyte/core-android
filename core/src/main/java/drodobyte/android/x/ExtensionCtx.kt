package drodobyte.android.x

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import io.reactivex.Observable

fun FragmentActivity.onBackPressed(action: () -> Unit) {
    onBackPressedDispatcher.addCallback(
        this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action()
            }
        }
    )
}

fun FragmentActivity.backPressObservable() =
    Observable.create<Any> {
        onBackPressed {
            it.onNext(Any())
        }
    }
