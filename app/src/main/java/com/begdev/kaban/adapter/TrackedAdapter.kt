package com.begdev.kaban.adapter

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.begdev.kaban.R
import com.begdev.kaban.databinding.CardTrackedBinding
import com.begdev.kaban.model.TrackedModel
import java.text.SimpleDateFormat

class TrackedAdapter:
    ListAdapter<TrackedModel, TrackedAdapter.TrackedViewHolder>(TrackedAdapter.DiffCallback()) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackedAdapter.TrackedViewHolder {
        val binding = CardTrackedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackedAdapter.TrackedViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: TrackedAdapter.TrackedViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
//        holder.itemView.setOnLongClickListener{
//        }
    }

    class TrackedViewHolder(private val binding: CardTrackedBinding, listener: TrackedAdapter.OptionsMenuClickListener) :
//    class TaskViewHolder(private val binding: CardTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tracked: TrackedModel) {
            binding.apply {
                textViewTitle.text = tracked!!.task!!.title
                textViewDescription.text = tracked.task!!.description
//                val formatter: Formatter =
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                textViewDeadline.text = formatter.format(tracked.deadline)
                textViewTitle.background = ColorDrawable(123)
//                cardRoot.background = ColorDrawable.
//                val colorRed: Int = ContextCompat.getColor(root.context, R.color.red));
                when(tracked.color){
//                    "green" -> cardRoot.background = getColor(R.color.green)
                    "green" -> cardRoot.setBackgroundColor(binding.root.context.getColor(R.color.green))
                    "yellow" -> cardRoot.setBackgroundColor(binding.root.context.getColor(R.color.yellow))
                    "red" -> cardRoot.setBackgroundColor(binding.root.context.getColor(R.color.red))
                    "" -> cardRoot.setBackgroundColor(binding.root.context.getColor(R.color.black))
//                    null -> cardRoot.setBackgroundColor(binding.root.context.getColor(R.color.black))
                }


//                itemView.setOnLongClickListener{}
            }
//            binding.checkboxStatus.setOnCheckedChangeListener { buttonView, isChecked ->
//
//                if (isChecked) {
//                    task.checkStatus()
//                } else {
//                    task.uncheckStatus()
//                }
//            }

        }

        init {
            itemView.setOnLongClickListener {
                listener.onOptionsMenuClicked(position)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TrackedModel>() {
        override fun areItemsTheSame(oldItem: TrackedModel, newItem: TrackedModel): Boolean {
            return oldItem.task!!.path == newItem.task!!.path             //TODO : need check not names but IDs
        }

        override fun areContentsTheSame(oldItem: TrackedModel, newItem: TrackedModel): Boolean {
            return oldItem.task!!.path == newItem.task!!.path
        }
    }

}