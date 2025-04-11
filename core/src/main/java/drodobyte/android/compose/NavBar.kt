@file:Suppress("FunctionNaming")

package drodobyte.android.compose

import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun NavBarDrawable(
    @StringRes initial: StringResInt,
    buttons: Map<StringResInt, DrawableResInt>,
    clicked: (StringResInt) -> Unit,
    content: @Composable (StringResInt) -> Unit
) =
    buttons.mapValues { (label, drawable) ->
        @Composable {
            Icon(
                painter = painterResource(drawable),
                contentDescription = label.toString()
            )
        } as @Composable () -> Unit
    }.let {
        NavBar(initial, it, clicked, content)
    }

@Composable
fun NavBar(
    @StringRes initial: StringResInt,
    buttons: Map<StringResInt, @Composable () -> Unit>,
    clicked: (StringResInt) -> Unit,
    content: @Composable (StringResInt) -> Unit
) {
    var pane by rememberSaveable { mutableIntStateOf(initial) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            buttons.forEach { (label, composable) ->
                item(
                    icon = composable,
                    label = { Text(stringResource(label)) },
                    selected = label == pane,
                    onClick = { pane = label; clicked(pane) },
                )
            }
        }
    ) {
        content(pane)
    }
}
