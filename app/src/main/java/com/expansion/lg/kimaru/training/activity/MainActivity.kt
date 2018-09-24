package com.expansion.lg.kimaru.training.activity

import android.os.Handler
import android.preference.PreferenceFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.fragments.HomeFragment
import com.expansion.lg.kimaru.training.fragments.SettingsFragment
import com.expansion.lg.kimaru.training.fragments.SetttingFragment
import com.expansion.lg.kimaru.training.fragments.TrainingsFragment
import com.expansion.lg.kimaru.training.utils.CircleTransform
import com.expansion.lg.kimaru.training.utils.SessionManagement

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal var llStats: LinearLayout? = null
    internal var txtPlayCount: TextView? = null
    internal var txtEarned: TextView? = null
    internal var drawer: DrawerLayout
    var navigationView: NavigationView
    private var imgNavHeaderBg: ImageView? = null
    private var imgProfile: ImageView? = null
    private var txtName: TextView? = null
    private var txtWebsite: TextView? = null
    var toolbar: Toolbar
    private var navHeader: View? = null
    internal var sessionManagement: SessionManagement
    private var mHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navHeader = navigationView.getHeaderView(0)
        txtName = navHeader!!.findViewById<View>(R.id.name) as TextView
        txtWebsite = navHeader!!.findViewById<View>(R.id.website) as TextView
        imgNavHeaderBg = navHeader!!.findViewById<View>(R.id.img_header_bg) as ImageView
        imgProfile = navHeader!!.findViewById<View>(R.id.img_profile) as ImageView
        sessionManagement = SessionManagement(baseContext)
        mHandler = Handler()
        loadNavHeader()

        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        //drawer.addDrawerListener(toggle);
        toggle.syncState()

        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        //load the Fragment

        val mPendingRunnable = Runnable {
            val fragment = HomeFragment()
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }
        if (mPendingRunnable != null) {
            mHandler!!.post(mPendingRunnable)
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            return
        }
        val mPendingRunnable: Runnable?
        if (backFragment != null) {
            mPendingRunnable = Runnable {
                // update the main content by replacing fragments
                val fragment = backFragment
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out)
                fragmentTransaction.replace(R.id.frame, fragment, "")
                fragmentTransaction.commitAllowingStateLoss()
            }

            // If mPendingRunnable is not null, then add to the message queue
            if (mPendingRunnable != null) {
                mHandler!!.post(mPendingRunnable)
            }
        } else {
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val fragment: Fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {
            val settings = SettingsFragment()
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, settings)
            fragmentTransaction.commitAllowingStateLoss()
            //            getSupportFragmentManager().beginTransaction()
            //                    .replace(R.id.frame, new SetttingFragment())
            //                    .commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private fun loadNavHeader() {
        // name, website
        txtName!!.text = sessionManagement.userDetails[SessionManagement.KEY_NAME]
        txtWebsite!!.text = sessionManagement.userDetails[SessionManagement.KEY_EMAIL]
        Glide.with(this).load(getImage("nav_menu_header_bg"))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg!!)


        //imgNavHeaderBg.setImageResource(R.drawable.nav_menu_header_bg);

        Glide.with(this).load(getImage("lg_bg"))
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile!!)
        //imgProfile.setImageResource(R.drawable.lg_bg);

        // showing dot next to notifications label
        navigationView.menu.getItem(3).setActionView(R.layout.menu_dot)
    }

    fun getImage(imageName: String): Int {
        return resources.getIdentifier(imageName, "drawable", packageName)
    }

    companion object {
        var backFragment: Fragment? = null
    }
}
