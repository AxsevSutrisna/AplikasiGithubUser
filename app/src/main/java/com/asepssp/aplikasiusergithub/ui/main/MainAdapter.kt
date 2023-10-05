package com.asepssp.aplikasiusergithub.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems
import com.asepssp.aplikasiusergithub.databinding.ListUserGithubBinding
import com.asepssp.aplikasiusergithub.utilities.UserDiff
import com.asepssp.aplikasiusergithub.utilities.loadImage

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val items = mutableListOf<UserResponseItems>()

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponseItems)
    }


    inner class ViewHolder(val binding: ListUserGithubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: UserResponseItems) {
            with(binding) {
                tvItemName.text = githubUser.login
                imgItemPhoto.loadImage(githubUser.avatarUrl)
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(githubUser)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListUserGithubBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val githubUser = items[position]
        holder.bind(githubUser)

    }


    override fun getItemCount(): Int {
        return items.size
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    fun submitFavoriteList(newList: List<UserResponseItems>) {
        val diffCallback = UserDiff(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}