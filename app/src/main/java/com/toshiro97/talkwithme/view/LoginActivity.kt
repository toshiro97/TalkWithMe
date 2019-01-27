package com.toshiro97.talkwithme.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.facebook.accountkit.AccountKitLoginResult
import com.facebook.accountkit.PhoneNumber

import com.facebook.accountkit.ui.AccountKitActivity
import com.facebook.accountkit.ui.AccountKitConfiguration
import com.facebook.accountkit.ui.LoginType
import com.google.firebase.firestore.FirebaseFirestore
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.model.User
import com.toshiro97.talkwithme.untils.Common
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    var rootRef = FirebaseFirestore.getInstance()
    var allUsersRef = rootRef.collection("users")

    val APP_REQUEST_CODE = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progcessBarLG.visibility = View.INVISIBLE

        btnLogin.setOnClickListener {
            showProcessBar()
            allUsersRef.document(Common.phoneNumber).get()
                    .addOnSuccessListener { documentSnapshot ->
                        val user = documentSnapshot.toObject(User::class.java)
                        if (user.password == eDPassword.text.toString()) {
                            val intent = Intent(this, HomeActivity::class.java)
                            hiddenProcessBar()
                            startActivity(intent)
                        } else {
                            hiddenProcessBar()
                            Toast.makeText(this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show()

                        }
                    }
        }


        btnAccoutKitLogin.setOnClickListener {
            phoneAccess()
        }

    }


    private fun phoneAccess() {


        val intent = Intent(this@LoginActivity, AccountKitActivity::class.java)
        val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE,
                AccountKitActivity.ResponseType.TOKEN).setInitialPhoneNumber(PhoneNumber(Common.ccc, Common.phone)); // or .ResponseType.TOKEN
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


                    if (loginResult.accessToken != null) {
                        Toast.makeText(this, "Access Token", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this, "Null Access Token", Toast.LENGTH_SHORT).show()
                    }


                }
            }
            val i = Intent(this, HomeActivity::class.java)
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
            progcessBarLG.visibility = View.INVISIBLE
            linearLogin.visibility = View.VISIBLE
        }, 1000)
    }

    fun showProcessBar() {
        progcessBarLG.visibility = View.VISIBLE
        linearLogin.visibility = View.INVISIBLE
    }
}
