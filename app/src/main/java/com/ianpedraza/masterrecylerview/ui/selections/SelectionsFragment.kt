package com.ianpedraza.masterrecylerview.ui.selections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianpedraza.masterrecylerview.R
import com.ianpedraza.masterrecylerview.databinding.FragmentSelectionsBinding
import com.ianpedraza.masterrecylerview.ui.MainActivity

class SelectionsFragment : Fragment(), ActionMode.Callback, MenuProvider {

    private var _binding: FragmentSelectionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatsViewModel by viewModels()

    private lateinit var adapter: ChatsAdapter
    private var tracker: SelectionTracker<Long>? = null
    private var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectionsBinding.inflate(inflater, container, false)

        if (savedInstanceState != null) {
            tracker?.onRestoreInstanceState(savedInstanceState)
        }

        setupUI()
        subscribeObservers()

        return binding.root
    }

    private fun setupUI() {
        setupRecyclerView()
        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        adapter = ChatsAdapter()

        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val decoration = DividerItemDecoration(requireContext(), manager.orientation)

        binding.recyclerViewChats.apply {
            layoutManager = manager
            this.adapter = this@SelectionsFragment.adapter
            addItemDecoration(decoration)
        }

        tracker = SelectionTracker.Builder(
            CHAT_SELECTION,
            binding.recyclerViewChats,
            ChatsKeyProvider(adapter),
            // StableIdKeyProvider(binding.recyclerViewChats),
            ChatDetailsLookup(binding.recyclerViewChats),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
            // SelectionPredicates.createSelectSingleAnything()
            // CustomStrategy
        ).build()

        adapter.tracker = tracker
    }

    private fun subscribeObservers() {
        viewModel.selected.observe(viewLifecycleOwner) { selected ->
            onItemAction(selected.size)
        }

        viewModel.data.observe(viewLifecycleOwner) { chats ->
            adapter.submitList(chats)
        }

        tracker?.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()
                val items = tracker?.selection!!.toList()
                viewModel.setSelected(items)
            }
        })
    }

    private object CustomStrategy : SelectionTracker.SelectionPredicate<Long>() {
        override fun canSetStateForKey(
            key: Long,
            nextState: Boolean
        ): Boolean = true

        override fun canSetStateAtPosition(
            position: Int,
            nextState: Boolean
        ): Boolean = true

        override fun canSelectMultiple(): Boolean = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
    }

    companion object {
        private const val CHAT_SELECTION = "chatSelection"
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_actions, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        // Called each time the action mode is shown
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean =
        when (item?.itemId) {
            R.id.action_delete -> {
                viewModel.removeSelection()
                true
            }
            R.id.action_select_all -> {
                val keys = adapter.currentList.map { it.id }
                tracker?.setItemsSelected(keys, true)
                true
            }
            else -> false
        }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        tracker?.clearSelection()
    }

    private fun onItemAction(size: Int) {
        if (actionMode == null) {
            val currentActivity = activity as MainActivity
            actionMode = currentActivity.startSupportActionMode(this@SelectionsFragment)
        }

        if (size > 0) {
            actionMode?.title = getString(R.string.action_selected, size)
        } else {
            actionMode?.finish()
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
