package com.example.telemedicine

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.View
import android.view.View.OnClickListener
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.notifications_include.*

class DoctorViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    private val ratingTextView: TextView = view.findViewById(R.id.notif_doctor_info_rating_number)
    private val starsView: RatingBar = view.findViewById(R.id.notif_doctor_info_rating_bar)
    private val nameView: TextView = view.findViewById(R.id.notif_doctor_info_name)
    private val specialityView: TextView = view.findViewById(R.id.notif_doctor_info_specialty)
    private val imageView: CircleImageView = view.findViewById(R.id.notif_doctor_info_photo)
    private lateinit var doctor: Doctor

    init {
        view.setOnClickListener {
            Intent(context, DoctorDetailsActivity::class.java)
                .apply {
                    putExtra(DoctorDetailsActivity.DOCTOR_KEY, doctor)
                }
                .also {
                    context.startActivity(it)
                }
        }
    }

    fun setData(doctor: Doctor) {
        this.doctor = doctor
        nameView.text = doctor.FullName
        specialityView.text = doctor.Specs
        ratingTextView.text = doctor.Stars.toString()
        starsView.rating = doctor.Stars
        val imageBytes = Base64.decode(doctor.Photo, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        imageView.setImageBitmap(bitmap)
    }
}