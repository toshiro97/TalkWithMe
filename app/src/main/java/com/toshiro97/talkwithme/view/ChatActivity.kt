package com.toshiro97.talkwithme.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.database.*
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.adapter.ChatAdapter
import com.toshiro97.talkwithme.model.ChatMessenger
import com.toshiro97.talkwithme.untils.Common
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_chat.view.*

class ChatActivity : AppCompatActivity() {

    var referenceSend = FirebaseDatabase.getInstance().getReference("messenger").
            child(Common.currentUser.phoneNumber + "-" + Common.sendUser.phoneNumber)
    var referenceReceive = FirebaseDatabase.getInstance().getReference("messenger").
            child(Common.sendUser.phoneNumber + "-" + Common.currentUser.phoneNumber)

    var listChat: ArrayList<ChatMessenger> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setSupportActionBar(toolbar)
        val tvName : TextView = toolbar.tvName
        tvName.text = Common.sendUser.username

        getListChat()

        recyclerChat.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,true)
        recyclerChat?.layoutManager = layoutManager
        recyclerChat.adapter = ChatAdapter(listChat)

        btnSend.setOnClickListener {
            val messenger = edtMessenger.text.toString()
            val chatMessengerSend = ChatMessenger(messenger,true)
            referenceSend.push().setValue(chatMessengerSend)

            val chatMessengerRecei = ChatMessenger(messenger,false)
            referenceReceive.push().setValue(chatMessengerRecei)
        }
    }

    private fun getListChat() {
        val query : Query = referenceSend.orderByKey()
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }
            override fun onDataChange(dataSnapShot: DataSnapshot?) {
                listChat.clear()
                val chatMessengers : ArrayList<ChatMessenger> = ArrayList()
                for (snapShot in dataSnapShot!!.children){
                    val chatMessenger : ChatMessenger = snapShot.getValue(ChatMessenger::class.java)!!
                    chatMessengers.add(chatMessenger)
                }
                for (i in chatMessengers.size - 1 downTo 0){
                    listChat.add(chatMessengers[i])
                }
                recyclerChat.adapter.notifyDataSetChanged()
            }
        })
    }
}
