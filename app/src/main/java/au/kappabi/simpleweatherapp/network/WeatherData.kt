package au.kappabi.simpleweatherapp.network

import com.squareup.moshi.Json

data class WeatherResponse (

    val ret : Boolean,
    val isOkay : Boolean,
    val data : List<WeatherData>

)

data class WeatherData (

    @Json(name = "_name") val name : String,
    @Json(name = "_weatherTemp") val temp : Int?,
    @Json(name = "_weatherLastUpdated") val lastUpdated : Long?

)