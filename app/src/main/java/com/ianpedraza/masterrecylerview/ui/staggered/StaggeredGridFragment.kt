package com.ianpedraza.masterrecylerview.ui.staggered

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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ianpedraza.masterrecylerview.R
import com.ianpedraza.masterrecylerview.databinding.FragmentStaggeredGridBinding
import com.ianpedraza.masterrecylerview.ui.grid.PhotosAction
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.refresh
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.showToast

class StaggeredGridFragment : Fragment(), MenuProvider {

    private var _binding: FragmentStaggeredGridBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhotosStaggeredGridViewModel by viewModels()

    private lateinit var adapter: PhotosStaggeredGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaggeredGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        setupRecyclerView()
        setupMenu()

        binding.floatingActionButtonStaggeredGrid.setOnClickListener {
            viewModel.removeFirst()
        }

        binding.root.setOnRefreshListener { refresh() }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        adapter = PhotosStaggeredGridAdapter() { action -> onAction(action) }

        val layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }

        binding.recyclerViewStaggeredGrid.apply {
            this.adapter = this@StaggeredGridFragment.adapter
            this.layoutManager = layoutManager
        }
    }

    private fun onAction(action: PhotosAction) {
        when (action) {
            is PhotosAction.OnClick -> showToast("Photo clicked")
        }
    }

    private fun subscribeObservers() {
        viewModel.data.observe(viewLifecycleOwner) { photos ->
            adapter.submitList(photos)
        }

        viewModel.showRemoveButton.observe(viewLifecycleOwner) { showRemoveButton ->
            binding.floatingActionButtonStaggeredGrid.isEnabled = showRemoveButton
        }
    }

    private fun refresh() {
        binding.root.refresh { viewModel.fetchData() }
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
