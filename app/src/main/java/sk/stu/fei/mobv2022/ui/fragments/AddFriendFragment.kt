package sk.stu.fei.mobv2022.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import sk.stu.fei.mobv2022.databinding.FragmentAddFriendBinding
import sk.stu.fei.mobv2022.services.Injection
import sk.stu.fei.mobv2022.ui.viewmodels.FriendViewModel

class AddFriendFragment : Fragment() {

    private lateinit var binding: FragmentAddFriendBinding
    private lateinit var viewModel: FriendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        )[FriendViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }.also { bnd ->
            bnd.addFriendButton.setOnClickListener{
                viewModel.addFriend(bnd.friendName.text.toString())
            }
        }

    }

}