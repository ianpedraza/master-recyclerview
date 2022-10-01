package com.ianpedraza.masterrecylerview.ui.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ianpedraza.masterrecylerview.data.photos.Photo
import com.ianpedraza.masterrecylerview.data.photos.PhotosDummyData

class PhotosGridViewModel : ViewModel() {

    private val _data = MutableLiveData<List<Photo>>()
    val data: LiveData<List<Photo>> = _data

    init {
        fetchData()
    }

    fun fetchData() {
        _data.value = PhotosDummyData.photos
    }

    fun removeFirst() {
        _data.value = _data.value?.toMutableList()?.apply {
            removeAt(0)
        }
    }
}
