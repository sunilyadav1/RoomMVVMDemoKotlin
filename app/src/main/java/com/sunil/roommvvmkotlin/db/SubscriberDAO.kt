package com.sunil.roommvvmkotlin.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sunil.roommvvmkotlin.model.Subscriber

@Dao
interface SubscriberDAO {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun  insertSubscriber(subscriber:Subscriber):Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber) :Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber) :Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll() :Int

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscriber():LiveData<List<Subscriber>>
}