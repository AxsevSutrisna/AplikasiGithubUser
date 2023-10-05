package com.asepssp.aplikasiusergithub.ui.detail.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems
import com.asepssp.aplikasiusergithub.databinding.ListUserGithubBinding
import com.asepssp.aplikasiusergithub.ui.main.MainAdapter
import com.asepssp.aplikasiusergithub.utilities.loadImage

class FollowAdapter(private val listFollow: List<UserResponseItems>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: MainAdapter.OnItemClickCallback

    inner class ViewHolder(private val binding: ListUserGithubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponseItems) {
            with(binding) {
                tvItemName.text = user.login
                imgItemPhoto.loadImage(user.avatarUrl)
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: MainAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFollow.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listFollow[position]
        holder.bind(user)
    }




}