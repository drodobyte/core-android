@file:Suppress("FunctionNaming")

package drodobyte.android.compose

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout


@Composable
@Suppress("LongParameterList")
fun Table(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    verticalLazyListState: LazyListState = rememberLazyListState(),
    horizontalScrollState: ScrollState = rememberScrollState(),
    columns: Int,
    rows: Int,
    beforeRow: (@Composable (row: Int) -> Unit)? = null,
    afterRow: (@Composable (row: Int) -> Unit)? = null,
    cell: @Composable (column: Int, row: Int) -> Unit
) {
    val columnWidths = remember { mutableStateMapOf<Int, Int>() }

    Box(modifier = modifier.then(Modifier.horizontalScroll(horizontalScrollState))) {
        LazyColumn(state = verticalLazyListState) {
            items(rows) { row ->
                Column {
                    beforeRow?.invoke(row)

                    Row(modifier = rowModifier) {
                        for (column in 0..<columns) {
                            Box(modifier = layoutModifier(columnWidths, column)) {
                                cell(column, row)
                            }
                        }
                    }

                    afterRow?.invoke(row)
                }
            }
        }
    }
}

@Composable
private fun layoutModifier(columnWidths: SnapshotStateMap<Int, Int>, column: Int) =
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        val existingWidth = columnWidths[column] ?: 0
        val maxWidth = maxOf(existingWidth, placeable.width)

        if (maxWidth > existingWidth) {
            columnWidths[column] = maxWidth
        }

        layout(width = maxWidth, height = placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
