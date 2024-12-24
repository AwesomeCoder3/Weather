package com.example.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.weatherapp.data.WeatherObject
import com.example.weatherapp.ui.theme.WeatherappTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.reflect.KSuspendFunction0
import kotlin.reflect.KSuspendFunction1


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp() {

    WeatherappTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            Content( Modifier.padding(it))
        }
    }
}

@Composable
fun Content(modifier: Modifier, viewModel: MainViewModel = koinViewModel()) {
    val state = viewModel.stateFlow.collectAsState()

    Column(modifier = modifier) {
        SearchText(viewModel::fetchWeather)

        when(state.value.screenState){
            WeatherScreenState.EMPTY -> {
                EmptyCard()
            }
            WeatherScreenState.SEARCH -> {
                SearchCard(state.value.weather, viewModel::cardClicked)
            }
            WeatherScreenState.HOME -> {
                HomeCard(state.value.weather)
            }
        }


    }

}

@Composable
fun SearchCard(weather: WeatherObject?, onClick: KSuspendFunction0<Unit>) {
    val coroutineScope : CoroutineScope = rememberCoroutineScope()

    weather?.let {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp))
                .clickable {
                    coroutineScope.launch {
                        onClick()
                    }
                }) {
            Column(Modifier.padding(24.dp) ) {
                Text(
                    text = weather.location?.name.toString(),
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)

                )
                Text(
                    text = weather.current?.temp_f?.toInt().toString()+"°",
                    fontSize = 48.sp,
                    style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
                )


            }
            AsyncImage(
                model = weather.current?.condition?.icon?.sanitizeIconUrl(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(24.dp)
                    .size(100.dp)
            )
        }
    }


}

fun String.sanitizeIconUrl():String{
    return "https:${this}"
}

@Composable
fun HomeCard(weather: WeatherObject?) {
    Column( modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = weather?.current?.condition?.icon?.sanitizeIconUrl(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(top = 12.dp)
                .size(200.dp)
        )
        Row(){
            Text(
                text = weather?.location?.name.toString(),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
            )
            Icon(Icons.Filled.PlayArrow, "")
        }

        Text(
            text = weather?.current?.temp_f?.toInt().toString() +"°",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.onPrimary, shape = RoundedCornerShape(8.dp))) {
        HomeFooterItems("Humidity", weather?.current?.humidity.toString() +"%")
        HomeFooterItems("UV", weather?.current?.uv?.toInt().toString())
        HomeFooterItems("Feels Like", weather?.current?.feelslike_f?.toInt().toString() + "°")
    }


}

@Composable
fun HomeFooterItems(name: String, value : String) {
    Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally ) {
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.bodySmall


        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onTertiary,
                    style = MaterialTheme.typography.bodyMedium


        )


    }
}



@Composable
fun EmptyCard() {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()){
        Text(
            text = "No City Selected",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Text(
            text = "Please Search for a City",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
        )
    }

}

@Composable
fun SearchText(search: KSuspendFunction1<String, Unit>) {
    val coroutineScope : CoroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        trailingIcon ={
            Icon(Icons.Filled.Search, "", tint = MaterialTheme.colorScheme.onTertiary)
        } ,

        onValueChange = {
            text = it
        },

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done // Action Key
        ),

        keyboardActions = KeyboardActions(onDone = {
            coroutineScope.launch {
                search(text)
            }
        }
        ),

        shape = RoundedCornerShape(10.dp),


    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherappTheme {
        WeatherApp()
    }
}