package sk.stu.fei.mobv2022.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import sk.stu.fei.mobv2022.databinding.FragmentSignUpBinding
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.services.PasswordValidation
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
        binding.signUpButton.setOnClickListener {
            if (PasswordValidation.valid(
                    binding.signUpPasswordEditText.text.toString(),
                    binding.signUpConfirmPasswordEditText.text.toString()
                )
            ) {
                binding.signUpConfirmPasswordTextView.visibility = View.INVISIBLE
                binding.signUpConfirmPasswordTextView2.visibility = View.INVISIBLE

                viewModel.signup(
                    binding.signUpNameEditText.text.toString(),
                    binding.signUpPasswordEditText.text.toString()
                )

            } else {
                binding.signUpConfirmPasswordTextView.visibility = View.VISIBLE
                binding.signUpConfirmPasswordTextView2.visibility = View.VISIBLE
            }
        }
    }
}