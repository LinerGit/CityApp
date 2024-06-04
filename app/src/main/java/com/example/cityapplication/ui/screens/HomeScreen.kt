package com.example.cityapplication.ui.screens

import android.animation.ObjectAnimator
import android.graphics.drawable.Animatable
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cityapplication.R
import com.example.cityapplication.model.City
import kotlinx.serialization.json.JsonNull.content
import java.time.format.TextStyle


@Composable
fun HomeScreen(
    cityUiState: CityUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (cityUiState) {
        is CityUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CityUiState.Success -> CityListScreen(
            cityUiState.cities, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
        )
        is CityUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Image(
        modifier = modifier
            .requiredSize(48.dp)
            .rotate(angle),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )

}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge)
        Button(onClick = retryAction) {
            Text(
                stringResource(R.string.retry),
                style = MaterialTheme.typography.bodyLarge
                )
        }
    }
}

@Composable
fun CityListScreen(
    cities: List<City>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    //cities.
    Scaffold (
        content = { innerPadding ->
            val sortedCities = cities.sortedBy{ it.name }
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = innerPadding,
            ) {

                items(items = sortedCities, key = { city -> city.id }) { city ->
                    CityNameCard(
                        city,
                        modifier = modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    )
    }

@Composable
fun CityNameCapital(city: City, modifier: Modifier = Modifier){
    Box(modifier = Modifier){

    }
}
@Composable
fun CityNameCard(city: City, modifier: Modifier = Modifier) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),



        ){
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Left
                )
            }
        }







