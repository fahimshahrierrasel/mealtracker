package com.fahimshahrierrasel.mealtracker.models

import io.realm.RealmObject
import java.util.*

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */
open class Meal(var date: Date = Date(), var type: String = "",
                var price: Double = 0.0): RealmObject()