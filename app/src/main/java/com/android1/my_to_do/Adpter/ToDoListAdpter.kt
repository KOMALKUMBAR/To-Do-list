package com.android1.my_to_do.Adpter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android1.my_to_do.databinding.TodoLayoutBinding
import com.android1.my_to_do.model.JsonData
import com.android1.my_to_do.model.Todo

class ToDoListAdpter:RecyclerView.Adapter<ToDoListAdpter.ViewHolder>() {

    private var todolist: List<Todo>? = null
    fun setTodoList(todolist: List<Todo>) {
        this.todolist = todolist
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: TodoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       // holder.binding.tvTodo.text = todolist?.get(position)?.todo ?: ""

        if (todolist?.get(position)?.completed!!){
            holder.binding.tvToDo.text = todolist?.get(position)?.todo.plus(" - Completed") ?: ""
        }else{
            holder.binding.tvToDo.text = todolist?.get(position)?.todo.plus(" - Not Completed") ?: ""
        }
       // holder.binding.tvStatus.text = todolist?.get(position)?.completed ?: ""
    }

    override fun getItemCount(): Int {
        return todolist?.size ?: 0
    }
}