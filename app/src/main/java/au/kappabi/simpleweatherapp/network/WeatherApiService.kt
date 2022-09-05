package au.kappabi.simpleweatherapp.network

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Base url for fetching weather data
private val BASE_URL = "https://dnu5embx6omws.cloudfront.net/venues/"

// Moshi object for converting json to weather data
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit object to build web service api
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    // Retrieve weather.json from base url
    @GET("weather.json")
    suspend fun getWeather(): WeatherResponse
}

// Public object to access the api service
object WeatherApi {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
