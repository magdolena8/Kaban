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
    init {
        this.setHasStableIds(true)
    }

    private lateinit var mListener: OptionsMenuClickListener

    interface OptionsMenuClickListener {
        fun onOptionsMenuClicked(position: Int): Boolean
    }

    fun setOnItemLongClickListener(listener: OptionsMenuClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksAdapter.TaskViewHolder {
        val binding = CardTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: TasksAdapter.TaskViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
//        holder.itemView.setOnLongClickListener{
//        }
    }

    class TaskViewHolder(private val binding: CardTaskBinding, listener: OptionsMenuClickListener) :
//    class TaskViewHolder(private val binding: CardTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: TaskModel) {
            binding.apply {

                textViewTitle.text = task.title
                textViewDescription.text = task.description
                checkboxStatus.isChecked = task.isChecked


//                itemView.setOnLongClickListener{}
            }
            binding.checkboxStatus.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
                    task.checkStatus()
                } else {
                    task.uncheckStatus()
                }
            }

        }

        init {


            itemView.setOnLongClickListener {
                listener.onOptionsMenuClicked(position)
            }
        }
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