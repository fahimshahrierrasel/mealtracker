package com.fahimshahrierrasel.mealtracker.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */
open class Customer : RealmObject() {
    @PrimaryKey
    var id: String? = null
    var name: String = ""
    var floorNo: String = ""
    var bedNo: String = ""
    var mobileNo: String = ""
    var permitNo: String = ""
    var photo: String = ""
    var payments: RealmList<Payment>? = null
}
