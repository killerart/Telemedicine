package com.example.telemedicine

import java.io.Serializable

class Consultation(
    var ConsId: Int,
    var Name: String,
    var Disease: String,
    var Address: String,
    var Description: String,
    var DocId: Int,
    var IsConfirmed: Boolean
) : Serializable