package com.example.weatherapppankaj.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.weatherapppankaj.R
import com.example.weatherapppankaj.databinding.ActivityMainBinding
import com.example.weatherapppankaj.ui.adapter.ViewPagerAdapter
import com.example.weatherapppankaj.ui.fragments.HistoryFragment
import com.example.weatherapppankaj.ui.fragments.TodayFragment
import com.example.weatherapppankaj.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val weatherViewModel: WeatherViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Initializing the ViewPagerAdapter
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(TodayFragment(), getString(R.string.today))
        adapter.addFragment(HistoryFragment(), getString(R.string.history))

        // Adding the Adapter to the ViewPager
        binding.viewPager.adapter = adapter

        // bind the viewPager with the TabLayout.
        binding.tabs.setupWithViewPager(binding.viewPager)


    }
}