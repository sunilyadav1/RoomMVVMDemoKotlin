package com.sunil.roommvvmkotlin.view

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sunil.roommvvmkotlin.R
import com.sunil.roommvvmkotlin.databinding.ActivityMainBinding
import com.sunil.roommvvmkotlin.db.SubscriberDAO
import com.sunil.roommvvmkotlin.db.SubscriberDatabase
import com.sunil.roommvvmkotlin.model.Subscriber
import com.sunil.roommvvmkotlin.repository.SubscriberRepository
import com.sunil.roommvvmkotlin.view.adapter.SuscreiberListAdapter
import com.sunil.roommvvmkotlin.viewmodel.SubscriberViewModel
import com.sunil.roommvvmkotlin.viewmodel.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao:SubscriberDAO= SubscriberDatabase.getInstance(application).subscriberDAO
        val repository=SubscriberRepository(dao)

        val factory=SubscriberViewModelFactory(repository)
        subscriberViewModel=ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel=subscriberViewModel

        binding.lifecycleOwner=this
        inItRecyclerView()

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {

                Snackbar.make(window.decorView,it, Snackbar.LENGTH_SHORT).show();
            }
        })


    }

    private fun displaySubscribersList(){
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.e("TAG",it.toString())
            binding.recyclerViewSubscriber.adapter=SuscreiberListAdapter(it,{selectedItem:Subscriber->listItemClicked(selectedItem)});
        })
    }

    private fun inItRecyclerView(){
        binding.recyclerViewSubscriber.layoutManager=LinearLayoutManager(this)
        displaySubscribersList()

    }

    private fun listItemClicked(subscriber: Subscriber){
        subscriberViewModel.inItUpdatedAndDelete(subscriber)
    }
}
