package au.kappabi.simpleweatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.kappabi.simpleweatherapp.network.WeatherApi
import au.kappabi.simpleweatherapp.network.WeatherApiService
import au.kappabi.simpleweatherapp.network.WeatherData
import au.kappabi.simpleweatherapp.network.WeatherResponse
import au.kappabi.simpleweatherapp.viewmodels.HomeViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.whenever
import java.lang.RuntimeException
import java.time.LocalDateTime

/**
 * Local unit tests of the home view model.
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class HomeViewModelUnitTests {
    // Test weather data
    private val testWeatherResponse = WeatherResponse(true, true, listOf(
        WeatherData("B Place", 0, 2L, "Hot", 0, "SW", "50%"),
        WeatherData("A Place", 1, 0L, "Hot", 0, "SW", "50%"),
        WeatherData("C Place", 2, 1L, "Hot", 0, "SW", "50%")))

    // Mock web retrieval service
    @Mock
    private lateinit var mockWeatherApi: WeatherApi
    @Mock
    private lateinit var mockRetrofitService: WeatherApiService

    // Needed to access the LiveData
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Set up the test dispatchers for testing of the coroutines
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialisation() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doReturn(testWeatherResponse)
            val testViewModel = HomeViewModel(mockWeatherApi)

            assert(testViewModel.loaded.value == true)
            // Check date retrieved is within a minute of now to account for slow process
            assert(testViewModel.dateRetrieved.value!! <= LocalDateTime.now()
                    && testViewModel.dateRetrieved.value!! > LocalDateTime.now().minusMinutes(1))
        }
    }

    @Test
    fun exceptionResponse() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doThrow(RuntimeException())
            val testViewModel = HomeViewModel(mockWeatherApi)

            // Check loaded and empty list set
            assert(testViewModel.loaded.value == true)
            assert(testViewModel.listWeather.value!!.isEmpty())
        }
    }

    @Test
    fun initialisedSortedAlphabetical() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doReturn(testWeatherResponse)
            val testViewModel = HomeViewModel(mockWeatherApi)

            // Check first item is A
            assert(testViewModel.listWeather.value!!.get(0).name == "A Place")
        }
    }

    @Test
    fun sortTemperature() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doReturn(testWeatherResponse)
            val testViewModel = HomeViewModel(mockWeatherApi)

            // Sort on temperature
            testViewModel.sortTemperature()

            // Check first item is C and temp is 2
            val firstItem = testViewModel.listWeather.value!!.get(0)
            assert(firstItem.name == "C Place")
            assert(firstItem.temp == 2)
        }
    }

    @Test
    fun sortLastUpdated() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doReturn(testWeatherResponse)
            val testViewModel = HomeViewModel(mockWeatherApi)

            // Sort on date last updated
            testViewModel.sortLastUpdated()

            // Check first item is B and date is 2
            val firstItem = testViewModel.listWeather.value!!.get(0)
            assert(firstItem.name == "B Place")
            assert(firstItem.lastUpdated == 2L)
        }
    }

    @Test
    fun sortData() {
        runTest {
            // Initialise ViewModel
            whenever(mockWeatherApi.retrofitService) doReturn (mockRetrofitService)
            whenever(mockRetrofitService.getWeather()).doReturn(testWeatherResponse)
            val testViewModel = HomeViewModel(mockWeatherApi)

            // Sort on date last updated
            testViewModel.sortData(2)
            // Check first item is B and date is 2
            var firstItem = testViewModel.listWeather.value!!.get(0)
            assert(firstItem.name == "B Place")
            assert(firstItem.lastUpdated == 2L)

            // Sort on temperature
            testViewModel.sortData(1)
            // Check first item is C and temp is 2
            firstItem = testViewModel.listWeather.value!!.get(0)
            assert(firstItem.name == "C Place")
            assert(firstItem.temp == 2)

            // Sort on alphabetical
            testViewModel.sortData(0)
            // Check first item is A
            assert(testViewModel.listWeather.value!!.get(0).name == "A Place")
        }
    }

}