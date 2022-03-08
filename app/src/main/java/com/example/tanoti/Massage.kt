package com.example.tanoti

import android.content.IntentSender

class Massage {
    var massage : String? = null
    var senderId : String?= null
    constructor(){}
    constructor(massage: String?,senderId:String?){
        this.massage = massage
        this.senderId = senderId
    }
}