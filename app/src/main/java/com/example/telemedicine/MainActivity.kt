package com.example.telemedicine

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.example.telemedicine.api.ApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.home_include.*
import kotlinx.android.synthetic.main.notifications_include.*


class MainActivity : AppCompatActivity() {
    private val backStack: ArrayDeque<Int> = ArrayDeque()
    private var selectedId: Int = R.id.navigation_home
    private lateinit var fragmentTitleTextView: TextView
    private lateinit var navigationView: BottomNavigationView
    var consultation: Consultation? = null
    var doctor: Doctor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            selectedId = savedInstanceState.getInt(MENU_KEY)
        }
        fragmentTitleTextView = findViewById(R.id.action_bar_title)
        navigationView = findViewById(R.id.navigation_view)
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigationView.selectedItemId = selectedId
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MENU_KEY, selectedId)
    }

    override fun onBackPressed() {
        if (backStack.size <= 1) finish() else {
            backStack.removeLast()
            if (backStack.lastOrNull() != null) navigationView.selectedItemId = backStack.last()
        }
    }

    private val onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            selectedId = menuItem.itemId
            setFragment(menuItem.itemId)
            backStack.remove(menuItem.itemId)
            backStack.add(menuItem.itemId)
            setActionBarTitle(menuItem.itemId)
            true
        }

    private fun setFragment(itemId: Int) {
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(itemId.toString())
        if (fragment == null) {
            replaceFragment(createNewFragment(itemId), itemId.toString())
        } else {
            if (fragment !== supportFragmentManager.findFragmentById(R.id.content_container)) {
                replaceFragment(fragment, null)
            }
        }
    }

    private fun createNewFragment(itemId: Int): Fragment {
        val item = navigationView.menu
        val add = item.findItem(R.id.navigation_add)
        add.isEnabled = false
        when (itemId) {
            R.id.navigation_home -> return HomeFragment()
            R.id.navigation_notifications -> return NotificationsFragment(this)
            R.id.navigation_schedule -> return ScheduleFragment()
            R.id.navigation_profile -> return ProfileFragment()
        }
        return HomeFragment()
    }

    private fun replaceFragment(fragment: Fragment?, tag: String?) {
        val fragmentTransaction: FragmentTransaction? = fragment?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_container, it, tag)
        }
        if (tag != null) {
            fragmentTransaction?.addToBackStack(tag)
        }
        fragmentTransaction?.commit()
    }

    private fun setActionBarTitle(itemId: Int) {
        when (itemId) {
            R.id.navigation_home -> fragmentTitleTextView.text = getString(R.string.title_home)
            R.id.navigation_notifications -> fragmentTitleTextView.text =
                getString(R.string.title_notifications)
            R.id.navigation_schedule -> fragmentTitleTextView.text =
                getString(R.string.title_schedule)
            R.id.navigation_profile -> fragmentTitleTextView.text =
                getString(R.string.title_profile)
        }
    }

    fun onRequestClick(view: View) {
        val formData = hashMapOf(
            "Name" to home_name_edit.text.toString(),
            "Disease" to home_disease_edit.text.toString(),
            "Address" to home_name_edit.text.toString(),
            "Description" to home_description_edit.text.toString(),
        )
        ApiService.instance.sendFormRequest(Request.Method.POST, "Doctor/AddConsultation", formData,
            { consultationJson ->
                consultation = Gson().fromJson(consultationJson, Consultation::class.java)
                    .also { consultation ->
                        ApiService.instance.sendFormRequest(
                            Request.Method.GET,
                            "Doctor/GetDoctor/${consultation.DocId}",
                            null,
                            { doctorJson ->
                                doctor = Gson().fromJson(doctorJson, Doctor::class.java)
                                setFragment(R.id.navigation_notifications)
                            }
                        )
                    }
            }
        )
//        Intent(this, DoctorListActivity::class.java).also {
//            startActivity(it)
//        }
    }

    fun onDoctorClick(view: View?) {
        Intent(this, DoctorDetailsActivity::class.java)
            .apply {
                putExtra(DoctorDetailsActivity.DOCTOR_KEY, doctor)
            }
            .also {
                startActivity(it)
            }
    }


    companion object {
        private const val MENU_KEY = "selected"
    }
}
