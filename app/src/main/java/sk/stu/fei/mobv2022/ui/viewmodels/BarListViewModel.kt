package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event

class BarListViewModel(private val repository: DataRepository) : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val bars: LiveData<List<BarItem>?> =
        liveData {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Event(it)) }
            loading.postValue(false)
            emitSource(repository.dbBars())
        }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Event(it)) }
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Event(msg))}

}