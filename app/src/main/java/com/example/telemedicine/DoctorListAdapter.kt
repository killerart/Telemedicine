package com.example.telemedicine

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback

class DoctorListAdapter() : RecyclerView.Adapter<DoctorViewHolder>() {
    var doctorList: SortedList<Doctor>

    init {
        doctorList = SortedList(Doctor::class.java,
            object : SortedListAdapterCallback<Doctor>(this) {
                override fun compare(o1: Doctor?, o2: Doctor?): Int {
                    if (o1 != null && o2 != null)
                        return o2.Stars.compareTo(o1.Stars)
                    if (o1 == null)
                        return 1
                    return -1
                }

                override fun areContentsTheSame(oldItem: Doctor?, newItem: Doctor?): Boolean {
                    return oldItem == newItem
                }

                override fun areItemsTheSame(item1: Doctor?, item2: Doctor?): Boolean {
                    return item1 == item2
                }
            })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.viewholder_doctor, parent, false)
            .let {
                return DoctorViewHolder(parent.context, it)
            }
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        doctorList[position]?.also { doctor ->
            holder.setData(doctor)
        }
    }

    override fun getItemCount(): Int {
        return doctorList.size()
    }
}