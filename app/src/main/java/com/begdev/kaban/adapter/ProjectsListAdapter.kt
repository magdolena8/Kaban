package com.begdev.kaban.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.begdev.kaban.model.ProjectModel
import com.begdev.kaban.databinding.CardProjectBinding

class ProjectsListAdapter :
    ListAdapter<ProjectModel, ProjectsListAdapter.ProjectViewHolder>(DiffCallback()) {
    init {
        setHasStableIds(true)
    }

    private lateinit var mListener: onItemCLickListener

    interface onItemCLickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemCLickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding = CardProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class ProjectViewHolder(private val binding: CardProjectBinding, listener: onItemCLickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(project: ProjectModel) {
            binding.apply {
                textViewTitle.text = project.name

//                textViewCreator.text = project.creator
            }

        }
    init {
        itemView.setOnClickListener{
            listener.onItemClick(adapterPosition)
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

    interface CustomViewHolderListener{
        fun onCustomItemClicked(x : ProjectModel)
    }
}