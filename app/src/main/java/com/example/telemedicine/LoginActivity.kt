package com.example.telemedicine

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.telemedicine.api.ApiService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        emailEditText = login_email_edit
        passwordEditText = login_password_edit
    }

    fun onLoginClick(view: View) {
        ApiService.instance.sendLoginRequest(
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        ) {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    fun onSignUpClick(view: View) {
        Intent(this, RegisterActivity::class.java).also {
            startActivity(it)
        }
    }
}