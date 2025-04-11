package drodobyte.android.compose

import androidx.compose.runtime.Composable
import drodobyte.core.model.Id

typealias Content = @Composable () -> Unit
typealias ContentId = @Composable (Id) -> Unit

typealias StringResInt = Int
typealias DrawableResInt = Int
