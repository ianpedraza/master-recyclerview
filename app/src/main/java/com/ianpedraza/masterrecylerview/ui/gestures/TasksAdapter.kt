package com.ianpedraza.masterrecylerview.ui.gestures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.masterrecylerview.data.tasks.Task
import com.ianpedraza.masterrecylerview.databinding.ItemTaskBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.strikeThru

class TasksAdapter(
    private val clickListener: TasksClickListener
) : ListAdapter<Task, TasksAdapter.ViewHolder>(TasksDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) = holder.bind(getItem(position), clickListener)

    class ViewHolder private constructor(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(
            item: Task,
            clickListener: TasksClickListener
        ) {
            with(binding) {
                textViewTaskTitle.text = item.title

                checkboxTask.setOnCheckedChangeListener { _, isChecked ->
                    textViewTaskTitle.strikeThru(isChecked)
                }

                root.setOnClickListener {
                    checkboxTask.toggle()
                    clickListener.onClick(item)
                }

                checkboxTask.isChecked = item.done
            }
        }
    }
}

class TasksClickListener(val clickListener: (taskId: String) -> Unit) {
    fun onClick(task: Task) = clickListener(task.id)
}

private object TasksDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(
        oldItem: Task,
        newItem: Task
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Task,
        newItem: Task
    ): Boolean = oldItem == newItem
}
