package au.kappabi.simpleweatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.kappabi.simpleweatherapp.network.WeatherApi
import au.kappabi.simpleweatherapp.network.WeatherData
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeViewModel : ViewModel() {

    private val _response = MutableLiveData<List<WeatherData>>()
    private val _dateRetrieved = MutableLiveData<LocalDateTime>()

    val listWeather: LiveData<List<WeatherData>> = _response
    val dateRetrieved: LiveData<LocalDateTime> = _dateRetrieved

    // Status of data retrieval to display feedback to user.
    private var _loaded = MutableLiveData<Boolean>(false)
    var loaded: LiveData<Boolean> = _loaded

    /**
     * Initialisation of weather data
     */
    init {
        getWeatherData(0) // Initially sorted alphabetically
    }

    /**
     * Function to retrieve weather data using retrofit api service
     */
    fun getWeatherData(sortOption: Int?) {
        _loaded.value = false
        viewModelScope.launch {
            try {
                val response = WeatherApi.retrofitService.getWeather()
                // TODO Include only suburbs with temperature data
                _response.value = response.data
                sortData(sortOption)
                _loaded.value = true
                _dateRetrieved.value = LocalDateTime.now()
            } catch (e: Exception) {
                // Setting list to empty triggers display of error message to user in fragment.
                _response.value = emptyList()
                _loaded.value = true
            }
        }
    }

    // Sort data based on tab selected
    fun sortData(pos: Int?) {
        when(pos) {
            0 -> sortAlphabetical()
            1 -> sortTemperature()
            2 -> sortLastUpdated()
        }
    }

    fun sortAlphabetical() {
        if (!listWeather.value.isNullOrEmpty()) {
            val sortedWeather = listWeather.value as MutableList
            sortedWeather.sortBy { it.name }
            _response.value = sortedWeather
        }
    }

    fun sortTemperature() {
        if (!listWeather.value.isNullOrEmpty()) {
            val sortedWeather = listWeather.value as MutableList
            sortedWeather.sortByDescending { it.temp }
            _response.value = sortedWeather
        }
    }

    fun sortLastUpdated() {
        if (!listWeather.value.isNullOrEmpty()) {
            val sortedWeather = listWeather.value as MutableList
            sortedWeather.sortByDescending { it.lastUpdated }
            _response.value = sortedWeather
        }
    }

}