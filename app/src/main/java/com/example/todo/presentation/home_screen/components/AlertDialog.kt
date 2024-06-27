package com.example.todo.presentation.home_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.domain.model.Todo
import com.example.todo.presentation.MainViewModel
import com.example.todo.presentation.common.taskTextStyle
import com.example.todo.presentation.common.toastMsg
import com.example.todo.ui.theme.isImportant1Color
import com.example.todo.ui.theme.isImportant2Color
import com.example.todo.ui.theme.isImportant3Color

@Composable
fun AlertDialog(
    openDialog: Boolean,
    onClose: () -> Unit,
    mainViewModel: MainViewModel
) {
    var text by remember { mutableStateOf("") }
    var priorities by remember { mutableStateOf(listOf(false, false, false)) }
    val todo = Todo(0, text, priorities[0], priorities[1], priorities[2])
    val focusRequester = FocusRequester()
    val context = LocalContext.current

    if (openDialog) {
        AlertDialog(
            title = { Text("Задача", fontFamily = FontFamily.Serif) },
            text = {
                LaunchedEffect(Unit) { focusRequester.requestFocus() }
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        placeholder = {
                            Text(
                                "Добавьте задачу",
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        singleLine = true,
                        shape = CircleShape,
                        modifier = Modifier
                            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            if (text.isNotBlank()) {
                                mainViewModel.insertTodo(todo)
                                text = ""
                                priorities = listOf(false, false, false)
                                onClose()
                            } else {
                                toastMsg(
                                    context,
                                    "Пустая задача!"
                                )
                            }
                        }),
                        trailingIcon = {
                            IconButton(onClick = { text = "" }) {
                                Icon(Icons.Rounded.Clear, contentDescription = null)
                            }
                        },
                        textStyle = taskTextStyle
                    )
                    PriorityCheckboxes(priorities) { index, value ->
                        priorities = priorities.mapIndexed { i, _ -> i == index && value }
                    }
                }
            },
            onDismissRequest = {
                onClose()
                text = ""
                listOf(false, false, false)
            },
            confirmButton = {
                Button(onClick = {
                        if (text.isNotBlank()) {
                            mainViewModel.insertTodo(todo)
                            text = ""
                            priorities = listOf(false, false, false)
                            onClose()
                        } else {
                            toastMsg(
                                context,
                                "Пустая задача!"
                            )
                    }
                }) { Text("Сохранить") }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    onClose()
                    text = ""
                    priorities = listOf(false, false, false)
                }) { Text("Закрыть") }
            }
        )
    }
}

@Composable
fun PriorityCheckboxes(priorities: List<Boolean>, onPriorityChange: (Int, Boolean) -> Unit) {
    val labels = listOf("Приоритет 1 ур", "Приоритет 2 ур", "Приоритет 3 ур")
    val colors = listOf(isImportant1Color, isImportant2Color, isImportant3Color)

    priorities.forEachIndexed { index, isChecked ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(value = isChecked, onValueChange = { onPriorityChange(index, it) }),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.Star, contentDescription = null, tint = colors[index])
            Text(
                labels[index],
                fontFamily = FontFamily.Monospace,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )
            Checkbox(checked = isChecked, onCheckedChange = { onPriorityChange(index, it) })
        }
    }
}