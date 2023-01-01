package com.begdev.kaban.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.begdev.kaban.databinding.CardProjectBinding
import com.begdev.kaban.databinding.CardTableBinding
import com.begdev.kaban.model.ProjectModel
import com.begdev.kaban.model.TableModel

class TablesAdapter:
    ListAdapter<TableModel, TablesAdapter.TableViewHolder>(DiffCallback()) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TablesAdapter.TableViewHolder {
        val binding = CardTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TableViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: TablesAdapter.TableViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class TableViewHolder(private val binding: CardTableBinding, listener: onItemCLickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(table: TableModel) {
            binding.apply {
                textViewTitle.text = table.title
                textViewDescription.text = table.descriptor
                textViewTasksCount.text = table.tasksCount.toString()
//                textViewDescription = table.descriptor
//                textViewName.text = "qweqwe"
            }

        }
        init {

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<TableModel>() {

        override fun areItemsTheSame(oldItem: TableModel, newItem: TableModel): Boolean {
            return oldItem == newItem             //TODO : need check not names but IDs
        }

        override fun areContentsTheSame(oldItem: TableModel, newItem: TableModel): Boolean {
            return oldItem == newItem
        }
    }
}