package com.example.tanoti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_launcher.*

class activity_launcher : AppCompatActivity() {
    lateinit var firebase: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        firebase = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = firebase.currentUser
        if(currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            val intent = Intent(this,activity_signup::class.java)
            startActivity(intent)
            finish()
        }
    }
}