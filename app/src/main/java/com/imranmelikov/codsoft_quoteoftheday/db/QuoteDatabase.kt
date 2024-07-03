package com.imranmelikov.codsoft_quoteoftheday.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([Quote::class], version = 1)
abstract class QuoteDatabase:RoomDatabase() {
    abstract fun dao():QuoteDao
}