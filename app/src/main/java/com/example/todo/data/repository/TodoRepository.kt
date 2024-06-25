package com.example.todo.data.repository

import com.example.todo.data.local.TodoDao
import com.example.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val dao: TodoDao
) {
    suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    suspend fun updateTodo(todo: Todo) = dao.updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo)

    suspend fun getTodoById(id: Int) = dao.getTodoById(id)

    fun getAllTodos(): Flow<List<Todo>> = dao.getAllTodos()
}