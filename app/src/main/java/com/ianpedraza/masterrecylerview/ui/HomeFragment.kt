package com.ianpedraza.masterrecylerview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ianpedraza.masterrecylerview.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.apply {
            textViewLinearOption.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLinearFragment())
            }

            textViewGridOption.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGridFragment())
            }

            textViewStaggeredGridOption.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStaggeredGridFragment())
            }

            textViewMultipleListenersOption.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMultiListenersFragment())
            }

            textViewGesturesOption.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGesturesFragment())
            }

            textViewSelectionOption.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSelectionsFragment())
            }

            textViewPaginationOption.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPagingFragment())
            }

            textViewSearchOption.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }
        }
    }
}
