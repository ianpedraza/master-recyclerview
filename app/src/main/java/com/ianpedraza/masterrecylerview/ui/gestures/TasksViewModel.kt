package com.ianpedraza.masterrecylerview.ui.gestures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianpedraza.masterrecylerview.data.tasks.Task
import com.ianpedraza.masterrecylerview.data.tasks.TasksDummyData
import com.ianpedraza.masterrecylerview.utils.ListExtensions.Companion.swap

class TasksViewModel : ViewModel() {
    private val _data = MutableLiveData<List<Task>>()
    val data: LiveData<List<Task>> = _data

    init {
        fetchData()
    }

    fun fetchData() {
        _data.value = TasksDummyData.tasks.toMutableList().shuffled()
    }

    fun addItemAt(index: Int, task: Task) {
        _data.value = _data.value?.toMutableList()?.apply {
            add(index, task)
        }
    }

    fun removeItemAt(index: Int): Task? {
        val tasks = _data.value ?: return null

        val taskToRemove = tasks[index].copy()

        _data.value = tasks.toMutableList().apply {
            removeAt(index)
        }

        return taskToRemove
    }

    fun swapItems(index1: Int, index2: Int) {
        _data.value = _data.value?.toMutableList()?.apply {
            swap(index1, index2)
        }
    }

    fun moveToEnd(index: Int) {
        _data.value = _data.value?.toMutableList()?.apply {
            val item = get(index).copy()
            remove(item)
            add(item)
        }
    }
}
