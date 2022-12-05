package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event

enum class Sort {
    DEFAULT, NAME, COUNT, DISTANCE
}

class BarListViewModel(private val repository: DataRepository) : ViewModel() {

    private val _sortBy = MutableLiveData(Sort.DEFAULT)
    val sortBy: LiveData<Sort> get() = _sortBy

    var isAsc = false

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val loading = MutableLiveData(false)

    var bars: LiveData<List<BarItem>?> =
        liveData {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Event(it)) }
            loading.postValue(false)
            emitSource(repository.getSortedBars(Sort.DEFAULT, isAsc))
        }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Event(it)) }
            loading.postValue(false)
        }
    }

    fun setSort(sort: Sort){
        isAsc = if(sortBy.value == sort){
            !isAsc
        } else {
            false
        }
        _sortBy.postValue(sort)
        bars = liveData {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Event(it)) }
            loading.postValue(false)
            emitSource(repository.getSortedBars(sort, isAsc))
        }
    }

    fun show(msg: String){ _message.postValue(Event(msg))}

}