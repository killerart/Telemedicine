package com.example.telemedicine

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import kotlinx.android.synthetic.main.activity_doctor_details.*

class DoctorDetailsActivity : AppCompatActivity() {
    companion object {
        const val DOCTOR_KEY = "DOCTOR_KEY"
    }

    private lateinit var doctor: Doctor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)
        doctor = intent.getSerializableExtra(DOCTOR_KEY) as Doctor
        notif_doctor_info_name.text = doctor.FullName
        notif_doctor_info_specialty.text = doctor.Specs
        notif_doctor_info_rating_bar.rating = doctor.Stars
        notif_doctor_info_rating_number.text = doctor.Stars.toString()
        val imageBytes = Base64.decode(doctor.Photo, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        notif_doctor_info_photo.setImageBitmap(bitmap)
        doctor_info_about_tv.text = doctor.About
        doctor_info_location_street.text = doctor.Address
    }
}