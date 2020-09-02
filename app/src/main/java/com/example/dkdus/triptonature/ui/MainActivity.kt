package com.example.dkdus.triptonature.ui

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dkdus.triptonature.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var homeFragment : HomeFragment? = null
    var pickFragment : PickFragment? = null
    var settingFragment : SettingFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, homeFragment!!)
            .commitAllowingStateLoss()

        nav_view.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when(p0.itemId){
            R.id.navigation_home ->{
                if(homeFragment == null){
                    homeFragment = HomeFragment()
                    transaction.add(R.id.frameLayout, homeFragment!!)
                }
                if(homeFragment != null)
                    transaction.show(homeFragment!!)
                if(pickFragment != null)
                    transaction.hide(pickFragment!!)
                if(settingFragment != null)
                    transaction.hide(settingFragment!!)
            }
            R.id.navigation_pick -> {
                if(pickFragment == null){
                    pickFragment = PickFragment()
                    transaction.add(R.id.frameLayout,pickFragment!!)
                }
                if(homeFragment != null)
                    transaction.hide(homeFragment!!)
                if(pickFragment != null)
                    transaction.show(pickFragment!!)
                if(settingFragment != null)
                    transaction.hide(settingFragment!!)
            }
            R.id.navigation_setting -> {
                if(settingFragment == null){
                    settingFragment = SettingFragment()
                    transaction.add(R.id.frameLayout,settingFragment!!)
                }
                if(homeFragment != null)
                     transaction.hide(homeFragment!!)
                if(pickFragment != null)
                    transaction.hide(pickFragment!!)
                if(settingFragment != null)
                    transaction.show(settingFragment!!)
            }
        }
        transaction.commit()
        return true
    }
}