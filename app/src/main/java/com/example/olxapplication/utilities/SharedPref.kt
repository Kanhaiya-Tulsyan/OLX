package com.example.olxapplication.utilities

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
     var sharedPref : SharedPreferences
     init{
         sharedPref=context.getSharedPreferences(Constants.sharedPrefName,0)
     }
     fun setString(key: String, value: String)
     {
         sharedPref.edit().putString(key,value)
     }
    fun getString(key: String): String? {
        return sharedPref.getString(key,"")
    }
}