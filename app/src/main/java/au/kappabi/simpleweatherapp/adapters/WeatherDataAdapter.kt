package au.kappabi.simpleweatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import au.kappabi.simpleweatherapp.R
import au.kappabi.simpleweatherapp.network.WeatherData
import java.text.SimpleDateFormat
import java.util.*

class WeatherDataAdapter : ListAdapter<WeatherData, WeatherDataAdapter.WeatherViewHolder>(WeatherDataDiffCallback) {

    class WeatherViewHolder(v: View): RecyclerView.ViewHolder(v) {

        private val suburbTextView : TextView = v.findViewById(R.id.suburbTextView)
        private val updatedTextView : TextView = v.findViewById(R.id.updatedTextView)
        private val temperatureTextView : TextView = v.findViewById(R.id.temperatureTextView)
        private val cardView : CardView = v.findViewById(R.id.cardView)

        fun bind(weatherData: WeatherData){

            // Populate suburb and temperature views
            suburbTextView.text= weatherData.name
            if (weatherData.temp != null) {
                temperatureTextView.text = "${weatherData.temp}\u00B0"
            } else {
                temperatureTextView.text = ""
            }

            // Format last updated date object
            var lastUpdatedText = ""
            if (weatherData.lastUpdated != null) {
                val date = Date(weatherData.lastUpdated*1000)
                val sdf = SimpleDateFormat("HH:mm dd-MMM-yyyy")
                lastUpdatedText = sdf.format(date)
            }
            updatedTextView.text = lastUpdatedText

            // Navigate to details fragment on card clicked
            cardView.setOnClickListener {
                val bundle = bundleOf("suburb" to weatherData.name, "summary" to weatherData.weatherCondition,
                    "temperature" to weatherData.temp, "feelslike" to weatherData.feelsLike, "humidity" to weatherData.humidity,
                    "wind" to weatherData.wind, "last_updated" to weatherData.lastUpdated)
                it.findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
            }

        }

    }

    object WeatherDataDiffCallback : DiffUtil.ItemCallback<WeatherData>(){
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val vg = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_weather_item, parent, false)
        return WeatherViewHolder(vg)
    }
}
