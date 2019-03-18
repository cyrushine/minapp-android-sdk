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
import com.minapp.android.sdk.database.Database
import com.minapp.android.sdk.database.QueryCallback
import com.minapp.android.sdk.database.TableObject
import com.minapp.android.sdk.database.query.Result
import com.minapp.android.sdk.file.CloudFile
import com.minapp.android.sdk.file.Storage
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.nio.file.Files

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            if (Util.checkPermission(this, IMAGE_PICKER_PERMISSIONS))
                openImagePicker()
            else
                Util.requestPermission(this, OPEN_IMAGE_PICKER, IMAGE_PICKER_PERMISSIONS)
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
                            Log.d(Const.TAG, "upload success : ${uploaded.id}")

                            val data = Storage.file(uploaded.id);
                            Log.d(Const.TAG, "file ${data.id} upload success : ${data.isUploadSuccess}")

                            Storage.deleteFiles(listOf(data.id));
                            Log.d(Const.TAG, "delete file ${data.id} success")

                            val page = Storage.files(null, 1000, null);
                            Log.d(Const.TAG, "uploaded ${page.totalCount}")

                            Storage.deleteFiles(page.objects.map { it.id })
                            Log.d(Const.TAG, "delete all files success")
                            Log.d(Const.TAG, "file size ${Storage.files(null, null, null).totalCount}")


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
