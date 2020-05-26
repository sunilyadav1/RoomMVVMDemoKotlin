package com.sunil.roommvvmkotlin.viewmodel

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunil.roommvvmkotlin.model.Subscriber
import com.sunil.roommvvmkotlin.repository.SubscriberRepository
import com.sunil.roommvvmkotlin.util.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class SubscriberViewModel(private val subscriberRepository: SubscriberRepository) : ViewModel(),
    Observable {
    val subscribers = subscriberRepository.subscribers

    private var isUpdateOrDelete = false;
    private lateinit var subscriberUpdateOrDelet: Subscriber

    private val statusMessage = MutableLiveData<Event<String>>();
    val message: LiveData<Event<String>>
        get() = statusMessage


    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }


    fun saveOrUpdate() {
        if (isValidate()) {
            if (isUpdateOrDelete) {
                subscriberUpdateOrDelet.name = inputName.value!!
                subscriberUpdateOrDelet.email = inputEmail.value!!
                update(subscriberUpdateOrDelet)
            } else {
                val name: String = inputName.value!!
                val email: String = inputEmail.value!!
                insert(Subscriber(0, name, email))
                inputName.value = null
                inputEmail.value = null
            }
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberUpdateOrDelet)
        } else {
            clearAll()
        }

    }


    fun insert(subscriber: Subscriber): Job = viewModelScope.launch {
        val rowid = subscriberRepository.insert(subscriber)
        statusMessage.value = Event("Successfully Insert")
    }

    fun update(subscriber: Subscriber): Job = viewModelScope.launch {
        val rowId = subscriberRepository.update(subscriber)

        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false;
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

        statusMessage.value = Event("Successfully Update")
    }

    fun delete(subscriber: Subscriber): Job = viewModelScope.launch {
        val rowid = subscriberRepository.delete(subscriber)

        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false;

        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

        statusMessage.value = Event("Successfully delet")
    }

    fun clearAll(): Job = viewModelScope.launch {
        val rowId = subscriberRepository.deleteAll()

        statusMessage.value = Event("Clear All")
    }


    fun inItUpdatedAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true;
        subscriberUpdateOrDelet = subscriber;

        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


    private fun isValidate(): Boolean {
        if (inputName.value == null) {
            statusMessage.value = Event("Enter Name")
            return false
        }
        if (inputEmail.value == null) {
            statusMessage.value = Event("Enter Email")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value).matches()) {
            statusMessage.value = Event("Enter Valid Email")
            return false
        }
        return true;
    }


}