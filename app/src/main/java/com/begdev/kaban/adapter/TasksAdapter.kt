package com.begdev.kaban.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.begdev.kaban.databinding.CardTaskBinding
import com.begdev.kaban.model.TaskModel

class TasksAdapter :
    ListAdapter<TaskModel, TasksAdapter.TaskViewHolder>(DiffCallback()) {
    init{
        this.setHasStableIds(true)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksAdapter.TaskViewHolder {
        val binding = CardTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TasksAdapter.TaskViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

//    override fun getItemId(position: Int): Long {
//        val task: TaskModel = currentList.get(position)
//        return task.
//    }


    //    class TaskViewHolder(private val binding: CardTaskBinding, listener: onItemCLickListener) :
    class TaskViewHolder(private val binding: CardTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskModel) {
            binding.apply {

                textViewTitle.text = task.title
                textViewDescription.text = task.description
                checkboxStatus.isChecked = task.isChecked
//                text_view_created = task
//                textViewTasksCount.text = table.tasksCount.toString()
//                textViewDescription = table.descriptor
//                textViewName.text = "qweqwe"
            }
            binding.checkboxStatus.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
                    task.checkStatus()
                } else {
                    task.uncheckStatus()
                }
            }
        }
//        init {
//            itemView.setOnClickListener{
//                listener.onItemClick(adapterPosition)
//            }
//        }

    }


    class DiffCallback : DiffUtil.ItemCallback<TaskModel>() {
        override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem.key == newItem.key             //TODO : need check not names but IDs
        }

        override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem.key == newItem.key
        }
    }
}