package com.rafiul.whatcanido.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafiul.whatcanido.data.source.Task
import com.rafiul.whatcanido.databinding.ItemTaskBinding
import com.rafiul.whatcanido.ui.task.TaskViewModel

class TaskAdapter (private val viewModel: TaskViewModel) : ListAdapter<Task,TaskAdapter.ViewHolder>(TaskDiffUtils()) {

     class ViewHolder private constructor(private val binding:ItemTaskBinding) : RecyclerView.ViewHolder(binding.root){
         fun bind(viewModel: TaskViewModel,task: Task){
             binding.viewmodel = viewModel
             binding.task = task
         }
         companion object{
             fun from(parent: ViewGroup) : ViewHolder{
                 val layoutInflater = LayoutInflater.from(parent.context)
                 val binding = ItemTaskBinding.inflate(layoutInflater,parent,false)
                 return ViewHolder(binding)
             }
         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind(viewModel,getItem(position))
}

class TaskDiffUtils : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
       return oldItem.id == newItem.id
    }

}
