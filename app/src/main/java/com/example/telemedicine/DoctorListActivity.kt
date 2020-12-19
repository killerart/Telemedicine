package com.example.telemedicine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.example.telemedicine.MainApplication.Companion.context
import com.example.telemedicine.api.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DoctorListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorListAdapter: DoctorListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list)
        doctorListAdapter = DoctorListAdapter()
        ApiService.instance.sendFormRequest(
            Request.Method.GET, "Doctor/GetDoctorList", null,
            {
                val doctorList: List<Doctor> = Gson().fromJson(it, object : TypeToken<List<Doctor>>() {}.type)
                doctorListAdapter.doctorList.addAll(doctorList)
            }
        )
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = doctorListAdapter
        }
    }
}