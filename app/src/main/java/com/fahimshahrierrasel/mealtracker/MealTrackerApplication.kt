package com.fahimshahrierrasel.mealtracker

import android.app.Application
import io.realm.Realm
/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */
class MealTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}

