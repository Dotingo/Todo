package com.example.todo.presentation.home_screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.domain.model.Todo
import com.example.todo.presentation.common.ExpandableText
import com.example.todo.ui.theme.isImportant1Color
import com.example.todo.ui.theme.isImportant2Color
import com.example.todo.ui.theme.isImportant3Color

@Composable
fun TodoCard(
    todo: Todo,
    onDone: () -> Unit,
    onUpdate: (id: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onDone() },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(imageVector = Icons.Rounded.Done, contentDescription = null)
            }
            ExpandableText(
                text = todo.task,
                modifier = Modifier
                    .weight(8f),
                fontSize = 22.sp,
                textModifier = Modifier.padding(3.dp)
            )
            if (todo.isImportant1) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = isImportant1Color,
                    modifier = Modifier
                        .weight(1f)
                )
            } else if (todo.isImportant2) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = isImportant2Color,
                    modifier = Modifier
                        .weight(1f)
                )
            } else if (todo.isImportant3) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = isImportant3Color,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            IconButton(
                onClick = { onUpdate(todo.id) },
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
            }
        }
    }
}