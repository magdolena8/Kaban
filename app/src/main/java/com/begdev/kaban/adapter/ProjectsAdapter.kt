package com.begdev.kaban.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.begdev.kaban.ProjectViewModel
import com.begdev.kaban.model.ProjectModel
import com.begdev.kaban.databinding.CardProjectBinding

class ProjectsAdapter : ListAdapter<ProjectModel, ProjectsAdapter.ProjectViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = CardProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ProjectViewHolder(private val binding: CardProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(project: ProjectModel) {
            binding.apply {
                textViewName.text = project.name
//                textViewCreator.text = project.creator
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ProjectModel>() {
        override fun areItemsTheSame(oldItem: ProjectModel, newItem: ProjectModel): Boolean {
            return oldItem.name == newItem.name             //TODO : need check not names but IDs
        }

        override fun areContentsTheSame(oldItem: ProjectModel, newItem: ProjectModel): Boolean {
            return oldItem == newItem
        }
    }
}