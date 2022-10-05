package com.ianpedraza.masterrecylerview.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ianpedraza.masterrecylerview.R
import com.ianpedraza.masterrecylerview.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    private val viewModel: PokemonSearchViewModel by viewModels()

    private lateinit var adapter: PokemonSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupUI()
        subscribeObservers()
        return binding.root
    }

    private fun setupUI() {
        setupMenu()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val manager = GridLayoutManager(requireContext(), 2)
        adapter = PokemonSearchAdapter()

        binding.recyclerViewPokemonSearch.apply {
            this.layoutManager = manager
            this.adapter = this@SearchFragment.adapter
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.pokemonList.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                binding.progressIndicatorPokemonSearch.isVisible = true
            } else {
                binding.progressIndicatorPokemonSearch.isVisible = false

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                errorState?.let {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.menu_search, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.action_search -> {
            (menuItem.actionView as SearchView).setOnQueryTextListener(this)
            true
        }
        else -> false
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(query: String?): Boolean {
        viewModel.searchPokemon(query)
        return true
    }
}
