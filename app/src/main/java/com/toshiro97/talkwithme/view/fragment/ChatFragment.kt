package com.toshiro97.talkwithme.view.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.toshiro97.talkwithme.R
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.toshiro97.talkwithme.model.User
import com.toshiro97.talkwithme.untils.Common
import com.toshiro97.talkwithme.view.ChatActivity
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*




class ChatFragment : Fragment() {

    var rootRef = FirebaseFirestore.getInstance()
    var allUsersRef = rootRef.collection("users")
    var sex = ""
    val max = 32
    val min = 13
    var current = 20
    var age = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat,container,false)

        view.radioSex.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = view!!.findViewById<RadioButton>(checkedId)
            sex = radioButton.text.toString()
        }

        view.seek_bar_age.max = max
        view.seek_bar_age.progress = current-min
        view.tv_age.text = "" + current
        age = current

        view.seek_bar_age.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                current = progress + min
                view.tv_age.text = "" + current
                age = current
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        view.btnFindFriend.setOnClickListener {
            getFriendChat(sex,age)
        }


        return view
    }

    private fun getFriendChat(sex: String, age : Int) {

        showProcessBar()

        val currentTime = Calendar.getInstance()

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val ageYear = year - age

        currentTime.add(Calendar.DATE,-1)
        val time = currentTime.time
        val formatDateSV = SimpleDateFormat("yyyyMMdd")
        val dateToSV = formatDateSV.format(time)

        val desireAge = year - Common.currentUser.age!!
        val desireSex = Common.currentUser.sex

        Log.d("sexAge", desireAge.toString() + desireSex.toString())

        if (view!!.radioAll.isChecked){
            val query = allUsersRef.whereEqualTo("age", ageYear).whereEqualTo("desireSex",desireSex).whereEqualTo("desireAge",desireAge)
            query.orderBy("timeOnline").limit(1)

            query.get().addOnSuccessListener {
                if (it.isEmpty) {
                    Toast.makeText(context, "Không có thông tin thích hợp", Toast.LENGTH_SHORT).show()
                    hiddenProcessBar()
                } else {
                    val userCollection = it.toObjects(User::class.java)
                    Common.sendUser = userCollection[0]

                    allUsersRef.document(Common.phoneNumber)
                            .update(
                                    "listUser", FieldValue.arrayUnion(userCollection[0])
                            )
                            .addOnSuccessListener {
                                Toast.makeText(context, "Cập nhật list thành công", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, ChatActivity::class.java)
                                hiddenProcessBar()
                                startActivity(intent)


                            }
                            .addOnFailureListener { Toast.makeText(context, "Cập nhật thất bại mật khẩu", Toast.LENGTH_SHORT).show() }

                    Log.d("userCollection", userCollection.toString())
                }
            }
        }

        else {
            val query = allUsersRef.whereEqualTo("sex", sex).whereEqualTo("age", ageYear).whereEqualTo("desireSex",desireSex).whereEqualTo("desireAge",desireAge)
            query.orderBy("timeOnline").limit(1)

            query.get().addOnSuccessListener {
                if (it.isEmpty) {
                    Toast.makeText(context, "Không có thông tin thích hợp", Toast.LENGTH_SHORT).show()
                    hiddenProcessBar()
                } else {
                    val userCollection = it.toObjects(User::class.java)
                    Common.sendUser = userCollection[0]

                    allUsersRef.document(Common.phoneNumber)
                            .update(
                                    "listUser", FieldValue.arrayUnion(userCollection[0])
                            )
                            .addOnSuccessListener {
                                Toast.makeText(context, "Cập nhật list thành công", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, ChatActivity::class.java)
                                hiddenProcessBar()
                                startActivity(intent)


                            }
                            .addOnFailureListener { Toast.makeText(context, "Cập nhật thất bại mật khẩu", Toast.LENGTH_SHORT).show() }

                    Log.d("userCollection", userCollection.toString())
                }
            }
        }

    }

    fun hiddenProcessBar() {
        Handler().postDelayed({
            progressBarChat.visibility = View.INVISIBLE
            linearChat.visibility = View.VISIBLE
        }, 1000)
    }

    fun showProcessBar() {
        progressBarChat.visibility = View.VISIBLE
        linearChat.visibility = View.INVISIBLE
    }
}