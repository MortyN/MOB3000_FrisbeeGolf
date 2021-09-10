package com.example.mob3000_frisbeegolf

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //lateinit will allow to skip the otherwise required initializer and will make the property access fail with exception until some meaningful value is assigned to it https://stackoverflow.com/questions/33849811/property-must-be-initialized-or-be-abstract
    private lateinit var drawer: DrawerLayout
    private var prevFragment: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
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

        if(savedInstanceState == null ){

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Fragment1()).commit()
        navigationView.setCheckedItem(R.id.nav_message)
        navigationView.setCheckedItem(R.id.nav_message)
        }
    }


    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if(drawer.isDrawerOpen(GravityCompat.START)){
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
        when (item.itemId){
            R.id.nav_message -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                Fragment1()
            ).addToBackStack(null).commit()
            R.id.nav_chat -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                Fragment2()
            ).addToBackStack(null).commit()
            R.id.nav_profile -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                Fragment3()
            ).addToBackStack(null).commit()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}