package com.example.telemedicine

import android.util.Base64
import java.io.Serializable

class Doctor(
    var DocId: Int,
    var FullName: String,
    var Specs: String,
    var Address: String,
    var About: String,
    var Stars: Float,
    var Photo: String
) : Serializable