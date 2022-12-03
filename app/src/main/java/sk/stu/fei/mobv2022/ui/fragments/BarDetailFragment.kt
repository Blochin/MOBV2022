package sk.stu.fei.mobv2022.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sk.stu.fei.mobv2022.R
import sk.stu.fei.mobv2022.databinding.FragmentBarDetailBinding
import sk.stu.fei.mobv2022.databinding.FragmentBarsBinding
import sk.stu.fei.mobv2022.ui.viewmodels.BarListViewModel

class BarDetailFragment : Fragment() {

    private lateinit var binding: FragmentBarDetailBinding
    private lateinit var viewModel: BarListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBarDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

}