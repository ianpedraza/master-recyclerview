package com.ianpedraza.masterrecylerview.ui.multilisteners

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianpedraza.masterrecylerview.data.contacts.Contact
import com.ianpedraza.masterrecylerview.data.contacts.ContactsDummyData

class ContactsViewModel : ViewModel() {

    private val _contactsList = MutableLiveData<List<ContactUiState>>()
    val contactsList: LiveData<List<ContactUiState>> get() = _contactsList

    private val _onCallAction = MutableLiveData<Contact?>()
    val onCallAction: LiveData<Contact?> get() = _onCallAction

    private val _onMessageAction = MutableLiveData<Contact?>()
    val onMessageAction: LiveData<Contact?> get() = _onMessageAction

    private val _onVideoCallAction = MutableLiveData<Contact?>()
    val onVideoCallAction: LiveData<Contact?> get() = _onVideoCallAction

    private val _onNavigateToContact = MutableLiveData<Contact?>()
    val onNavigateToContact: LiveData<Contact?> get() = _onNavigateToContact

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
            onClick = { onNavigateToContact(this) },
            onCall = { onCall(this) },
            onMessage = { onMessage(this) },
            onVideoCall = { onVideoCall(this) }
        )
    }

    private fun onCall(contact: Contact) {
        _onCallAction.value = contact
    }

    private fun onMessage(contact: Contact) {
        _onMessageAction.value = contact
    }

    private fun onVideoCall(contact: Contact) {
        _onVideoCallAction.value = contact
    }

    private fun onNavigateToContact(contact: Contact) {
        _onNavigateToContact.value = contact
    }

    fun onContactCalled() {
        _onCallAction.value = null
    }

    fun onContactMessaged() {
        _onMessageAction.value = null
    }

    fun onContactVideoCalled() {
        _onVideoCallAction.value = null
    }

    fun onContactNavigated() {
        _onNavigateToContact.value = null
    }

    data class ContactUiState(
        val id: Long,
        val name: String,
        val photo: String,
        val onClick: () -> Unit,
        val onCall: () -> Unit,
        val onMessage: () -> Unit,
        val onVideoCall: () -> Unit
    )
}
