package com.toshiro97.talkwithme.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toshiro97.chatappkt.listener.ItemUserListener
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.adapter.ListUserChatAdapter
import com.toshiro97.talkwithme.untils.Common

import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(),ItemUserListener {
    override fun onClickUser(position: Int) {

    }

    internal lateinit var linearLayoutManager: LinearLayoutManager
    internal lateinit var adapter: ListUserChatAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home,container,false)

        val listUser = Common.currentUser.listUser

        adapter = ListUserChatAdapter(listUser!!,this)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        view.recyclerUserHome.layoutManager = linearLayoutManager

        view.recyclerUserHome.adapter = adapter
        return view
    }
}