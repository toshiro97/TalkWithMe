package com.toshiro97.talkwithme.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.toshiro97.chatappkt.listener.ItemUserListener
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.model.User
import kotlinx.android.synthetic.main.item_user_layout.view.*

class ListUserChatAdapter(userList: MutableList<User>, userLastMes: MutableList<String>, itemUserListener: ItemUserListener
) : RecyclerView.Adapter<ListUserChatAdapter.ListUserChatViewHolder>() {

    private var userList: MutableList<User> = userList
    private var itemUserListener: ItemUserListener = itemUserListener
    private var userLastMes: MutableList<String> = userLastMes

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserChatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_layout, parent, false)
        return ListUserChatViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ListUserChatViewHolder, position: Int) {
        val item = userList[position]

        holder.tvNameUser!!.text = item.username
        if (!item.sex!!.isEmpty()) {
            holder.imgSex!!.setImageResource(R.drawable.icon_user)
        } else if (item.sex!! == "Nam") {
            holder.imgSex!!.setImageResource(R.drawable.icon_male)
        } else {
            holder.imgSex!!.setImageResource(R.drawable.icon_female)
        }

        if (item.isOnline!!) {
            holder.imgIsOnline!!.setImageResource(R.drawable.ic_fiber_manual_record_black_24dp)
        } else {
            holder.imgIsOnline!!.visibility = View.INVISIBLE
        }

        holder.itemView.setOnClickListener {
            itemUserListener.onClickUser(position)
        }

        holder.tvMessenger!!.text = userLastMes[position]


    }


    class ListUserChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameUser: TextView? = null
        var tvAge: TextView? = null
        var imgSex: ImageView? = null
        var imgIsOnline: ImageView? = null
        var tvMessenger: TextView? = null

        init {
            tvNameUser = itemView.tvNameUser
            tvAge = itemView.tvAge
            imgSex = itemView.imgSex
            imgIsOnline = itemView.imgIsOnline
            tvMessenger = itemView.tvMessenger
        }
    }
}
