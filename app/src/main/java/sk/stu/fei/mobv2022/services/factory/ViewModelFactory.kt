package sk.stu.fei.mobv2022.services.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.ui.viewmodels.*


class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(LogInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LogInViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(BarListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarListViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(BarDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BarDetailViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(FriendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FriendViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}