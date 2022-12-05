package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.database.model.BarItem
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event
import sk.stu.fei.mobv2022.ui.viewmodels.data.BarDetail

class BarDetailViewModel(private val repository: DataRepository) : ViewModel()  {

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val loading = MutableLiveData(false)

    private val _bar = MutableLiveData<BarDetail>()
    val bar: LiveData<BarDetail> get() = _bar

    private val onError = { errorMessage: String ->
        loading.postValue(false)
        _message.postValue(Event(errorMessage))
    }

    val bars: LiveData<List<BarItem>?> =
        liveData {
            loading.postValue(true)
            emitSource(repository.dbBars())
            loading.postValue(false)
        }


    fun loadBar(id: String) {
        viewModelScope.launch {
            loading.postValue(true)
            val bar = repository.apiBarDetail(id, onError)
            val barDb = bars.value?.find { it.id == id }
            bar?.let {
                _bar.postValue(BarDetail(
                    bar.id,
                    bar.name,
                    bar.type,
                    bar.lat,
                    bar.lon,
                    bar.tags,
                    bar.distance
                ).apply {
                    barDb?.let {
                        this.users = it.users
                    }
                })
            }
        }
    }

   /* fun loadBar(id: String) {
        if (id.isBlank())
            return
        viewModelScope.launch {
            loading.postValue(true)
            _bar.postValue(repository.apiBarDetail(id) { _message.postValue(Event(it)) })
            loading.postValue(false)
        }
    }*/
}