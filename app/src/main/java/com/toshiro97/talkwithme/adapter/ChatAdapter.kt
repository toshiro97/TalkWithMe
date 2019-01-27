package com.toshiro97.talkwithme.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.model.ChatMessenger
import kotlinx.android.synthetic.main.list_item_message_recv.view.*
import kotlinx.android.synthetic.main.list_item_message_send.view.*


class ChatAdapter(messengerList : ArrayList<ChatMessenger>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var messengerList:ArrayList<ChatMessenger> = messengerList
    private var context : Context? = null

    override fun getItemViewType(position: Int): Int {
        if (messengerList[position].isSend!!){
            return 1
        }else{
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        if (viewType == 0){
            val inflater : LayoutInflater = LayoutInflater.from(context)
            return ChatReceive(inflater.inflate(R.layout.list_item_message_recv,parent,false))
        }else{
            val inflater : LayoutInflater = LayoutInflater.from(context)
            return ChatSend(inflater.inflate(R.layout.list_item_message_send,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return messengerList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            0 -> {
                val viewHolder : ChatReceive = holder as ChatReceive
                val chatMessenger : ChatMessenger = messengerList[position]
                viewHolder.setIsRecyclable(false)
                viewHolder.tvReceive!!.text = chatMessenger.messenger
                viewHolder.img_avatar_receive!!.setImageResource(R.drawable.ic_account_circle_black_24dp)
            }
            1 ->{
                val viewHolder : ChatSend = holder as ChatSend
                val chatMessenger : ChatMessenger = messengerList[position]
                viewHolder.setIsRecyclable(false)
                viewHolder.tvSend!!.text = chatMessenger.messenger

            }
        }
    }
}

class ChatSend(itemView : View) : RecyclerView.ViewHolder(itemView){
    var tvSend : TextView? = null

    init {
        tvSend = itemView.tvSend
    }
}

class ChatReceive(itemView: View) : RecyclerView.ViewHolder(itemView){
    var tvReceive : TextView? = null
    var img_avatar_receive : ImageView? = null

    init {
        tvReceive = itemView.tvReceive
        img_avatar_receive = itemView.img_avatar_receive
    }
}