package com.example.tanoti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.email_sign
import kotlinx.android.synthetic.main.activity_signup.password_sign
import kotlinx.android.synthetic.main.activity_signup.sign_btn
import kotlinx.android.synthetic.main.activity_signup.*

class activity_signup : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        firebaseAuth = FirebaseAuth.getInstance()
        sign_btn.setOnClickListener {
            val email = email_sign.text.toString()
            val password = password_sign.text.toString()
            val name = name_sign.text.toString()
            if (email.isEmpty() || password.isEmpty()||name.isEmpty()){
                Toast.makeText(this, "Fill all boxes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            sign_fun(name,email,password)
        }
        login_tv_btn.setOnClickListener { startActivity(Intent(this,activity_login::class.java))
            finish()
        }
    }

    private fun sign_fun(name: String, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                addUserDatabase(name,email,firebaseAuth.currentUser?.uid!!)
                Toast.makeText(this, "User created succefully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, "Somethin went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}