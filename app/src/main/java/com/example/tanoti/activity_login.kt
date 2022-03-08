package com.example.tanoti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class activity_login : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        login_btn.setOnClickListener {
            val email = email_login.text.toString()
            val password = password_login.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fill boxes properway", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            login_fun(email,password)
        }
        sign_tv_btn.setOnClickListener { startActivity(Intent(this,activity_signup::class.java))
            finish()
        }

    }
    private fun login_fun(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful){
                Toast.makeText(this, "Login succefully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Somethin went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}