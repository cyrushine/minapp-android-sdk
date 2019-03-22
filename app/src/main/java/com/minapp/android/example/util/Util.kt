package com.minapp.android.example.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

object Util {

    fun checkPermission(ctx: Context, permissions: Array<String>): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(ctx, it) == PackageManager.PERMISSION_DENIED)
                return false
        }
        return true
    }

    fun requestPermission(activity: Activity, code: Int, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, code);
    }

    fun permissionGranted(results: IntArray): Boolean {
        results.forEach {
            if (it == PackageManager.PERMISSION_DENIED)
                return false
        }
        return true
    }

    fun toastSuccess(ctx: Context) {
        Toast.makeText(ctx, "操作成功", Toast.LENGTH_SHORT).show()
    }

    fun toastFailure(ctx: Context) {
        Toast.makeText(ctx, "操作失败", Toast.LENGTH_SHORT).show()
    }

    fun hideSoftInput(activity: Activity) {
        (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }

}