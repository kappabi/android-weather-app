package au.kappabi.simpleweatherapp.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import au.kappabi.simpleweatherapp.R
import au.kappabi.simpleweatherapp.viewmodels.HomeViewModel

/**
 * This fragment lists weather data according to suburb.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    val homeViewModel : HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Temporarily displaying json fetched data
        val textView = getView()?.findViewById<TextView>(R.id.textView)
        homeViewModel.listWeather.observe(viewLifecycleOwner) {
            textView?.text = it.toString()
        }
    }
}