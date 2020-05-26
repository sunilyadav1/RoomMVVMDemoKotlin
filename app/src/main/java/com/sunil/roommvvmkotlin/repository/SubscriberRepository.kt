package com.sunil.roommvvmkotlin.repository

import com.sunil.roommvvmkotlin.db.SubscriberDAO
import com.sunil.roommvvmkotlin.model.Subscriber

class SubscriberRepository (private val dao: SubscriberDAO) {

    val subscribers=dao.getAllSubscriber()

    suspend fun insert(subscriber: Subscriber) :Long{
       return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int{
       return dao.updateSubscriber(subscriber)
    }
    suspend fun delete(subscriber: Subscriber) :Int{
       return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll() :Int{
      return  dao.deleteAll()
    }


}