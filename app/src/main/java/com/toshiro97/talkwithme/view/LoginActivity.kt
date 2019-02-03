package com.toshiro97.talkwithme.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast

import com.google.firebase.firestore.FirebaseFirestore
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.model.User
import com.toshiro97.talkwithme.untils.Common
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    var rootRef = FirebaseFirestore.getInstance()
    var allUsersRef = rootRef.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progcessBarLG.visibility = View.INVISIBLE

        btnLogin.setOnClickListener {
            val ccc = sPCode.defaultCountryCode
            val phone = eDphoneNumber.text.toString()
            val phoneNumber = ccc + phone
            showProcessBar()
            allUsersRef.document(phoneNumber).get()
                    .addOnSuccessListener { documentSnapshot ->

                        if (documentSnapshot.exists()) {
                            val user = documentSnapshot.toObject(User::class.java)
                            if (user.password == eDPassword.text.toString()) {
                                val intent = Intent(this, HomeActivity::class.java)
                                hiddenProcessBar()
                                startActivity(intent)
                                Common.phoneNumber = phoneNumber
                                Common.currentUser = user
                            } else {
                                hiddenProcessBar()
                                Toast.makeText(this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show()

                            }
                        }else{
                            hiddenProcessBar()
                            Toast.makeText(this, "Tài khoản chưa tồn tại", Toast.LENGTH_SHORT).show()
                        }
                    }
        }


    }



    fun hiddenProcessBar() {
        Handler().postDelayed({
            progcessBarLG.visibility = View.INVISIBLE
            linearLogin.visibility = View.VISIBLE
        }, 1000)
    }

    fun showProcessBar() {
        progcessBarLG.visibility = View.VISIBLE
        linearLogin.visibility = View.INVISIBLE
    }
}
