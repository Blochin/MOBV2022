package sk.stu.fei.mobv2022.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.databinding.FragmentBarsBinding
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.services.PreferenceData
import sk.stu.fei.mobv2022.ui.viewmodels.BarListViewModel
import sk.stu.fei.mobv2022.ui.viewmodels.LogInViewModel

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
        }.also {
            it.logout.setOnClickListener {
                PreferenceData.getInstance().clearData(requireContext())
                Navigation.findNavController(it).navigate(R.id.action_to_login)
            }
        }
    }
}