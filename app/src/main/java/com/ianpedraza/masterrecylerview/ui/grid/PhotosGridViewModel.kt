package com.ianpedraza.masterrecylerview.ui.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ianpedraza.masterrecylerview.data.photos.Photo
import com.ianpedraza.masterrecylerview.data.photos.PhotosFlickerDummyData

class PhotosGridViewModel : ViewModel() {

    private val _data = MutableLiveData<List<Photo>>()
    val data: LiveData<List<Photo>> = _data

    val showRemoveButton: LiveData<Boolean> = Transformations.map(_data) { photos ->
        photos.isNotEmpty()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        _data.value = PhotosFlickerDummyData.photos
    }

    fun removeFirst() {
        _data.value = _data.value?.toMutableList()?.apply {
            removeAt(0)
        }
    }
}
