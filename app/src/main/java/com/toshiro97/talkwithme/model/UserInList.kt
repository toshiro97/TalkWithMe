package com.toshiro97.talkwithme.model

class UserInList {
    var username : String ?= null
    var isOnline : Boolean ?= null
    var sex : String ?= null
    var age: Int? = null
    var phoneNumber: String? = null

    constructor()

    constructor(username: String?, isOnline: Boolean?, sex: String?, age: Int?, phoneNumber: String?) {
        this.username = username
        this.isOnline = isOnline
        this.sex = sex
        this.age = age
        this.phoneNumber = phoneNumber
    }


}