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
import com.ianpedraza.masterrecylerview.R
import com.ianpedraza.masterrecylerview.databinding.FragmentLinearBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.refresh
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.showToast

class LinearFragment : Fragment(), MenuProvider {

    private var _binding: FragmentLinearBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TravelsViewModel by viewModels()

    private val adapter = TravelsAdapter() { action -> onClickListener(action) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLinearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        setupMenu()
        binding.recyclerViewLinear.adapter = adapter
        binding.floatingActionButtonRemove.setOnClickListener { viewModel.removeRandom() }
        binding.root.setOnRefreshListener { refresh() }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun subscribeObservers() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }

        viewModel.isRemoveRandomEnabled.observe(viewLifecycleOwner) {
            binding.floatingActionButtonRemove.isEnabled = it
        }
    }

    private fun refresh() {
        binding.root.refresh { viewModel.fetchData() }
    }

    private fun onClickListener(travelsAction: TravelsAction) = when (travelsAction) {
        is TravelsAction.AdClicked -> showToast("Ad clicked")
        is TravelsAction.CoverClicked -> showToast("Cover clicked")
        is TravelsAction.DescriptionClicked -> showToast("Description clicked")
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.menu, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.menuItemRefresh -> {
            refresh()
            true
        }
        else -> false
    }
}
