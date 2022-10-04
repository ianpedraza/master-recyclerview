package com.ianpedraza.masterrecylerview.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ianpedraza.masterrecylerview.databinding.FragmentPagingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PagingFragment : Fragment() {

    private var _binding: FragmentPagingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokemonViewModel by viewModels()

    private lateinit var adapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagingBinding.inflate(inflater, container, false)
        setupRecyclerView()
        subscribeObservers()
        return binding.root
    }

    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            /* viewModel.pokemon.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            } */

            viewModel.pokemonList.observe(viewLifecycleOwner) { pagingData ->
                viewLifecycleOwner.lifecycleScope.launch {
                    adapter.submitData(pagingData)
                }
            }
        }

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                binding.progressIndicatorPokemon.isVisible = true
            } else {
                binding.progressIndicatorPokemon.isVisible = false

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

    private fun setupRecyclerView() {
        val manager = GridLayoutManager(requireContext(), 2)
        adapter = PokemonAdapter()

        binding.recyclerViewPokemon.apply {
            this.layoutManager = manager
            this.adapter = this@PagingFragment.adapter
        }
    }
}
