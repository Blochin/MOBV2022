package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event
import sk.stu.fei.mobv2022.ui.viewmodels.data.Bar
import sk.stu.fei.mobv2022.ui.viewmodels.data.BarDetail
import sk.stu.fei.mobv2022.ui.viewmodels.data.MyLocation
import sk.stu.fei.mobv2022.ui.viewmodels.data.NearbyBar

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
            val barsFromDb = repository.getSortedBars(Sort.NAME,isAsc)
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
            loading.postValue(false)
        }
    }

    fun setSort(sort: Sort) {
        isAsc = if (sortBy.value == sort) {
            !isAsc
        } else {
            false
        }
        _sortBy.postValue(sort)
        viewModelScope.launch {
            loading.postValue(true)
            var barsFromDb = repository.getSortedBars(sort, isAsc)
            if (sortBy.value == (Sort.DISTANCE)){
                var mutablePubs = barsFromDb?.map { it ->
                    Bar(
                        it.id,
                        it.name,
                        it.type,
                        it.lat,
                        it.lon,
                        it.users,
                        it.distanceTo(MyLocation(48.1587, 17.0643))
                    )
                }?.toMutableList()
                mutablePubs?.sortBy { it.distance }

                if (!isAsc){
                    mutablePubs = mutablePubs?.reversed() as MutableList<Bar>?
                }
                mutablePubs?.size
                barsFromDb = mutablePubs?.map {
                    BarItem(
                        it.id,
                        it.name,
                        it.type,
                        it.lat,
                        it.lon,
                        it.users
                    )
                }
            }
            _bars.postValue(barsFromDb)
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Event(msg))}

}