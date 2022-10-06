package com.ianpedraza.masterrecylerview.ui.multilisteners

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.masterrecylerview.databinding.ItemContactsBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.loadCircleImageByUrl

class ContactsAdapter :
    ListAdapter<ContactsViewModel.ContactUiState, ContactsAdapter.ViewHolder>(ContactsDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    class ViewHolder private constructor(
        private val binding: ItemContactsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemContactsBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: ContactsViewModel.ContactUiState) {
            with(binding) {
                imageViewItemContacts.loadCircleImageByUrl(item.photo)
                textViewItemContactsName.text = item.name
                root.setOnClickListener { item.onClick() }
                imageButtonItemContactsPhone.setOnClickListener { item.onCall() }
                imageButtonItemContactsMessage.setOnClickListener { item.onMessage() }
                imageButtonItemContactsVideoCall.setOnClickListener { item.onVideoCall() }
            }
        }
    }
}

private object ContactsDiffUtil : DiffUtil.ItemCallback<ContactsViewModel.ContactUiState>() {
    override fun areItemsTheSame(
        oldItem: ContactsViewModel.ContactUiState,
        newItem: ContactsViewModel.ContactUiState
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ContactsViewModel.ContactUiState,
        newItem: ContactsViewModel.ContactUiState
    ): Boolean = oldItem == newItem
}
