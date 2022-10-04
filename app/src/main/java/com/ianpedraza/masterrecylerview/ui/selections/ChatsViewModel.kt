package com.ianpedraza.masterrecylerview.ui.selections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianpedraza.masterrecylerview.data.chats.ChatPreview
import com.ianpedraza.masterrecylerview.data.chats.ChatsDummyData

class ChatsViewModel : ViewModel() {

    private val _data = MutableLiveData<List<ChatPreview>>()
    val data: LiveData<List<ChatPreview>> = _data

    private val _selected = MutableLiveData<List<ChatPreview>>()
    val selected: LiveData<List<ChatPreview>> = _selected

    init {
        fetchData()
    }

    private fun fetchData() {
        _data.value = ChatsDummyData.chats
    }

    fun removeSelection() {
        val removeList = _selected.value ?: emptyList()

        _data.value = _data.value?.toMutableList()?.apply {
            removeAll(removeList)
        }
    }

    fun setSelected(keys: List<Long>) {
        val selection = _data.value?.filter { chat ->
            keys.contains(chat.id)
        } ?: emptyList()

        _selected.value = selection
    }
}
