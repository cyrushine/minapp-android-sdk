package com.minapp.android.example

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.minapp.android.example.model.SimpleModel
import com.minapp.android.example.util.Glide4Engine
import com.minapp.android.example.util.Util
import com.minapp.android.sdk.database.*
import com.minapp.android.sdk.file.Storage
import com.minapp.android.sdk.util.DateUtil
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            /*if (Util.checkPermission(this, IMAGE_PICKER_PERMISSIONS))
                openImagePicker()
            else
                Util.requestPermission(this, OPEN_IMAGE_PICKER, IMAGE_PICKER_PERMISSIONS)*/

            GlobalScope.launch { runCatching {

                val table = TableObject(66683)
                val record = table.createRecord()
                    .put("horse_name", "扫马")
                    .putStringArray("children", listOf("一", "二", "三", "四", "五", "六", "七"))
                    .save()
                table.fetchRecord(record.id()).let { it.getStringArray("children") }.also {
                    Log.d(Const.TAG, it?.toString())
                }
                null
            }.onFailure { Log.e(Const.TAG, it.message, it) } }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    fun openImagePicker() {
        Matisse.from(this)
            .choose(IMAGE_PICKER_TYPES)
            .maxSelectable(1)
            .imageEngine(Glide4Engine())
            .forResult(IMAGE_PICKER_RESULT)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            OPEN_IMAGE_PICKER -> {
                if (Util.permissionGranted(grantResults)) {
                    openImagePicker()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            IMAGE_PICKER_RESULT -> {
                if (resultCode == RESULT_OK) {
                    GlobalScope.launch {
                        runCatching {

                            val file = File(Matisse.obtainPathResult(data)[0])
                            val uploaded = Storage.uploadFile(file.name, file.readBytes())
                            val horses = TableObject(66683)
                            val qrCode = horses.createRecord().put("horse_name", "二维马").put("attachment", uploaded).save()
                            Log.d(Const.TAG, "${qrCode.getFile("attachment")?.path}")

                        }.onFailure { Log.d(Const.TAG, it.message, it) }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        val TAG = "MainActivity"
        const val OPEN_IMAGE_PICKER = 9
        const val IMAGE_PICKER_RESULT = 10
        val IMAGE_PICKER_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val IMAGE_PICKER_TYPES = setOf(MimeType.JPEG, MimeType.PNG, MimeType.GIF)
    }
}
