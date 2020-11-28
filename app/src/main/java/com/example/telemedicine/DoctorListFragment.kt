package com.example.telemedicine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DoctorListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorListAdapter: DoctorListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_doctor_list, container, false)
        doctorListAdapter = DoctorListAdapter()
        doctorListAdapter.doctorList.addAll(Doctor(3F), Doctor(3.5F), Doctor(5F), Doctor(4.5F), Doctor(4F))
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = doctorListAdapter
        }
        return view
    }
}