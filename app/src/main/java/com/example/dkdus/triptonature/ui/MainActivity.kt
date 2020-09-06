package com.example.dkdus.triptonature.ui

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.dkdus.triptonature.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commitAllowingStateLoss()

        nav_view.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        when(p0.itemId){
            R.id.navigation_home ->{
                val homeFragment = HomeFragment()
                transaction.replace(R.id.frameLayout, homeFragment)
            }
            R.id.navigation_pick -> {
                val pickFragment = PickFragment()
                transaction.replace(R.id.frameLayout,pickFragment)
                //              transaction.detach(pickFragment).attach(pickFragment)
            }
            R.id.navigation_setting -> {
                val settingFragment = SettingFragment()
                transaction.replace(R.id.frameLayout,settingFragment)
            }
        }
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
        return true
    }

}