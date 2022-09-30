package com.ianpedraza.masterrecylerview.ui.linear

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianpedraza.masterrecylerview.R
import com.ianpedraza.masterrecylerview.databinding.FragmentLinearBinding

class LinearFragment : Fragment(), MenuProvider {

    private var _binding: FragmentLinearBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TravelsViewModel by viewModels()

    private val adapter = TravelsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLinearBinding.inflate(inflater, container, false)
        setupUI()
        subscribeObservers()
        return binding.root
    }

    private fun setupUI() {
        setupMenu()
        setupRecyclerView()

        binding.floatingActionButtonRemove.setOnClickListener { viewModel.removeRandom() }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val decoration = DividerItemDecoration(requireContext(), manager.orientation)

        binding.recyclerViewLinear.apply {
            adapter = this@LinearFragment.adapter
            layoutManager = manager
            addItemDecoration(decoration)
        }
    }

    private fun subscribeObservers() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }

        viewModel.isRemoveRandomEnabled.observe(viewLifecycleOwner) {
            binding.floatingActionButtonRemove.isEnabled = it
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.menu, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.menuItemRefresh -> {
            viewModel.fetchData()
            true
        }
        else -> false
    }
}
