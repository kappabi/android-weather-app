package au.kappabi.simpleweatherapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.kappabi.simpleweatherapp.R
import au.kappabi.simpleweatherapp.adapters.WeatherListAdapter
import au.kappabi.simpleweatherapp.viewmodels.HomeViewModel
import com.google.android.material.tabs.TabLayout
import java.time.format.DateTimeFormatter

/**
 * This fragment lists weather data according to suburb.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    val homeViewModel : HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val textView = getView()?.findViewById<TextView>(R.id.noDataTextView)
        val loadingSpinner = requireView().findViewById<ProgressBar>(R.id.loadingSpinner)
        loadingSpinner?.visibility = View.VISIBLE
        val recyclerView = getView()?.findViewById<RecyclerView>(R.id.homeRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(getView()?.context)

        homeViewModel.listWeather.observe(viewLifecycleOwner) {
            // Set the text displayed when the list is empty
            if (it.isEmpty()) {
                textView?.text = getString(R.string.no_data_message)
            }

            // Populate the recycler view with the list of weather data
            val adapter = WeatherListAdapter(it)
            recyclerView?.adapter = adapter
        }

        // Put a loading spinner on the page while waiting for the weather data
        homeViewModel.loaded.observe(viewLifecycleOwner) {
            if (it == false) {
                loadingSpinner?.visibility = View.VISIBLE
            } else {
                loadingSpinner?.visibility = View.GONE
            }
        }

        // Set up sorting tabs
        val tabLayout = getView()?.findViewById<TabLayout>(R.id.tab)
        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                homeViewModel.sortData(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        // Show date data was last retrieved
        val dateRetrievedTextView = getView()?.findViewById<TextView>(R.id.dateRetrievedTextView)
        homeViewModel.dateRetrieved.observe(viewLifecycleOwner) {
            dateRetrievedTextView?.text = getString(R.string.data_retrieved) + ": ${it.format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))}"
        }

        // Set up refresh data button
        val refreshButton = getView()?.findViewById<ImageButton>(R.id.refreshButton)
        refreshButton?.setOnClickListener{
            homeViewModel.getWeatherData(tabLayout?.selectedTabPosition)
        }
    }
}