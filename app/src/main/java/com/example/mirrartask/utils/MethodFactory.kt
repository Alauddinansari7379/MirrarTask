package com.example.mirrartask.utils

import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.example.mirrartask.R

     fun myToast(activity: Activity, message: String) {
        val layout = activity.layoutInflater.inflate(
            R.layout.my_toast,
            activity.findViewById(R.id.toast_container)
        )
        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message
        val myToast = Toast(activity)
        myToast.duration = Toast.LENGTH_SHORT
        myToast.setGravity(Gravity.BOTTOM, 0, 40)
        myToast.view = layout //setting the view of custom toast layout
        myToast.show()
    }

