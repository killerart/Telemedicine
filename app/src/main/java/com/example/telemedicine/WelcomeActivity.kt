package com.example.telemedicine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    fun onRegisterClick(view: View) {
        Intent(this, RegisterActivity::class.java).also {
            startActivity(it)
        }
    }

    fun onLoginClick(view: View) {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        }
    }

    fun onUrgentClick(view: View) {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}