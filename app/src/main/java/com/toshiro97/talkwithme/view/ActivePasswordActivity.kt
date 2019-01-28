package com.toshiro97.talkwithme.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.facebook.accountkit.Account
import com.facebook.accountkit.AccountKit
import com.facebook.accountkit.AccountKitCallback
import com.facebook.accountkit.AccountKitError
import com.google.firebase.firestore.FirebaseFirestore
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.untils.Common
import kotlinx.android.synthetic.main.activity_active_password.*
import java.text.SimpleDateFormat
import java.util.*

class ActivePasswordActivity : AppCompatActivity() {

    var rootRef = FirebaseFirestore.getInstance()
    var allUsersRef = rootRef.collection("users")

    private val phoneNumber = Common.phoneNumber

    private var radioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_password)

        progcessBarPass.visibility = View.INVISIBLE

        btnContinuePassword.setOnClickListener {
            showProcessBar()
            if (eDPass.text.toString().trim().length >= 6 && eDPass.text.toString() == eDRePass.text.toString()) {
                val age = eDAge.text.toString().trim()
                val ageNumber = age.toInt()
                if (ageNumber in 1970..2005) {
                    allUsersRef.document(phoneNumber).get()
                            .addOnSuccessListener { document ->
                                if (document != null) {
                                    val currentTime = Calendar.getInstance()
                                    val time = currentTime.time
                                    val formatDateSV = SimpleDateFormat("yyyyMMdd")
                                    val dateToSV = formatDateSV.format(time).toInt()

                                    val selectedId = radioSex!!.checkedRadioButtonId
                                    radioButton = findViewById(selectedId)

                                    allUsersRef.document(phoneNumber)
                                            .update(
                                                    "password", eDPass.text.toString(),
                                                    "timeOnline", dateToSV,
                                                    "username", eDName.text.toString(),
                                                    "sex", radioButton!!.text.toString(),
                                                    "timeOnline", dateToSV,
                                                    "age", eDAge.text.toString().toInt()

                                            )
                                            .addOnSuccessListener {
                                                Toast.makeText(this, "Cập nhật thành công tài khoản", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(ActivePasswordActivity@ this, HomeActivity::class.java)
                                                hiddenProcessBar()
                                                startActivity(intent)


                                            }
                                            .addOnFailureListener { Toast.makeText(this, "Cập nhật thất bại mật khẩu", Toast.LENGTH_SHORT).show() }
                                } else {

                                }
                            }
                            .addOnFailureListener {

                            }
                } else {
                    Toast.makeText(this, "Mời đọc điều khoản sử dụng về năm sinh", Toast.LENGTH_SHORT).show()
                    hiddenProcessBar()
                }
            } else {
                Toast.makeText(this, "Mời nhập mật khẩu chính xác", Toast.LENGTH_SHORT).show()
                hiddenProcessBar()
            }
        }
    }

    fun hiddenProcessBar() {
        Handler().postDelayed({
            progcessBarPass.visibility = View.INVISIBLE
            linearPassword.visibility = View.VISIBLE
        }, 1000)
    }

    fun showProcessBar() {
        progcessBarPass.visibility = View.VISIBLE
        linearPassword.visibility = View.INVISIBLE
    }
}
