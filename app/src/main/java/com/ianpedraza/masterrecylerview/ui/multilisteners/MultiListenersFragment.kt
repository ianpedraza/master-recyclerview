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

        setupUI()
        subscribeObservers()

        return binding.root
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
    }
}
