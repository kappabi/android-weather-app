package au.kappabi.simpleweatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.kappabi.simpleweatherapp.network.WeatherApi
import au.kappabi.simpleweatherapp.network.WeatherData
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _response = MutableLiveData<List<WeatherData>>()

    val listWeather: LiveData<List<WeatherData>> = _response

    /**
     * Initialisation of weather data
     */
    init {
        getWeatherData()
    }

    /**
     * Function to retrieve weather data using retrofit api service
     */
    private fun getWeatherData() {
        viewModelScope.launch {
            try {
                val response = WeatherApi.retrofitService.getWeather()
                _response.value = response.data
            } catch (e: Exception) {
                // TODO: Show meaningful error message to user
                Log.e("Temp","Error retrieving data: ${e.message}")
            }
        }
    }
}