package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event
import sk.stu.fei.mobv2022.ui.viewmodels.data.MyLocation

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

    private val _bars = MutableLiveData<List<BarItem>?>()
    val bars: LiveData<List<BarItem>?>
        get() = _bars


    init {
        viewModelScope.launch {
            val barsFromDb = repository.getSortedBars(Sort.NAME,isAsc,null)
            if (barsFromDb == null || barsFromDb.isEmpty()) {
                refreshData()
            } else {
                _bars.postValue(barsFromDb)
            }
        }
    }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Event(it)) }
            val barsFromDb = repository.getSortedBars(Sort.NAME,isAsc,null)
            _bars.postValue(barsFromDb)
            loading.postValue(false)
        }
    }

    fun setSort(sort: Sort, myLocation: MyLocation?) {
        isAsc = if (sortBy.value == sort) {
            !isAsc
        } else {
            false
        }
        _sortBy.postValue(sort)
        viewModelScope.launch {
            loading.postValue(true)
            val barsFromDb = repository.getSortedBars(sort, isAsc, myLocation)
            _bars.postValue(barsFromDb)
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Event(msg))}

}