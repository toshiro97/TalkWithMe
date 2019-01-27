package com.toshiro97.talkwithme.model

class ChatMessenger {
    var messenger : String? = null
    var isSend : Boolean? = false

    constructor()

    constructor(messenger: String?, isSend: Boolean?) {
        this.messenger = messenger
        this.isSend = isSend
    }


}