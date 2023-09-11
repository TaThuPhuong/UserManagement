package com.ttp.usermanagement.ui.statistics.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {

    private val _statisticsState = MutableLiveData<Resource<Map<String, Int>>>()
    val statisticsState: LiveData<Resource<Map<String, Int>>> = _statisticsState

    fun getStatistics() {
        viewModelScope.launch {
            _statisticsState.value = Resource.Loading
            userRepo.getStatistics().collect {
                _statisticsState.value = it
            }
        }
    }
}