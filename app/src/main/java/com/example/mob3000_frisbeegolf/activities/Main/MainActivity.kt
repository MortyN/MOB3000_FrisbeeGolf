package com.example.mob3000_frisbeegolf.activities.Main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.mob3000_frisbeegolf.*
import com.example.mob3000_frisbeegolf.activities.Feed.Feed
import com.example.mob3000_frisbeegolf.activities.AddRound.view.AddRound
import com.example.mob3000_frisbeegolf.activities.MyProfile.MyProfile
import com.example.mob3000_frisbeegolf.activities.MyRounds.MyRounds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //lateinit will allow to skip the otherwise required initializer and will make the property access fail with exception until some meaningful value is assigned to it https://stackoverflow.com/questions/33849811/property-must-be-initialized-or-be-abstract
    private lateinit var drawer: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private var prevFragment: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //disable night mode color theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Feed())
                .commit()
            navigationView.setCheckedItem(R.id.nav_myprofile)
            navigationView.setCheckedItem(R.id.nav_myprofile)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setOnItemSelectedListener(navListener)
    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item -> // By using switch we can easily get
            // the selected fragment
            // by using there id.
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.feed_navbtn -> selectedFragment = Feed()
                R.id.myrounds_navbtn -> selectedFragment = MyRounds()
                R.id.addround_navbtn -> selectedFragment = AddRound()
                R.id.myprofile_navbtn -> selectedFragment = MyProfile()
            }
            // It will help to replace the
            // one fragment to other.
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment!!)
                .commit()
            true
        }


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            return
        }

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            supportFragmentManager.popBackStack();
            return
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.nav_myprofile -> supportFragmentManager.beginTransaction().replace(
//                R.id.fragment_container,
//                MyProfile()
//            ).addToBackStack(null).commit()
//            R.id.nav_friends -> supportFragmentManager.beginTransaction().replace(
//                R.id.fragment_container,
//                MyRounds()
//            ).addToBackStack(null).commit()
//            R.id.nav_arenarecords -> supportFragmentManager.beginTransaction().replace(
//                R.id.fragment_container,
//                AddRound()
//            ).addToBackStack(null).commit()
//            R.id.feed_navbtn -> supportFragmentManager.beginTransaction().replace(
//                R.id.fragment_container,
//                MyProfile()
//            ).addToBackStack(null).commit()
//        }
        // the selected fragment
        // by using there id.
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.nav_myprofile -> selectedFragment = Feed()
            R.id.nav_friends -> selectedFragment = MyRounds()
            R.id.nav_arenarecords -> selectedFragment = AddRound()
            R.id.nav_addarena -> selectedFragment = MyProfile()
            R.id.nav_yourarenas -> selectedFragment = MyProfile()
            R.id.nav_settings -> selectedFragment = MyProfile()
        }
        // It will help to replace the
        // one fragment to other.
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, selectedFragment!!)
            .commit()
        true
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}


