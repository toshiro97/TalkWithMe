package com.toshiro97.talkwithme.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.toshiro97.talkwithme.R
import kotlinx.android.synthetic.main.activity_start.*
import com.google.firebase.firestore.FirebaseFirestore
import android.view.View
import android.widget.Toast
import com.facebook.accountkit.*
import com.facebook.accountkit.ui.AccountKitActivity
import com.facebook.accountkit.ui.AccountKitConfiguration
import com.facebook.accountkit.ui.LoginType
import com.toshiro97.talkwithme.model.User
import com.toshiro97.talkwithme.untils.Common


class StartActivity : AppCompatActivity() {


    var rootRef = FirebaseFirestore.getInstance()
    var allUsersRef = rootRef.collection("users")
    val APP_REQUEST_CODE = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        progcessBarAP.visibility = View.INVISIBLE

        btnContinue.setOnClickListener {
            val ccc = sPCode.defaultCountryCode
            val phone = eDphoneNumber.text.toString()

            val phoneNumber = ccc + phone


            showProcessBar()
            allUsersRef.document(phoneNumber).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            Common.phoneNumber = phoneNumber
                            val intent = Intent(StartActivity@ this, LoginActivity::class.java)
                            hiddenProcessBar()
                            Common.phoneNumber = phoneNumber
                            Common.phone = phone
                            Common.ccc = ccc
                            startActivity(intent)
                        } else {

                            phoneAccess()
                            hiddenProcessBar()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this,"Thất bại",Toast.LENGTH_SHORT).show()
                    }
        }
    }

    private fun phoneAccess() {

        val ccc = sPCode.defaultCountryCode
        val phone = eDphoneNumber.text.toString()

        val intent = Intent(this@StartActivity, AccountKitActivity::class.java)
        val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE,
                AccountKitActivity.ResponseType.TOKEN).setInitialPhoneNumber(PhoneNumber(ccc, phone)) // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build())
        startActivityForResult(intent, APP_REQUEST_CODE)
    }


    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            val loginResult = data.getParcelableExtra<AccountKitLoginResult>(AccountKitLoginResult.RESULT_KEY)



            when {
                loginResult.error != null -> Toast.makeText(this, loginResult.error!!.errorType.message + "", Toast.LENGTH_SHORT).show()
                loginResult.wasCancelled() -> Toast.makeText(this, "SendAu Cancelled", Toast.LENGTH_SHORT).show()
                else -> {

                    val ccc = sPCode.defaultCountryCode.toString()
                    val phone = eDphoneNumber.text.toString()
                    val phoneNumber = ccc + phone

                    if (loginResult.accessToken != null) {
                        Toast.makeText(this, "Access Token", Toast.LENGTH_SHORT).show()

                        val userInfor = User(phoneNumber, "", "", true, 0, "", "", phoneNumber,13)

                        allUsersRef.document(phoneNumber)
                                .set(userInfor)
                                .addOnSuccessListener { Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show() }
                                .addOnFailureListener { Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show() }

                        Common.phoneNumber = phoneNumber

                    } else {
                        Toast.makeText(this, "Null Access Token", Toast.LENGTH_SHORT).show()
                    }


                }
            }
            val i = Intent(this, ActivePasswordActivity::class.java)
            startActivity(i)
            Toast.makeText(
                    this,
                    "ERROR",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    fun hiddenProcessBar() {
        Handler().postDelayed({
            progcessBarAP.visibility = View.INVISIBLE
            linearAccessPhone.visibility = View.VISIBLE
        }, 1000)
    }

    fun showProcessBar() {
        progcessBarAP.visibility = View.VISIBLE
        linearAccessPhone.visibility = View.INVISIBLE
    }

}
