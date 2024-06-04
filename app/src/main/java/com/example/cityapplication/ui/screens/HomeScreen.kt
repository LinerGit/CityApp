package com.example.cityapplication.ui.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cityapplication.R
import com.example.cityapplication.model.City



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
    Image(
        modifier = modifier
            .requiredSize(48.dp)
            .rotate(360f),
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
        content = { it ->
            val sortedCities = cities.sortedBy{ it.name }
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = it,
            ) {

                items(items = sortedCities) {
                    CityNameCard(
                        city = it,
                        modifier = modifier
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Left
                )
            }

            }
        }







