package com.fahimshahrierrasel.mealtracker.controllers

import com.fahimshahrierrasel.mealtracker.models.Customer
import com.fahimshahrierrasel.mealtracker.models.Payment
import io.realm.Realm
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by fahim on 8/29/17.
 * Project: MealTracker
 */
open class CustomerController() {

    private var realm: Realm by Delegates.notNull()

    init {
        realm = Realm.getDefaultInstance()
    }

    fun registerCustomer(name: String, floorNo: String, bedNo: String,
                         mobileNo: String, permitN0: String){

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

    fun getCustomerBy(id: String): Customer{
        val query = realm.where(Customer::class.java).equalTo("id", id)
        return query.findFirst()
    }

    fun addCustomerPayment(id: String, date: String, payment: String){
        val query = realm.where(Customer::class.java).equalTo("id", id)

        realm.beginTransaction()


        val paymentDate = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(date)
        val customer = query.findFirst()
        customer.payments!!.add(Payment(paymentDate, payment.toDouble()))

        realm.copyToRealmOrUpdate(customer)
        realm.commitTransaction()
    }
}