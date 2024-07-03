package com.imranmelikov.codsoft_quoteoftheday.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.imranmelikov.codsoft_quoteoftheday.api.Instance
import com.imranmelikov.codsoft_quoteoftheday.api.Quote
import com.imranmelikov.codsoft_quoteoftheday.db.QuoteDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteViewModel(application: Application):AndroidViewModel(application) {
    private val db=Room.databaseBuilder(application.applicationContext,QuoteDatabase::class.java,"QuoteDb").build()
    private val dao=db.dao()

    private val quoteMutableLiveData=MutableLiveData<Quote>()
    val quoteLiveData:LiveData<Quote>
        get() = quoteMutableLiveData

    private val quoteLocalMutableLiveData=MutableLiveData<List<com.imranmelikov.codsoft_quoteoftheday.db.Quote>>()
    val quoteLocalLiveData:LiveData<List<com.imranmelikov.codsoft_quoteoftheday.db.Quote>>
        get() = quoteLocalMutableLiveData

   private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }


    fun insertQuote(quote: com.imranmelikov.codsoft_quoteoftheday.db.Quote){
        viewModelScope.launch {
            dao.insertQuote(quote)
        }
        getQuoteFromRoom()
    }
    fun deleteQuote(quote: com.imranmelikov.codsoft_quoteoftheday.db.Quote){
        viewModelScope.launch {
            dao.deleteQuote(quote)
        }
        getQuoteFromRoom()
    }
    fun getQuoteFromRoom(){
        viewModelScope.launch {
            quoteLocalMutableLiveData.value=dao.getQuote()
        }
    }

    fun getQuote(){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response=Instance.api.getQuote()
            println(response)
            viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                    response.body().let {
                        quoteMutableLiveData.value = it
                    }
                }
            }
        }
    }
}