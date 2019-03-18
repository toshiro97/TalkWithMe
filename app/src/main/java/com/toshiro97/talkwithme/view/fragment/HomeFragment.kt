package com.toshiro97.talkwithme.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.toshiro97.chatappkt.listener.ItemUserListener
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.adapter.ListUserChatAdapter
import com.toshiro97.talkwithme.untils.Common

import kotlinx.android.synthetic.main.fragment_home.view.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.toshiro97.talkwithme.model.ChatMessenger
import com.toshiro97.talkwithme.model.User
import com.toshiro97.talkwithme.view.ChatActivity


class HomeFragment : Fragment(), ItemUserListener {
    override fun onClickUser(position: Int) {
        Common.sendUser = listUser[position]
        val intent = Intent(context, ChatActivity::class.java)
        startActivity(intent)
    }

    internal lateinit var linearLayoutManager: LinearLayoutManager
    internal lateinit var adapter: ListUserChatAdapter
    var listUser = mutableListOf<User>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        listUser = Common.currentUser.listUser!!
        val userLastMess = mutableListOf<String>()


        for (user in listUser!!) {
            val referenceSend = FirebaseDatabase.getInstance().getReference("messenger").child(Common.currentUser.phoneNumber + "-" + user.phoneNumber)
            val lastQuery = referenceSend.orderByKey().limitToLast(1)
            lastQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("abcde", dataSnapshot.toString())
                    for (snapshot in dataSnapshot.children) {
                        val chatMessenger: ChatMessenger = snapshot.getValue(ChatMessenger::class.java)!!
                        val messenger = chatMessenger.messenger

                        userLastMess.add(messenger.toString())

                        adapter = ListUserChatAdapter(listUser, userLastMess, this@HomeFragment)


                        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        view.recyclerUserHome.layoutManager = linearLayoutManager

                        view.recyclerUserHome.adapter = adapter
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("abcde", databaseError.toString())
                }
            })
        }



        return view
    }
}