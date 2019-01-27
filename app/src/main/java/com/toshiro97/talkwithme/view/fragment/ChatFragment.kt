package com.toshiro97.talkwithme.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.toshiro97.talkwithme.R
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_chat.view.*


class ChatFragment : Fragment() {

    var rootRef = FirebaseFirestore.getInstance()
    var allUsersRef = rootRef.collection("users")
    var sex = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat,container,false)



        view.radioSex.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = view!!.findViewById<RadioButton>(checkedId)
            sex = radioButton.text.toString()
        }

        view.btnFindFriend.setOnClickListener {
            getFriendChat(sex)
        }


        return view
    }

    private fun getFriendChat(sex: String) {
        val currentTime = Calendar.getInstance()
        currentTime.add(Calendar.DATE,-1)
        val time = currentTime.time
        val formatDateSV = SimpleDateFormat("yyyyMMdd")
        val dateToSV = formatDateSV.format(time)

        val query = allUsersRef.whereEqualTo("sex","Nam").orderBy("timeOnline")

        Log.d("","");

        query.get().addOnCompleteListener {
            if (it.isSuccessful) {
                it.result

            } else {

            }
        }

    }
}