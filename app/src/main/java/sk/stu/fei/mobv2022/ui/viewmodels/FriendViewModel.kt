package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.database.model.FriendItem
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event

class FriendViewModel(private val repository: DataRepository) : ViewModel() {

    val loading = MutableLiveData(false)

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val friends : LiveData<List<FriendItem>?>
        = liveData {
        loading.postValue(true)
        repository.getFriends { _message.postValue(Event(it)) }
        loading.postValue(false)
        emitSource(repository.dbFriends())
    }

    private val onError = { errorMessage: String ->
        loading.postValue(false)
        _message.postValue(Event(errorMessage))
    }

    private val onResolve = { resolveMessage: String ->
        loading.postValue(false)
        _message.postValue(Event(resolveMessage))
    }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.getFriends { _message.postValue(Event(it)) }
            loading.postValue(false)
        }
    }

    fun addFriend(friendsName: String){
        viewModelScope.launch {
            repository.addFriend(friendsName,onResolve ,onError)
        }
    }

    fun removeFriend(friendName: String){
        viewModelScope.launch {
            repository.deleteFriend(friendName ,onError)
        }
        refreshData()
    }

    fun setMessage(message: String) {
        _message.postValue(Event(message))
    }

    fun show(msg: String){ _message.postValue(Event(msg))}
}