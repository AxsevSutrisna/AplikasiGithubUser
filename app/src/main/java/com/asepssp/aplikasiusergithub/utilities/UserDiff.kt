package com.asepssp.aplikasiusergithub.utilities

import androidx.recyclerview.widget.DiffUtil
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems

class UserDiff(
    private val oldList: List<UserResponseItems>,
    private val newList: List<UserResponseItems>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].login == newList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val latest = newList[newItemPosition]
        return old == latest
    }
}