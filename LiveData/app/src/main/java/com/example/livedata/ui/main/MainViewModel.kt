package com.example.livedata.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val text = MutableLiveData<String>()

    val post: LiveData<String>
        get() = text

    fun changeData(str: String) {
        if(post.value.isNullOrEmpty()) {
            text.postValue(str)
        } else {
            text.value = str
        }
    }
}