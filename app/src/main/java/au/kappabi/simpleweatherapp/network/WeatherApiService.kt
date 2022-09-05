package au.kappabi.simpleweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

// Base url for fetching weather data
private val BASE_URL = "https://dnu5embx6omws.cloudfront.net/venues/"

// Retrofit object to build web service api
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    // Retrieve weather.json from base url
    @GET("weather.json")
    suspend fun getWeather(): String
}

// Public object to access the api service
object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
