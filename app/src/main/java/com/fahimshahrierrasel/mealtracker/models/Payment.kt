package com.fahimshahrierrasel.mealtracker.models

import io.realm.RealmObject
import java.util.*

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */
open class Payment (var date: String = "", var amount: Double = 0.0): RealmObject()