package com.toshiro97.talkwithme.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.adapter.ListUserChatAdapter
import com.toshiro97.talkwithme.model.User
import com.toshiro97.talkwithme.untils.Common

class HomeFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home,container,false)
        return view
    }


}