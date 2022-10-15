package com.example.fitbit1

import android.app.Application

class FoodItemApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}