package com.example.telemedicine.api

import android.content.Intent
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.telemedicine.LoginActivity
import com.example.telemedicine.MainApplication
import org.json.JSONObject

class ApiService private constructor() {
    companion object {
        val instance: ApiService = ApiService()
        const val apiUrl = "http://81.180.72.17/api/"
    }

    var userToken: String? = null
    private var email: String? = null
    private var password: String? = null
    private var queue: RequestQueue = Volley.newRequestQueue(MainApplication.context)

    fun sendFormRequest(
        method: Int,
        url: String,
        formData: MutableMap<String, String>?,
        listener: Response.Listener<String>,
        errorListener: Response.ErrorListener? = null
    ) {
        val request = object : StringRequest(method, apiUrl + url, listener,
            { response ->
                if (response.networkResponse == null || response.networkResponse.statusCode == 401) {
                    sendLoginRequest(email, password) {
                        sendFormRequest(method, url, formData, listener, errorListener)
                    }
                } else {
                    Log.d("api", response.message.toString())
                    errorListener?.onErrorResponse(response)
                }
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded"
            }

            override fun getParams(): MutableMap<String, String> {
                return formData ?: super.getParams()
            }

            override fun getHeaders(): MutableMap<String, String> {
                return if (userToken != null) {
                    hashMapOf(
                        "token" to userToken.orEmpty()
                    )
                } else super.getHeaders()
            }
        }
        queue.add(request)
    }

    fun sendLoginRequest(
        email: String?,
        password: String?,
        listener: Response.Listener<String>? = null,
        errorListener: Response.ErrorListener? = null
    ) {
        val request =
            object : StringRequest(Method.POST, apiUrl + "Login/UserAuth",
                { response ->
                    userToken = JSONObject(response).getString("Message")
                    this@ApiService.email = email
                    this@ApiService.password = password
                    listener?.onResponse(response)
                },
                {
                    if (errorListener != null) {
                        errorListener.onErrorResponse(it)
                    } else {
                        with(MainApplication.context) {
                            Intent(this, LoginActivity::class.java).also {
                                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(it)
                            }
                        }
                    }
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/x-www-form-urlencoded"
                }

                override fun getParams(): MutableMap<String, String> {
                    return hashMapOf(
                        "Email" to email.orEmpty(),
                        "Password" to password.orEmpty()
                    )
                }
            }
        queue.add(request)
    }

    fun jsonRequest(
        method: Int,
        url: String,
        jsonData: JSONObject?,
        listener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener? = null
    ) {
        val request = object : JsonObjectRequest(method, apiUrl + url, jsonData, listener,
            { response ->
                if (response.networkResponse.statusCode == 401) {
                    sendLoginRequest(email, password) {
                        jsonRequest(method, url, jsonData, listener, errorListener)
                    }
                } else {
                    Log.d("api", response.message.toString())
                    errorListener?.onErrorResponse(response)
                }
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return hashMapOf(
                    "token" to userToken.orEmpty()
                )
            }
        }
        queue.add(request)
    }
}