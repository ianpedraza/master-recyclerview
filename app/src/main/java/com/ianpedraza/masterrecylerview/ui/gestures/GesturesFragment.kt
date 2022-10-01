package com.ianpedraza.masterrecylerview.ui.gestures

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ianpedraza.masterrecylerview.R
import com.ianpedraza.masterrecylerview.databinding.FragmentGesturesBinding
import com.ianpedraza.masterrecylerview.domain.helpers.gestures.RecyclerViewDragDataHelper
import com.ianpedraza.masterrecylerview.domain.helpers.gestures.RecyclerViewSwipeDataHelper
import com.ianpedraza.masterrecylerview.domain.helpers.gestures.RecyclerViewSwipeDataHelper.SwipeHolder

class GesturesFragment : Fragment(), MenuProvider {

    private var _binding: FragmentGesturesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TasksViewModel by viewModels()

    private val adapter by lazy { TasksAdapter(tasksClickListener) }

    private val tasksClickListener = TasksClickListener { taskId ->
        Toast.makeText(requireContext(), taskId, Toast.LENGTH_SHORT).show()
    }

    private val startHolder by lazy {
        SwipeHolder(
            backgroundColor = ColorDrawable(Color.LTGRAY),
            icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_archive)!!,
            iconColor = Color.DKGRAY
        )
    }

    private val endHolder by lazy {
        SwipeHolder(
            backgroundColor = ColorDrawable(Color.RED),
            icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)!!,
            iconColor = Color.WHITE
        )
    }

    private val swipeGestures by lazy {
        object : RecyclerViewSwipeDataHelper(startHolder, endHolder) {
            override fun onSwipeStart(viewHolder: RecyclerView.ViewHolder) =
                removeTask(viewHolder.adapterPosition)

            override fun onSwipeEnd(viewHolder: RecyclerView.ViewHolder) =
                viewModel.moveToEnd(viewHolder.adapterPosition)
        }
    }

    private val dragGestures by lazy {
        object : RecyclerViewDragDataHelper() {
            override fun onDrag(
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = viewModel.swapItems(viewHolder.adapterPosition, target.adapterPosition)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGesturesBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        return binding.root
    }

    private fun setupUI() {
        setupMenu()
        setupRecyclerView()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val decoration = DividerItemDecoration(requireContext(), manager.orientation)

        binding.recyclerViewGestures.apply {
            adapter = this@GesturesFragment.adapter
            layoutManager = manager
            addItemDecoration(decoration)
            ItemTouchHelper(swipeGestures).attachToRecyclerView(this)
            ItemTouchHelper(dragGestures).attachToRecyclerView(this)
        }
    }

    private fun subscribeObservers() {
        viewModel.data.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }
    }

    private fun removeTask(index: Int) {
        viewModel.removeItemAt(index)?.let { task ->
            Snackbar.make(binding.root, "1 deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo") { viewModel.addItemAt(index, task) }
                .show()
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
