package com.ttp.usermanagement.ui.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ttp.usermanagement.R
import com.ttp.usermanagement.data.model.UserInfo
import com.ttp.usermanagement.databinding.ItemUserBinding

class UserListAdapter(
    private var userList: List<UserInfo?>,
    private val userClickListener: (UserInfo) -> Unit
) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        user?.let {
            holder.bind(it)
            holder.noUser.text = (position + 1).toString()
            if (it.admin == 1) {
                holder.itemView.setBackgroundResource(R.drawable.bg_admin)
            } else {
                holder.itemView.setBackgroundResource(R.drawable.bg_out_line_4)
            }
            holder.itemView.setOnClickListener {
                userClickListener.invoke(user)
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<UserInfo?>) {
        userList = newList
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var noUser = binding.tvNo

        @SuppressLint("SetTextI18n")
        fun bind(user: UserInfo) {
            binding.user = user
            binding.isAdmin = user.admin == 1
            binding.executePendingBindings()
        }
    }
}