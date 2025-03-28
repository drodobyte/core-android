@file:Suppress("FunctionNaming")

package drodobyte.android.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import java.util.logging.Logger

fun log(tag: String? = null, message: String) =
    logger(tag).info(message)

fun logger(tag: String? = null): Logger =
    tag?.let { Logger.getLogger(it) } ?: Logger.getGlobal()

@Composable
fun Log(tag: String? = null, m: String) =
    with(remember(tag) { logger(tag) }) {
        SideEffect {
            info(m)
        }
    }
