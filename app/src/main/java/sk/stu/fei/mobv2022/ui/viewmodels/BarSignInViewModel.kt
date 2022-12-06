package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event
import sk.stu.fei.mobv2022.ui.viewmodels.data.MyLocation
import sk.stu.fei.mobv2022.ui.viewmodels.data.NearbyBar

class BarSignInViewModel(private val repository: DataRepository) : ViewModel()  {
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val myLocation = MutableLiveData<MyLocation>(null)
    val myBar= MutableLiveData<NearbyBar>(null)

    private val _checkedIn = MutableLiveData<Event<Boolean>>()
    val checkedIn: LiveData<Event<Boolean>>
        get() = _checkedIn


    val bars : LiveData<List<NearbyBar>> = myLocation.switchMap {
        liveData {
            loading.postValue(true)
            it?.let {
                val b = repository.apiNearbyBars(it.lat,it.lon) { _message.postValue(Event(it)) }
                emit(b)
                if (myBar.value==null){
                    myBar.postValue(b.firstOrNull())
                }
            } ?: emit(listOf())
            loading.postValue(false)
        }
    }

    fun checkMe(){
        viewModelScope.launch {
            loading.postValue(true)
            myBar.value?.let {
                repository.apiBarCheckin(
                    it,
                    {_message.postValue(Event(it))},
                    {_checkedIn.postValue(Event(it))})
            }
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Event(msg))}
}