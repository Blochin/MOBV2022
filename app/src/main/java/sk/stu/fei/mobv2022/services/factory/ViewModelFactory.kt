package sk.stu.fei.mobv2022.services.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.stu.fei.mobv2022.data.repository.DataRepository
import sk.stu.fei.mobv2022.ui.viewmodels.SignUpViewModel


class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}