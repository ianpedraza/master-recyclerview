package com.ianpedraza.masterrecylerview.ui.multilisteners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianpedraza.masterrecylerview.data.contacts.Contact
import com.ianpedraza.masterrecylerview.data.contacts.ContactsDummyData

class ContactsViewModel : ViewModel() {

    private val _contactsList = MutableLiveData<List<ContactUiState>>()
    val contactsList: LiveData<List<ContactUiState>> get() = _contactsList

    init {
        fetchData()
    }

    fun fetchData() {
        _contactsList.value = ContactsDummyData.contacts.map { it.toUiState() }
    }

    private fun Contact.toUiState(): ContactUiState {
        return ContactUiState(
            id = this.id,
            name = this.name,
            photo = this.photo,
            onCall = { onCall(this) },
            onMessage = { onMessage(this) },
            onVideoCall = { onVideoCall(this) }
        )
    }

    private fun onCall(contact: Contact) {
        Log.i(TAG, "action:onCall:${contact.name}")
    }

    private fun onMessage(contact: Contact) {
        Log.i(TAG, "action:onMessage:${contact.name}")
    }

    private fun onVideoCall(contact: Contact) {
        Log.i(TAG, "action:onVideoCall:${contact.name}")
    }

    data class ContactUiState(
        val id: Long,
        val name: String,
        val photo: String,
        val onCall: () -> Unit,
        val onMessage: () -> Unit,
        val onVideoCall: () -> Unit
    )

    companion object {
        private const val TAG = "ContactsViewModel"
    }
}
