package com.toshiro97.talkwithme.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.google.firebase.database.FirebaseDatabase

import com.toshiro97.talkwithme.R



@Suppress("UNREACHABLE_CODE")
class SettingFragment : Fragment() {

    var db = FirebaseDatabase.getInstance().getReference("Users")!!
    val TAG = "logout"

    var phone = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting,container,false)

        return view
    }

}