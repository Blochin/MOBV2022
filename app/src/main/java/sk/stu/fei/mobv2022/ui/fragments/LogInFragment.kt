package sk.stu.fei.mobv2022.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.databinding.FragmentLogInBinding
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.services.PreferenceData
import sk.stu.fei.mobv2022.services.Validation
import sk.stu.fei.mobv2022.ui.viewmodels.LogInViewModel
import sk.stu.fei.mobv2022.ui.viewmodels.SignUpViewModel

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private lateinit var viewModel: LogInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        )[LogInViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid ?: "").isNotBlank()) {
            Navigation.findNavController(view).navigate(R.id.action_to_all_bars)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }

        binding.btnLogin.setOnClickListener {
            if (Validation.validUser(binding.loginName.text.toString())) {
                binding.loginValidationName.visibility = View.INVISIBLE;
            } else {
                binding.loginValidationName.visibility = View.VISIBLE;
                //viewModel.show("fields must not be empty") //TODO vytvorit pre kazdy validacny field binding
            }
            if(Validation.validPassword(binding.loginPassword.text.toString(), binding.loginPassword.text.toString())){
                binding.loginValidationPassword.visibility = View.INVISIBLE
                viewModel.login(binding.loginName.text.toString(), binding.loginPassword.text.toString())
            } else {
                binding.loginValidationPassword.visibility = View.VISIBLE
            }
        }

        binding.loginSubTitle.setOnClickListener {
            it.findNavController().navigate(R.id.action_to_sign_up)
        }

        viewModel.user.observe(viewLifecycleOwner){
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(),it)
                Navigation.findNavController(requireView()).navigate(R.id.action_to_all_bars)
            }
        }
    }

}