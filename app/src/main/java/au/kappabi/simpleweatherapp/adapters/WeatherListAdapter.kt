package au.kappabi.simpleweatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.kappabi.simpleweatherapp.R
import au.kappabi.simpleweatherapp.network.WeatherData
import java.text.SimpleDateFormat
import java.util.*

class WeatherListAdapter(val data: List<WeatherData>) : RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>() {

    class WeatherListViewHolder(v: View): RecyclerView.ViewHolder(v) {

        private val suburbTextView : TextView = v.findViewById(R.id.suburbTextView)
        private val updatedTextView : TextView = v.findViewById(R.id.updatedTextView)
        private val temperatureTextView : TextView = v.findViewById(R.id.temperatureTextView)

        fun bind(weatherData: WeatherData, pos: Int, action: ((view: View) -> Unit)?){

            // Populate suburb and temperature views
            suburbTextView.text= weatherData.name
            if (weatherData.temp != null) {
                temperatureTextView.text = "${weatherData.temp}\u00B0"
            } else {
                temperatureTextView.text = ""
            }

            // Format last updated date object
            if (weatherData.lastUpdated != null) {
                val date = Date(weatherData.lastUpdated)
                val sdf = SimpleDateFormat("HH:mm dd-MMM-yyyy")
                updatedTextView.text = sdf.format(date)
            } else {
                updatedTextView.text = ""
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder {
        val vg = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_weather_item, parent, false)
        return WeatherListViewHolder(vg)
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {

        val weatherData = data[position]
        holder.bind(weatherData, data.indexOf(weatherData),null )

    }

    override fun getItemCount(): Int {
        return data.size
    }

}