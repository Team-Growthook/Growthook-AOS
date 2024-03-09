package com.growthook.aos.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.growthook.aos.domain.entity.Actionplan

@Entity(tableName = "todolist")
data class TodoEntity(
    @PrimaryKey val actionPlanId: Int,
    val content: String,
    val isScraped: Boolean,
    val isFinished: Boolean,
    val hasReview: Boolean,
) {
    companion object {

        fun toTodo(todoList: List<TodoEntity>) = todoList.map { todo ->
            Actionplan(
                todo.actionPlanId,
                todo.content,
                todo.isScraped,
                todo.isFinished,
                todo.hasReview,
            )
        }
    }
}
