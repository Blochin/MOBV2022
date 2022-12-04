package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event

class FriendViewModel(private val repository: DataRepository) : ViewModel() {

    val loading = MutableLiveData(false)

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    private val onError = { errorMessage: String ->
        loading.postValue(false)
        _message.postValue(Event(errorMessage))
    }

    private val onResolve = { resolveMessage: String ->
        loading.postValue(false)
        _message.postValue(Event(resolveMessage))
    }

    fun addFriend(friendsName: String){
        viewModelScope.launch {
            repository.addFriend(friendsName,onResolve ,onError)
        }
    }

    fun setMessage(message: String) {
        _message.postValue(Event(message))
    }
}