package com.example.cityapplication.ui.screens



import androidx.collection.intFloatMapOf
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.cityapplication.R
import com.example.cityapplication.model.City
import java.time.format.TextStyle
import java.util.SortedMap


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
            cityUiState.cities, contentPadding = contentPadding, modifier = modifier.fillMaxWidth(),  categories = cityUiState.categories
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
        modifier = modifier.padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.loading_failed),
            modifier = Modifier
                .padding(bottom = 42.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = MaterialTheme.typography.bodyLarge)
        Button(
            onClick = retryAction){
            Text(
                text = stringResource(id = R.string.retry),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge)
                }

        }

    }



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityListScreen(
    cities: List<City>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    categories: SortedMap<Char, List<City>>
) {

    val namesList = categories.map {
        Category(
            name = it.key.toString(),
            items = it.value
        )
    }
    //cities.
    Scaffold (

        modifier = Modifier,
        content = { it ->

            val listState = rememberLazyListState()
            val isAtTopOfList by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex < 3
                }
            }

            LazyColumn(
                state = listState,
                contentPadding = it,
                modifier = modifier
                    .fillMaxSize()

            ) {

                categories.forEach { category ->
                    stickyHeader {
                            CategoryHeader(category.key.toString())

                    }
                    items(
                        category.value
                    ) { city ->
                        CityNameCard(
                            name = city.name,
                            modifier = modifier
                                .fillMaxWidth()
                        )


                }


                }
           }



        }
    )
    }

@Composable
fun ScrollToTopButton() {
    TODO("Not yet implemented")
}

@Composable
private fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
    ) {
        Text(
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge,
            text = text,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }

}
@Composable
fun CityNameCard(name: String, modifier: Modifier = Modifier,) {

        Card(
            modifier = Modifier
                .requiredHeight(40.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .padding(start = 56.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
                .clickable { },
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),

        ){
                Text(
                    text = name,
                    modifier =  modifier
                        .padding(start = 16.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge,

                )
            }
        }







