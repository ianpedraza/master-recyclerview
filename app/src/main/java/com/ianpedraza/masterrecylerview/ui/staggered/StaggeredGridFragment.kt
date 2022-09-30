package com.ianpedraza.masterrecylerview.ui.staggered

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ianpedraza.masterrecylerview.databinding.FragmentStaggeredGridBinding

class StaggeredGridFragment : Fragment() {

    private var _binding: FragmentStaggeredGridBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaggeredGridBinding.inflate(inflater, container, false)
        return binding.root
    }
}
