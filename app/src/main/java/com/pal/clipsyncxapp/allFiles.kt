package com.pal.clipsyncxapp

import android.content.Context
import android.widget.Toast

object allFiles {
    fun showToast(context : Context, message : String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}