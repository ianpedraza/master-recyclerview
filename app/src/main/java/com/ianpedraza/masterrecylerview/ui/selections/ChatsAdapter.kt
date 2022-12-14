package com.ianpedraza.masterrecylerview.ui.selections

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ianpedraza.masterrecylerview.data.chats.ChatPreview
import com.ianpedraza.masterrecylerview.databinding.ItemChatBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.goneIf
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.loadCircleImageByUrl

class ChatsAdapter(
    private val onAction: (ChatsAction) -> Unit
) : ListAdapter<ChatPreview, ChatsAdapter.ViewHolder>(ChatsDiffCallback) {

    var tracker: SelectionTracker<Long>? = null

    init {
        // DON'T-USE: Could be used only for Long type id to avoid create Custom-KeyProvider and use StableIdKeyProvider
        // setHasStableIds(true)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        tracker?.let { tracker ->
            val item = getItem(position)
            holder.bind(item, tracker.isSelected(item.id), onAction)
        }
    }

    // DON'T-USE: Could be used only for Long type id to avoid create Custom-KeyProvider and use StableIdKeyProvider
    // override fun getItemId(position: Int): Long = getItem(position).id

    inner class ViewHolder(
        private val binding: ItemChatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: ChatPreview,
            selected: Boolean,
            onAction: (ChatsAction) -> Unit
        ) {
            with(binding) {
                root.isActivated = selected
                root.setOnClickListener { onAction(ChatsAction.OnClick(item)) }
                imageViewChat.loadCircleImageByUrl(item.image)
                textViewChatName.text = item.name
                textViewChatMessage.text = item.message
                imageViewChatSelected.goneIf(!selected)
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long = getItem(bindingAdapterPosition).id
            }
    }
}

private object ChatsDiffCallback : DiffUtil.ItemCallback<ChatPreview>() {
    override fun areItemsTheSame(
        oldItem: ChatPreview,
        newItem: ChatPreview
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ChatPreview,
        newItem: ChatPreview
    ): Boolean = oldItem == newItem
}

/* Create custom ItemKeyProvider if id is a String */
class ChatsKeyProvider(
    private val adapter: ChatsAdapter
) : ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long = adapter.currentList[position].id

    override fun getPosition(key: Long): Int = adapter.currentList.indexOfFirst { it.id == key }
}

class ChatDetailsLookup(
    private val recyclerView: RecyclerView
) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)

        return if (view != null) {
            (recyclerView.getChildViewHolder(view) as ChatsAdapter.ViewHolder).getItemDetails()
        } else {
            null
        }
    }
}

sealed interface ChatsAction {
    data class OnClick(val chat: ChatPreview) : ChatsAction
}
