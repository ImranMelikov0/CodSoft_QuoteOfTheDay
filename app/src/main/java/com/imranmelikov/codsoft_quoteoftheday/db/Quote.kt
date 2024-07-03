package com.imranmelikov.codsoft_quoteoftheday.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(val text:String) {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}