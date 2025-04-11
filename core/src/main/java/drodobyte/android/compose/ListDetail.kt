@file:Suppress("FunctionNaming")

package drodobyte.android.compose

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole.Detail
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import drodobyte.core.model.Id
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetail(showDetails: Boolean, list: Content, detail: Content, back: () -> Unit) =
    with(rememberListDetailPaneScaffoldNavigator<Any>()) {
        val scope = rememberCoroutineScope()
        var navigate by remember { mutableStateOf(showDetails) }
        navigate = showDetails
        DisposableEffect(navigate) {
            scope.launch {
                if (navigate) {
                    navigate = false
                    navigateTo(Detail, "")
                }
            }
            onDispose {
            }
        }

        BackHandler(canNavigateBack()) {
            back()
            scope.launch {
                navigateBack()
            }
        }

        ListDetailPaneScaffold(
            directive = scaffoldDirective,
            value = scaffoldValue,
            listPane = { AnimatedPane { list() } },
            detailPane = { AnimatedPane { detail() } },
        )
    }

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailV2(
    list: Content,
    detail: Content?,
    detailId: Id?,
    back: () -> Unit
) =
    with(rememberListDetailPaneScaffoldNavigator<Any>()) {
        val scope = rememberCoroutineScope()

        LaunchedEffect(detailId) {
            scope.launch {
                if (currentDestination?.contentKey != detailId && detail != null) {
                    navigateTo(Detail, detailId)
                }
            }
        }

        BackHandler(canNavigateBack()) {
            back()
            scope.launch {
                navigateBack()
            }
        }

        ListDetailPaneScaffold(
            directive = scaffoldDirective,
            value = scaffoldValue,
            listPane = { AnimatedPane { list() } },
            detailPane = { detail?.let { AnimatedPane { it() } } },
        )
    }
