package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.api.UserResponse
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event

class LogInViewModel (private val repository: DataRepository) : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val user= MutableLiveData<UserResponse>(null)

    val loading = MutableLiveData(false)

    fun login(name: String, password: String){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiUserLogin(
                name,password,
                { _message.postValue(Event(it)) },
                { user.postValue(it) }
            )
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Event(msg))}
}