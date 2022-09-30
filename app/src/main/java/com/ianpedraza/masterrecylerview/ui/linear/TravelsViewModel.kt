package com.ianpedraza.masterrecylerview.ui.linear

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ianpedraza.masterrecylerview.data.travels.TravelsDummyData

class TravelsViewModel : ViewModel() {
    private val _data = MutableLiveData<List<TravelsDataItem>>()
    val data: LiveData<List<TravelsDataItem>> = _data

    val isRemoveRandomEnabled: LiveData<Boolean> = Transformations.map(_data) {
        it.isNotEmpty()
    }

    init {
        fetchData()
    }

    fun fetchData() {
        val ads = TravelsDummyData.ads.map { ad ->
            TravelsDataItem.AdItem(ad)
        }.toTypedArray()

        val travels = TravelsDummyData.covers.map { cover ->
            TravelsDataItem.CoverItem(cover)
        }.toTypedArray()

        val descriptions = TravelsDummyData.descriptions.map { description ->
            TravelsDataItem.DescriptionItem(description)
        }.toTypedArray()

        _data.value = listOf(*ads, *travels, *descriptions).shuffled()
    }

    fun removeRandom() {
        _data.value = _data.value?.toMutableList().apply {
            _data.value?.random()?.let { this?.remove(it) }
        }?.toList()
    }
}
