package com.example.todo.presentation.update_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.presentation.MainViewModel
import com.example.todo.presentation.common.taskTextStyle
import com.example.todo.presentation.common.topAppBarTextStyle
import com.example.todo.ui.theme.isImportant1Color
import com.example.todo.ui.theme.isImportant2Color
import com.example.todo.ui.theme.isImportant3Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    id: Int,
    mainViewModel: MainViewModel,
    onBack: () -> Unit
) {
    val task = mainViewModel.todo.task
    val isImportant1 = mainViewModel.todo.isImportant1
    val isImportant2 = mainViewModel.todo.isImportant2
    val isImportant3 = mainViewModel.todo.isImportant3

    LaunchedEffect(key1 = true,
        block = {
            mainViewModel.getTodoById(id)
        })
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = "Изменить задачу",
                style = topAppBarTextStyle
            )
        },
            navigationIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Rounded.Edit, contentDescription = null, modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            TextField(
                value = task, onValueChange = { newValue ->
                    mainViewModel.updateTask(newValue = newValue)
                },
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .height(200.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                label = {
                    Text(
                        text = "Задача",
                        fontFamily = FontFamily.Monospace
                    )
                },
                shape = RectangleShape,
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.Sentences
                ),
                textStyle = taskTextStyle
            )
            Spacer(modifier = Modifier.size(8.dp))
            ImportantCheckbox(isImportant1Color, "Срочно, важно", isImportant1) {
                mainViewModel.updateIsImportant1(it)
            }
            Spacer(modifier = Modifier.size(8.dp))
            ImportantCheckbox(isImportant2Color, "Не срочно, важно", isImportant2) {
                mainViewModel.updateIsImportant2(it)
            }
            Spacer(modifier = Modifier.size(8.dp))
            ImportantCheckbox(isImportant3Color, "Срочно, не важно", isImportant3) {
                mainViewModel.updateIsImportant3(it)
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = {
                mainViewModel.updateTodo(mainViewModel.todo)
                onBack()
            }) {
                Text(
                    text = "Сохранить задачу",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ImportantCheckbox(
    color: Color,
    impNum: String,
    isImportant: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Star,
            contentDescription = null,
            tint = color,
        )
        Text(
            text = impNum,
            fontFamily = FontFamily.Monospace,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Checkbox(
            checked = isImportant,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = color)
        )
    }
}