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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.notifications_include.*

class NotificationsFragment(private val mainActivity: MainActivity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onResume() {
        super.onResume()
        mainActivity.consultation?.let { consultation ->
            mainActivity.doctor?.let { doctor ->
                notif_doctor_info_name.text = doctor.FullName
                notif_doctor_info_rating_bar.rating = doctor.Stars
                notif_doctor_info_rating_number.text = doctor.Stars.toString()
                notif_doctor_info_specialty.text = doctor.Specs
                val imageBytes = Base64.decode(doctor.Photo, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                notif_doctor_info_photo.setImageBitmap(bitmap)
            }
            notifications_name.text = consultation.Name
            notifications_request_description.text = consultation.Description
            notifications_disease.text = consultation.Disease
            notifications_location.text = consultation.Address
        }

    }
}