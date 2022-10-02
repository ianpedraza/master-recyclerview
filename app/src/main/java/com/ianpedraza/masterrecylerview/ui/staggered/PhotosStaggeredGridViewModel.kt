package com.ianpedraza.masterrecylerview.ui.staggered

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ianpedraza.masterrecylerview.data.photos.Photo
import com.ianpedraza.masterrecylerview.data.photos.PhotosPicsumDummyData
import com.ianpedraza.masterrecylerview.utils.ListExtensions.Companion.swap

class PhotosStaggeredGridViewModel : ViewModel() {

    private val _data = MutableLiveData<List<Photo>>()
    val data: LiveData<List<Photo>> = _data

    val showRemoveButton: LiveData<Boolean> = Transformations.map(_data) { photos ->
        photos.isNotEmpty()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        _data.value = PhotosPicsumDummyData.photos
    }

    fun removeFirst() {
        _data.value = _data.value?.toMutableList()?.apply {
            removeAt(0)
        }
    }

    fun swapItems(index1: Int, index2: Int) {
        _data.value = _data.value?.toMutableList()?.apply {
            swap(index1, index2)
        }
    }
}
