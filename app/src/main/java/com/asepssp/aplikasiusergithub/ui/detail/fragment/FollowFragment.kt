package com.asepssp.aplikasiusergithub.ui.detail.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems
import com.asepssp.aplikasiusergithub.databinding.FragmentFollowBinding
import com.asepssp.aplikasiusergithub.ui.detail.DetailActivity
import com.asepssp.aplikasiusergithub.ui.detail.DetailViewModel
import com.asepssp.aplikasiusergithub.ui.main.MainAdapter

class FollowFragment : Fragment() {

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_NAME = "app_name"
    }

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var followViewModel: FollowViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        setRV()
        observeViewModel()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    private fun setRV() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }


    private fun observeViewModel() {
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        arguments?.getString(ARG_NAME)

        followViewModel.userFollowers(
            activity?.intent?.getStringExtra(DetailViewModel.USERNAME).toString()
        )
        followViewModel.userFollowing(
            activity?.intent?.getStringExtra(DetailViewModel.USERNAME).toString()
        )
        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        when (index) {
            1 -> followViewModel.listFollower.observe(viewLifecycleOwner)
            { setUserFollow(it) }
            2 -> followViewModel.listFollowing.observe(viewLifecycleOwner)
            { setUserFollow(it) }
        }

    }


    private fun putDetailUser(data: UserResponseItems) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailViewModel.USERNAME, data.login)
        intent.putExtra(DetailViewModel.AVATAR_URL, data.avatarUrl)
        startActivity(intent)
    }


    private fun setUserFollow(userFoll: List<UserResponseItems>) {
        val adapter = FollowAdapter(userFoll)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponseItems) {
                putDetailUser(data)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}