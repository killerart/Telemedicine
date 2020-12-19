package com.example.telemedicine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.example.telemedicine.api.ApiService
import kotlinx.android.synthetic.main.sign_up_include.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var fullNameEditText: EditText
    private lateinit var birthdayEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        fullNameEditText = sign_up_full_name_edit
        birthdayEditText = sign_up_birthday_edit
        emailEditText = sign_up_email_edit
        phoneEditText = sign_up_phone_edit
        addressEditText = sign_up_address_edit
        usernameEditText = sign_up_username_edit
        passwordEditText = sign_up_password_edit
    }

    fun onNextClick(view: View) {
        val formData = hashMapOf(
            "FullName" to fullNameEditText.text.toString(),
            "Birthday" to birthdayEditText.text.toString(),
            "Email" to emailEditText.text.toString(),
            "Phone" to phoneEditText.text.toString(),
            "Address" to addressEditText.text.toString(),
            "Username" to usernameEditText.text.toString(),
            "Password" to passwordEditText.text.toString(),
            "Base64Photo" to "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"
        )

        ApiService.instance.sendFormRequest(Request.Method.GET, "Register/UserReg", formData,
            { response ->
                Log.d("api", response)
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                }
            },
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        )
    }
}