package com.example.telemedicine

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.example.telemedicine.api.ApiService
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        ApiService.instance.sendFormRequest(Request.Method.GET, "Profile/GetProfile", null,
            {
                val json = JSONObject(it)
                profile_fullname.text = json.getString("FullName")
                profile_birthday.text = json.getString("Birthday")
                profile_email.text = json.getString("Email")
                profile_phone.text = json.getString("Phone")
                profile_address.text = json.getString("Address")
                profile_username.text = json.getString("Username")

                val imageBytes = Base64.decode(json.getString("Base64Photo"), Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                profile_photo.setImageBitmap(bitmap)
            }
        )

        return view
    }
}