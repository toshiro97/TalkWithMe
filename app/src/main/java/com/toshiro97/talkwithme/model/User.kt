package com.toshiro97.talkwithme.model

class User {
    var username: String? = null
    var password: String? = null
    var secureCode: String? = null
    var isOnline: Boolean? = null
    var timeOnline: Int? = null
    var sex: String? = null
    var timeCheck: String? = null
    var phoneNumber: String? = null
    var age: Int? = null
    var desireAge : Int? = null
    var desireSex : String? = null
    var listUser: MutableList<User> ?= null

    constructor()

    constructor(
            username: String?,
            password: String?,
            secureCode: String?,
            isOnline: Boolean?,
            timeOnline: Int?,
            sex: String?,
            timeCheck: String?,
            phoneNumber: String?,
            age: Int?,
            listUser : MutableList<User>?,
            desireAge : Int?,
            desireSex : String?
    ) {
        this.username = username
        this.password = password
        this.secureCode = secureCode
        this.isOnline = isOnline
        this.timeOnline = timeOnline
        this.sex = sex
        this.timeCheck = timeCheck
        this.phoneNumber = phoneNumber
        this.age = age
        this.listUser = listUser
        this.desireAge = desireAge
        this.desireSex = desireSex
    }


}