package com.begdev.kaban.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.begdev.kaban.NewTableViewModel
import com.begdev.kaban.databinding.CardProjectBinding
import com.begdev.kaban.databinding.CardTableNewBinding
import com.begdev.kaban.model.ProjectModel

class NewTablesAdapter: ListAdapter<String, NewTablesAdapter.NewTablesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewTablesAdapter.NewTablesViewHolder {

        //TODO изменить CardProjectBinding на нормальный View для tables
        val binding = CardTableNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        notifyDataSetChanged()
        return NewTablesAdapter.NewTablesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewTablesAdapter.NewTablesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

    }

    class NewTablesViewHolder(private val binding: CardTableNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newTableName: String) {
            binding.apply {
                textViewTitle.text = newTableName
//                textViewCreator.text = project.creator
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}