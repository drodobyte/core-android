@file:Suppress("FunctionNaming")

package drodobyte.android.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

@Composable
fun VerticalRadioGroup(items: List<String>, clicked: (String) -> Unit) {
    val (selected, onSelected) = remember { mutableStateOf(items[0]) }
    Column(Modifier.selectableGroup()) {
        items.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selected),
                        onClick = { onSelected(text); clicked(text) },
                        role = Role.RadioButton
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selected),
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = text,
                )
            }
        }
    }
}
