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
import sk.stu.fei.mobv2022.databinding.FragmentSignUpBinding
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.services.PasswordHash
import sk.stu.fei.mobv2022.services.PreferenceData
import sk.stu.fei.mobv2022.services.Validation
import sk.stu.fei.mobv2022.ui.viewmodels.SignUpViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        )[SignUpViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
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

        binding.btnSignup.setOnClickListener {
            if(Validation.validUser(binding.signupName.text.toString())){
                binding.tvSignUpConfirmUser.visibility = View.INVISIBLE
            } else {
                binding.tvSignUpConfirmUser.visibility = View.VISIBLE
            }
            if (Validation.validPassword(
                    binding.signupPassword.text.toString(),
                    binding.signupConfirmPassword.text.toString()
                )
            ) {
                binding.signupValidationPassword.visibility = View.INVISIBLE
                binding.signupValidationPassword2.visibility = View.INVISIBLE

                val password = binding.signupPassword.text.toString()
                val passwordHash = PasswordHash.getPassword(password)

                viewModel.signup(
                    binding.signupName.text.toString(),
                    passwordHash
                )

            } else {
                binding.signupValidationPassword.visibility = View.VISIBLE
                binding.signupValidationPassword2.visibility = View.VISIBLE
            }
        }

        binding.signupSubTitle.setOnClickListener {
            it.findNavController().navigate(R.id.action_to_login)
        }

        viewModel.user.observe(viewLifecycleOwner){
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(),it)
                Navigation.findNavController(requireView()).navigate(R.id.action_to_all_bars)
            }
        }
    }
}