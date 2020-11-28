package com.example.telemedicine

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val ratingTextView: TextView = view.findViewById(R.id.notif_doctor_info_rating_number)
    private val starsView: RatingBar = view.findViewById(R.id.notif_doctor_info_rating_bar)

    fun setRating(rating: Float) {
        ratingTextView.text = rating.toString()
        starsView.rating = rating
    }
}