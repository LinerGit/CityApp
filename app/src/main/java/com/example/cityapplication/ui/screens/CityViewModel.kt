package com.example.cityapplication.ui.screens

import android.net.http.HttpResponseCache
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cityapplication.CityNamesApplication
import com.example.cityapplication.data.CityNamesRepository
import com.example.cityapplication.model.City
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed interface CityUiState {
    data class Success(val cities: List<City>) : CityUiState
    object Error : CityUiState
    object Loading : CityUiState
}

class CityViewModel(private val cityNamesRepository: CityNamesRepository) : ViewModel() {

    var cityUiState: CityUiState by mutableStateOf(CityUiState.Loading)
        private set


    init {
        getCityNames()
    }

    fun getCityNames() {
        viewModelScope.launch {
            cityUiState = CityUiState.Loading
            cityUiState = try {

                CityUiState.Success(cityNamesRepository.getCityNames())
            } catch (e: IOException) {
                CityUiState.Error
            } catch (e: HttpException) {
               CityUiState.Error

            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CityNamesApplication)
                val cityNamesRepository = application.container.cityNamesRepository
                CityViewModel(cityNamesRepository = cityNamesRepository)
            }
        }
    }
}
