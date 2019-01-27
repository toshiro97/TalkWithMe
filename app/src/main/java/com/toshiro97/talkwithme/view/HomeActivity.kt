package com.toshiro97.talkwithme.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.facebook.accountkit.Account
import com.facebook.accountkit.AccountKit
import com.facebook.accountkit.AccountKitCallback
import com.facebook.accountkit.AccountKitError
import com.google.firebase.firestore.FirebaseFirestore
import com.toshiro97.talkwithme.R
import com.toshiro97.talkwithme.model.User
import com.toshiro97.talkwithme.untils.Common
import com.toshiro97.talkwithme.view.viewpager.MainViewPager
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {

    internal var prevMenuItem: MenuItem? = null

    var rootRef = FirebaseFirestore.getInstance()
    var allUsersRef = rootRef.collection("users")

    private val phoneNumber = Common.phoneNumber

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val currentTime = Calendar.getInstance()
        val time = currentTime.time
        val formatDateSV = SimpleDateFormat("yyyyMMdd")
        val dateToSV = formatDateSV.format(time).toInt()

        allUsersRef.document(phoneNumber)
                .update(
                        "timeOnline", dateToSV
                )
                .addOnSuccessListener {
                    Toast.makeText(this, "Cập nhật thành công thoi gian online", Toast.LENGTH_SHORT).show()


                }
                .addOnFailureListener { Toast.makeText(this, "Cập nhật thất bại mật khẩu", Toast.LENGTH_SHORT).show() }

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> viewPager.currentItem = 0
                R.id.navigation_chat -> viewPager.currentItem = 1
                R.id.navigation_settings -> viewPager.currentItem = 2
            }
            false
        }
        val adapter  = MainViewPager(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (prevMenuItem != null) {
                    prevMenuItem!!.isChecked = false
                } else {
                    bottomNavigation.menu.getItem(0).isChecked = false
                }
                Log.d("page", "onPageSelected: $position")
                bottomNavigation.getMenu().getItem(position).isChecked = true
                prevMenuItem = bottomNavigation.menu.getItem(position)

            }
        })

    }
}
