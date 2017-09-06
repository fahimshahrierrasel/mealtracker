package com.fahimshahrierrasel.mealtracker.controllers

import com.fahimshahrierrasel.mealtracker.models.AllPayment
import com.fahimshahrierrasel.mealtracker.models.Customer
import com.fahimshahrierrasel.mealtracker.models.Payment
import com.fahimshahrierrasel.mealtracker.models.UserPayment
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */
open class CustomerController() {

    private var realm: Realm by Delegates.notNull()

    val realmConfiguration: RealmConfiguration?
        get() {
            return RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        }

    init {
        realm = Realm.getInstance(realmConfiguration)
    }

    fun registerCustomer(name: String, floorNo: String, bedNo: String,
                         mobileNo: String, permitN0: String) {

        realm.beginTransaction()

        val id = UUID.randomUUID().toString()
        val customer = realm.createObject(Customer::class.java, id)
        customer.name = name
        customer.floorNo = floorNo
        customer.bedNo = bedNo
        customer.mobileNo = mobileNo
        customer.permitNo = permitN0
        realm.commitTransaction()
    }

    fun getAllCustomer(): RealmResults<Customer>? {
        val query = realm.where(Customer::class.java)
        return query.findAll()
    }

    fun searchCustomerBy(option: String, withKey: String): RealmResults<Customer>? {
        val query = realm.where(Customer::class.java).contains(option, withKey)
        return query.findAll()
    }

    fun getCustomerBy(id: String): Customer {
        val query = realm.where(Customer::class.java).equalTo("id", id)
        return query.findFirst()
    }

    fun addCustomerPayment(id: String, date: String, payment: String) {
        val query = realm.where(Customer::class.java).equalTo("id", id)

        realm.beginTransaction()

        val customer = query.findFirst()
        customer.payments!!.add(Payment(date, payment.toDouble()))

        realm.copyToRealmOrUpdate(customer)
        realm.commitTransaction()
    }

    fun addCustomerImage(id: String, image: String) {
        val query = realm.where(Customer::class.java).equalTo("id", id)

        realm.beginTransaction()

        val customer = query.findFirst()
        customer.photo = image

        realm.copyToRealmOrUpdate(customer)
        realm.commitTransaction()
    }

    fun getAllPaymentBy(date: String): AllPayment? {

        val allUserPayment = ArrayList<UserPayment>()
        var totalAmount: Double = 0.0

        val query = realm.where(Customer::class.java)
        val allCustomer = query.findAll()
        for (aCustomer in allCustomer) {
            if (aCustomer.payments != null || aCustomer.payments!!.size > 0) {
                for (aPayment in aCustomer.payments!!) {
                    if (aPayment!!.date == date) {
                        allUserPayment.add(UserPayment(aCustomer.id!!, aCustomer.name, aPayment.date, aPayment.amount))
                        totalAmount += aPayment.amount
                    }
                }
            }
        }
        return AllPayment(allUserPayment, totalAmount)
    }

    fun getRealmIstance(): Realm {
        return realm
    }

    fun refreshRealm() {
        realm.refresh()
    }


}