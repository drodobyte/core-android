@file:Suppress("FunctionNaming")

package drodobyte.android.compose

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import drodobyte.core.util.Strings

typealias Pane = String
typealias Panes = Strings

@Composable
fun PanesHost(
    panes: Panes,
    go: Go?,
    content: @Composable (Pane) -> Unit,
    shown: (prev: Pane?, now: Pane?) -> Unit,
    done: () -> Unit
) =
    rememberNavController().let {
        NavHost(
            it,
            startDestination = I
        ) {
            composable(I) { }
            panes.forEach { pane ->
                composable(pane) { content(pane) }
            }
        }
        DisposableEffect(it) {
            it.onRoute { prev, now ->
                if (now?.first != I)
                    shown(prev?.first, now?.first)
            }
            onDispose {}
        }
        SideEffect {
            go?.let(it::to)
        }
        BackHandler(true) {
            it.popBackStack()
            if (it.currentDestination?.route == I) {
                done()
            }
        }
    }

data class Go(val pane: Pane, val singleTop: Boolean = true, val inBackstack: Boolean = true)

private fun NavHostController.to(go: Go) =
    navigate(go.pane) {
        if (go.inBackstack)
            popUpTo(I) { saveState = true }
        launchSingleTop = go.singleTop
        restoreState = true
    }

private const val I = "<--init-->"

private typealias Dest = Pair<String?, Bundle?>

private var prev: Dest? = null
private fun NavHostController.onRoute(onRoute: (prev: Dest?, next: Dest?) -> Unit) {
    addOnDestinationChangedListener { _, dest, args ->
        val next = dest.route to args
        if (prev != next) {
            onRoute(prev, next)
            prev = next
        }
    }
}
