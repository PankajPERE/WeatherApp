package com.example.weatherapppankaj.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weatherapppankaj.BuildConfig
import com.example.weatherapppankaj.R
import com.example.weatherapppankaj.databinding.FragmentTodayBinding
import com.example.weatherapppankaj.ui.activity.HomeActivity
import com.example.weatherapppankaj.utils.CommonUtils
import com.example.weatherapppankaj.utils.CommonUtils.showToast
import com.example.weatherapppankaj.utils.Resource
import com.example.weatherapppankaj.utils.Utils.convertDate
import com.example.weatherapppankaj.utils.Utils.convertKelvinToCel
import com.example.weatherapppankaj.utils.Utils.getImageUrlByName
import com.example.weatherapppankaj.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodayFragment : Fragment() {

    private lateinit var binding: FragmentTodayBinding
    lateinit var weatherViewModel: WeatherViewModel
    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel = (activity as HomeActivity).weatherViewModel
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (CommonUtils.checkConnection(requireContext()) && CommonUtils.isGpsEnable(requireContext())) {
            getLocationData()
        }
    }

    private fun getLocationData() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProvider.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    try {
                        loadWeatherDataRemote(location.latitude, location.longitude)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } else {
            askForPermission()
        }
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            CommonUtils.GPS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CommonUtils.GPS_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocationData()
            }else{
                showToast(requireContext(),getString(R.string.please_provide_required_permission))
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun loadWeatherDataRemote(latitude: Double, longitude: Double) {

        weatherViewModel.getWeatherDataRemote(latitude, longitude, BuildConfig.API_KEY)

        weatherViewModel.weatherData.observe(viewLifecycleOwner, Observer { response ->

            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        binding.rlData.isVisible = true
                        binding.progrssBar.isVisible = false
                        val intTemp = convertKelvinToCel(it.main?.temp)
                        binding.txtWeather.text = intTemp
                        binding.txtCity.text = "${it.name}, "
                        binding.txtCountry.text = it.sys?.country?:""
                        binding.txtSunriseTime.text = convertDate(it.sys?.sunrise)
                        binding.txtSunsetTime.text = convertDate(it.sys?.sunset)

                        val imgUrl = it.weather?.get(0)?.icon?.let { img ->
                            getImageUrlByName(img)
                        } ?: run {
                            getImageUrlByName(getString(R.string.default_weather_icon))
                        }

                        Glide.with(this).load(imgUrl).into(binding.imvWeather)
                    }
                }

                is Resource.Error -> {
                    response.data?.let {
                        binding.rlData.isVisible = false
                        binding.progrssBar.isVisible = false
                    }
                }

                is Resource.Loading -> {
                    binding.rlData.isVisible = false
                    binding.progrssBar.isVisible = true
                }
            }
        })
    }


}