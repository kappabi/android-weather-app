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
    @Json(name = "_weatherLastUpdated") val lastUpdated : Long?,
    @Json(name = "_weatherCondition") val weatherCondition : String?,
    @Json(name = "_weatherFeelsLike") val feelsLike : Int?,
    @Json(name = "_weatherWind") val wind : String?,
    @Json(name = "_weatherHumidity") val humidity : String?

)