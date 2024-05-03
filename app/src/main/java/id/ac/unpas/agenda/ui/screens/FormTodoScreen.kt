package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.launch

@Composable
fun FormTodoScreen() {

    val viewModel = hiltViewModel<TodoViewModel>()
    val scope = rememberCoroutineScope()

    val title = remember { mutableStateOf(TextFieldValue("")) }
    val description = remember { mutableStateOf(TextFieldValue("")) }
    val dueDate = remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {

        OutlinedTextField(
            label = { Text(text = "Title") },
            modifier = Modifier.fillMaxWidth(),
            value = title.value, onValueChange = {
            title.value = it
        })

        OutlinedTextField(
            label = { Text(text = "Description") },
            modifier = Modifier.fillMaxWidth(),
            value = description.value, onValueChange = {
                description.value = it
            })

        OutlinedTextField(
            label = { Text(text = "Due Date") },
            modifier = Modifier.fillMaxWidth(),
            value = dueDate.value, onValueChange = {
                dueDate.value = it
            })

        Row {
            Button(modifier = Modifier.weight(5f), onClick = {
                scope.launch {
                    viewModel.insert(uuid4().toString(), title.value.text, description.value.text, dueDate.value.text)
                }
            }) {
                Text(text = "Simpan")
            }

            Button(modifier = Modifier.weight(5f), onClick = {
                title.value = TextFieldValue("")
                description.value = TextFieldValue("")
                dueDate.value = TextFieldValue("")
            }) {
                Text(text = "Batal")
            }
        }

        viewModel.isDone.observe(LocalLifecycleOwner.current) {
            if (it) {
                title.value = TextFieldValue("")
                description.value = TextFieldValue("")
                dueDate.value = TextFieldValue("")
            }
        }

    }

}