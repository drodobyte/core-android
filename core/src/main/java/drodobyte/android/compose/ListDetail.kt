@file:Suppress("FunctionNaming")

package drodobyte.android.compose

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetail(showDetails: Boolean, list: Content, detail: Content, back: () -> Unit) =
    with(rememberListDetailPaneScaffoldNavigator<Any>()) {
        var navigate by remember { mutableStateOf(showDetails) }
        navigate = showDetails
        DisposableEffect(navigate) {
            if (navigate) {
                navigate = false
                navigateTo(ListDetailPaneScaffoldRole.Detail, "")
            }
            onDispose {
            }
        }

        BackHandler(canNavigateBack()) {
            back()
            navigateBack()
        }

        ListDetailPaneScaffold(
            directive = scaffoldDirective,
            value = scaffoldValue,
            listPane = { AnimatedPane { list() } },
            detailPane = { AnimatedPane { detail() } },
        )
    }
