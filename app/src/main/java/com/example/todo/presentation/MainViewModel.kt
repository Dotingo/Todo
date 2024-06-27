package com.example.todo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.repository.TodoRepository
import com.example.todo.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    var todo by mutableStateOf(Todo(0, "", false, false, false))
        private set

    val getALlTodos = repository.getAllTodos()

    private var deletedTodo: Todo? = null

    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            repository.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            deletedTodo = todo
            repository.deleteTodo(todo)
        }
    }

    fun undoDeletedTodo() {
        deletedTodo?.let { todo ->
            viewModelScope.launch {
                repository.insertTodo(todo)
            }
        }
    }

    fun getTodoById(id: Int) {
        viewModelScope.launch {
            todo = repository.getTodoById(id)
        }
    }

    fun updateTask(newValue: String) {
        todo = todo.copy(task = newValue)
    }

    fun updateIsImportant1(newValue: Boolean) {
        todo = todo.copy(isImportant1 = newValue, isImportant2 = false, isImportant3 = false)
    }

    fun updateIsImportant2(newValue: Boolean) {
        todo = todo.copy(isImportant2 = newValue, isImportant1 = false, isImportant3 = false)
    }

    fun updateIsImportant3(newValue: Boolean) {
        todo = todo.copy(isImportant3 = newValue, isImportant1 = false, isImportant2 = false)
    }
}