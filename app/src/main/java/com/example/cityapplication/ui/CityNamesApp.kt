package com.example.cityapplication.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cityapplication.ui.screens.CityViewModel
import com.example.cityapplication.ui.screens.HomeScreen


@Composable
fun CityNamesApp() {
    Scaffold(
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val cityViewModel: CityViewModel =
                viewModel(factory = CityViewModel.Factory)
            HomeScreen(
                cityUiState = cityViewModel.cityUiState,
                retryAction = cityViewModel::getCityNames,
                contentPadding = it
            )
        }
    }
}