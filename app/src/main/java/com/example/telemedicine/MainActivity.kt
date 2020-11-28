package com.example.telemedicine

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private val backStack: ArrayDeque<Int> = ArrayDeque()
    private var selectedId: Int = R.id.navigation_home
    private lateinit var fragmentTitleTextView: TextView
    private lateinit var navigationView: BottomNavigationView
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
            R.id.navigation_notifications -> return NotificationsFragment()
            R.id.navigation_schedule -> return ScheduleFragment()
            R.id.navigation_profile -> return ProfileFragment()
            R.id.doctorListFragment -> return DoctorListFragment()
            R.id.doctorDetailsFragment -> return DoctorDetailsFragment()
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
        setFragment(R.id.doctorListFragment)
        fragmentTitleTextView.text = getString(R.string.doctor_list)
    }

    fun onDoctorClick(view: View) {
        setFragment(R.id.doctorDetailsFragment)
        fragmentTitleTextView.text = getString(R.string.doctor_details)
    }

    companion object {
        private const val MENU_KEY = "selected"
    }
}
