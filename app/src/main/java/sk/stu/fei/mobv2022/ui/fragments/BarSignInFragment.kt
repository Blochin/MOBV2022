package sk.stu.fei.mobv2022.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.databinding.FragmentBarDetailBinding
import sk.stu.fei.mobv2022.databinding.FragmentBarSignInBinding
import sk.stu.fei.mobv2022.ui.viewmodels.BarDetailViewModel
import sk.stu.fei.mobv2022.ui.viewmodels.BarSignInViewModel

class BarSignInFragment : Fragment() {

    private lateinit var binding: FragmentBarSignInBinding
    private lateinit var viewModel: BarSignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBarSignInBinding.inflate(inflater, container, false)
        return binding.root
    }
}