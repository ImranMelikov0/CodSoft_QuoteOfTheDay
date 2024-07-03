package com.imranmelikov.codsoft_quoteoftheday.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: Quote)

    @Query("SELECT * FROM Quote")
    suspend fun getQuote():List<Quote>

    @Delete
    suspend fun deleteQuote(quote: Quote)
}