package sk.stu.fei.mobv2022.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.databinding.FragmentBarsBinding
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.services.PreferenceData
import sk.stu.fei.mobv2022.ui.viewmodels.BarListViewModel
import sk.stu.fei.mobv2022.ui.viewmodels.Sort
import sk.stu.fei.mobv2022.ui.viewmodels.data.MyLocation

class BarsFragment : Fragment() {

    private lateinit var binding: FragmentBarsBinding
    private lateinit var viewModel: BarListViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geofencingClient: GeofencingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        )[BarListViewModel::class.java]

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid ?: "").isBlank()) {
            Navigation.findNavController(view).navigate(R.id.action_to_login)
            return
        }
        viewModel.setSort(Sort.NAME,null)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }.also { bnd ->
            bnd.sortByName.setOnClickListener{
                viewModel.setSort(Sort.NAME,null)
            }
            bnd.sortByDistance.setOnClickListener{
                sortByDistance()
            }
            bnd.sortByCount.setOnClickListener{
                viewModel.setSort(Sort.COUNT,null)
            }
            bnd.swiperefresh.setOnRefreshListener {
                viewModel.refreshData()
            }
            bnd.logout.setOnClickListener {
                PreferenceData.getInstance().clearData(requireContext())
                Navigation.findNavController(requireView()).navigate(R.id.action_to_login)
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = it
        }
    }

    @SuppressLint("MissingPermission")
    private fun sortByDistance() {
        if (checkPermissions()) {
            viewModel.loading.postValue(true)
            fusedLocationClient.getCurrentLocation(
                CurrentLocationRequest.Builder().setDurationMillis(30000)
                    .setMaxUpdateAgeMillis(60000).build(), null
            ).addOnSuccessListener {
                it?.let {
                    viewModel.setSort(Sort.DISTANCE ,MyLocation(it.latitude, it.longitude))
                } ?: viewModel.loading.postValue(false)
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}