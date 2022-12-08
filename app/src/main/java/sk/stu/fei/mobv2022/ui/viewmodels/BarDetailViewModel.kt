package sk.stu.fei.mobv2022.ui.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.services.Event
import sk.stu.fei.mobv2022.ui.viewmodels.data.BarDetail

class BarDetailViewModel(private val repository: DataRepository) : ViewModel()  {

    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val loading = MutableLiveData(true)

    private val _bar = MutableLiveData<BarDetail>()
    val bar: LiveData<BarDetail> get() = _bar

    private val onError = { errorMessage: String ->
        loading.postValue(false)
        _message.postValue(Event(errorMessage))
    }

    fun loadBar(id: String) {
        viewModelScope.launch {
            val bar = repository.apiBarDetail(id, onError)
            val barById = repository.dbBarById(id)
            loading.postValue(true)
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
                    barById.let {
                        this.users = it.users
                    }
                })
            }
            loading.postValue(false)
        }
    }
}