package com.example.cityapplication.ui.screens

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
import java.io.IOException
import java.util.SortedMap

data class Category(
    val name: String,
    val items: List<City>
)
sealed interface CityUiState {
    data class Success(val cities: List<City>, val categories: SortedMap<Char, List<City>>) : CityUiState
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
                val cities = cityNamesRepository.getCityNames()
                val sortedCities = cities.sortedBy{ it.name }
                val filteredCities = sortedCities.filterIndexed { index, _ -> (index > 3) }
                val grouped = filteredCities.groupBy { it.name[0] }.toSortedMap()
                CityUiState.Success(filteredCities, grouped)

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
