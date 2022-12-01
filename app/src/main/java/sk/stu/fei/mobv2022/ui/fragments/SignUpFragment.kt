package sk.stu.fei.mobv2022.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.databinding.FragmentSignUpBinding
import sk.stu.fei.mobv2022.services.Injection
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
        binding.acbSignUp.setOnClickListener {
            if(Validation.validUser(binding.tietSignUpName.text.toString())){
                binding.tvSignUpConfirmUser.visibility = View.INVISIBLE
            } else {
                binding.tvSignUpConfirmUser.visibility = View.VISIBLE
            }
            if (Validation.validPassword(
                    binding.tietSignUpPassword.text.toString(),
                    binding.tietSignUpConfirmPassword.text.toString()
                )
            ) {
                binding.tvSignUpConfirmPassword.visibility = View.INVISIBLE
                binding.tvSignUpConfirmPassword2.visibility = View.INVISIBLE

                viewModel.signup(
                    binding.tietSignUpName.text.toString(),
                    binding.tietSignUpPassword.text.toString()
                )

            } else {
                binding.tvSignUpConfirmPassword.visibility = View.VISIBLE
                binding.tvSignUpConfirmPassword2.visibility = View.VISIBLE
            }
        }

        binding.tvLoginSubTitle.setOnClickListener {
            it.findNavController().navigate(R.id.action_to_login)
        }
    }
}