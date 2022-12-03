package sk.stu.fei.mobv2022.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.databinding.FragmentBarsBinding
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.services.PreferenceData
import sk.stu.fei.mobv2022.ui.viewmodels.BarListViewModel
import sk.stu.fei.mobv2022.ui.viewmodels.LogInViewModel
import sk.stu.fei.mobv2022.ui.viewmodels.Sort

class BarsFragment : Fragment() {

    private lateinit var binding: FragmentBarsBinding
    private lateinit var viewModel: BarListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        )[BarListViewModel::class.java]
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
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }.also { it ->
            it.bottomNavigation.setOnNavigationItemSelectedListener{ item->
                when(item.itemId){
                    R.id.logout->{
                        PreferenceData.getInstance().clearData(requireContext())
                        Navigation.findNavController(requireView()).navigate(R.id.action_to_login)
                        true
                    } else -> false
                }
            }
            it.sortByName.setOnClickListener{
                binding.apply {
                    model = model
                }.also {
                    it.barsRecyclerView.scrollToPosition(0)
                }
                viewModel.setSort(Sort.NAME)
            }
            it.sortByDistance.setOnClickListener{
                binding.apply {
                    model = model
                }.also {
                    it.barsRecyclerView.scrollToPosition(0)
                }
                viewModel.setSort(Sort.DISTANCE)
            }
            it.sortByCount.setOnClickListener{
                binding.apply {
                    model = model
                }.also {
                    it.barsRecyclerView.scrollToPosition(0)
                }
                viewModel.setSort(Sort.COUNT)
            }

            it.swiperefresh.setOnRefreshListener {
                viewModel.refreshData()
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = it
        }
    }
}