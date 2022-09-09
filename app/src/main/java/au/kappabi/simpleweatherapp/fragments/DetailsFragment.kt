package au.kappabi.simpleweatherapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import au.kappabi.simpleweatherapp.R
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment(R.layout.fragment_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve data from bundle
        val suburb = arguments?.getString("suburb")
        val summary = arguments?.getString("summary")
        val temp = arguments?.getInt("temperature")
        val feelsLike = arguments?.getInt("feelslike")
        val humidity = arguments?.getString("humidity")?.split(" ")?.get(1)
        val wind = arguments?.getString("wind")
        val lastUpdated = arguments?.getLong("last_updated")

        // Populate text views
        val suburbTextView = getView()?.findViewById<TextView>(R.id.suburbTextView)
        suburbTextView?.text = suburb
        val summaryTextView = getView()?.findViewById<TextView>(R.id.summaryTextView)
        summaryTextView?.text = summary
        val tempTextView = getView()?.findViewById<TextView>(R.id.temperatureTextView)
        tempTextView?.text = "${temp}\u00B0"
        val feelsLikeTextView = getView()?.findViewById<TextView>(R.id.feelsLikeTextView)
        feelsLikeTextView?.text = "${feelsLike}\u00B0"
        val humidityTextView = getView()?.findViewById<TextView>(R.id.humidityTextView)
        humidityTextView?.text = humidity
        val windTextView = getView()?.findViewById<TextView>(R.id.windTextView)
        windTextView?.text = wind
        val lastUpdatedTextView = getView()?.findViewById<TextView>(R.id.lastUpdatedTextView)
        var lastUpdatedText = ""
        if (lastUpdated != null) {
            val date = Date(lastUpdated)
            val sdf = SimpleDateFormat("HH:mm a dd MMMM yyyy")
            lastUpdatedText = getString(R.string.last_updated_details) + sdf.format(date)
        }
        lastUpdatedTextView?.text = lastUpdatedText

    }

}