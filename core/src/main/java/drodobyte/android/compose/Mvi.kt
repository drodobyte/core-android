package drodobyte.android.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import drodobyte.core.rx.In

@Composable
fun <S : Any> mvi(
    empty: S,
    start: S,
    states: In<S>,
    reduce: (prev: S, now: S) -> S,
    save: (S) -> S = { it }
): State<S> {
    var saved by rememberSaveable { mutableStateOf(start) }
    return produceState(empty) { // fixme empty state not needed
        states
            .startWith(saved)
            .scan { prev, now ->
                reduce(prev, now)
                    .also {
                        log("Compose-Mvi", "Reduce: [$now] + [$prev]  ->  [$it]")
                    }
            }
            .distinctUntilChanged()
            .doOnNext { log("Compose-Mvi", "Next  : [$it]") }

            .subscribe {
                value = it
            }
            .apply {
                awaitDispose {
                    saved = save(value) // save state for recreation
                    dispose()
                }
            }
    }
}
