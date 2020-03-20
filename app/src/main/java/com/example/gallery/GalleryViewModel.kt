package com.example.gallery

import android.app.Application
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.coroutines.InternalCoroutinesApi


class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive:LiveData<List<PhotoItem>>
    get() =_photoListLive
    @InternalCoroutinesApi
    fun fetchData(){
        val StringRequest = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                _photoListLive.value=Gson().fromJson(it,Pixabay::class.java).hits.toList()
            },
            Response.ErrorListener {
                Log.d("hello",it.toString())
            }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(StringRequest)
    }
    private fun getUrl():String{
        return "https://pixabay.com/api/?key=15616381-9cc90fa2b131cb4cf27b11e28&q=${keyWords.random()}&per_page=100"
    }
    private val keyWords = arrayListOf("cat","dog","flower","animal","computr","photo","phone")
}