package au.kappabi.simpleweatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.kappabi.simpleweatherapp.network.WeatherApi
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _response = MutableLiveData<String>()

    val listWeather: LiveData<String> = _response

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
            val response = WeatherApi.retrofitService.getWeather()
            _response.value = response
        }
    }
}