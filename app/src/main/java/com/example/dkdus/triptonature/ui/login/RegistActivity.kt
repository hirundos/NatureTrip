package com.example.dkdus.triptonature.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dkdus.triptonature.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_regist.*

class RegistActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        firebaseAuth = FirebaseAuth.getInstance()

        register_btn.setOnClickListener {
            val email = username.text.toString()
            val pwd = password.text.toString()
            firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this) { task ->
                    when {
                        task.isSuccessful -> {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        pwd.length < 6 -> {
                            textAlert.text = "비밀번호는 6자 이상이어야 합니다"
                        }
                        else -> {
                            textAlert.text = "이미 존재하는 아이디입니다"
                        }
                    }
                }
        }
    }
}