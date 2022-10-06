package com.ianpedraza.masterrecylerview.ui.multilisteners

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianpedraza.masterrecylerview.databinding.FragmentMultiListenersBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.showToast

class MultiListenersFragment : Fragment() {
    private var _binding: FragmentMultiListenersBinding? = null
    private val binding: FragmentMultiListenersBinding get() = _binding!!

    private val viewModel: ContactsViewModel by viewModels()
    private lateinit var adapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMultiListenersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(requireContext(), manager.orientation)
        adapter = ContactsAdapter()

        binding.recyclerViewContacts.apply {
            this.adapter = this@MultiListenersFragment.adapter
            layoutManager = manager
            addItemDecoration(itemDecoration)
        }
    }

    private fun subscribeObservers() {
        viewModel.contactsList.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
        }

        viewModel.onCallAction.observe(viewLifecycleOwner) { contact ->
            contact?.let {
                showToast("Call: ${contact.phone}")
                viewModel.onContactCalled()
            }
        }

        viewModel.onMessageAction.observe(viewLifecycleOwner) { contact ->
            contact?.let {
                showToast("Message: ${contact.phone}")
                viewModel.onContactMessaged()
            }
        }

        viewModel.onVideoCallAction.observe(viewLifecycleOwner) { contact ->
            contact?.let {
                showToast("VideoCall: ${contact.phone}")
                viewModel.onContactVideoCalled()
            }
        }

        viewModel.onNavigateToContact.observe(viewLifecycleOwner) { contact ->
            contact?.let {
                showToast("Navigate to: ${contact.name}")
                viewModel.onContactNavigated()
            }
        }
    }
}
