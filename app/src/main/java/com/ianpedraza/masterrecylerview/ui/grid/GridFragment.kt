package com.ianpedraza.masterrecylerview.ui.grid

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
import androidx.recyclerview.widget.GridLayoutManager
import com.ianpedraza.masterrecylerview.R
import com.ianpedraza.masterrecylerview.databinding.FragmentGridBinding
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.refresh
import com.ianpedraza.masterrecylerview.utils.ViewExtensions.Companion.showToast

class GridFragment : Fragment(), MenuProvider {

    private var _binding: FragmentGridBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PhotosGridViewModel by viewModels()

    private lateinit var adapter: PhotosGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGridBinding.inflate(inflater, container, false)
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

        binding.floatingActionButtonGridPhotos.setOnClickListener {
            viewModel.removeFirst()
        }

        binding.root.setOnRefreshListener { refresh() }
    }

    private fun setupRecyclerView() {
        adapter = PhotosGridAdapter() { action -> onAction(action) }

        val manager = GridLayoutManager(requireActivity(), 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) = when {
                    position % 10 == 0 -> 3
                    else -> 1
                }
            }
        }

        binding.recyclerViewGridPhotos.apply {
            this.adapter = this@GridFragment.adapter
            layoutManager = manager
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
            binding.floatingActionButtonGridPhotos.isEnabled = showRemoveButton
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun refresh() {
        binding.root.refresh { viewModel.fetchData() }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.menuItemRefresh -> {
            refresh()
            true
        }
        else -> false
    }
}
