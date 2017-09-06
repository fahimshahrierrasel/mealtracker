package com.fahimshahrierrasel.mealtracker.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.fahimshahrierrasel.mealtracker.controllers.CustomerController
import com.google.firebase.storage.FirebaseStorage
import io.realm.Realm
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


/**
 * Modified version of Paolo Rotolo's Medium Post
 * https://medium.com/glucosio-project/example-class-to-export-import-a-realm-database-on-android-c429ade2b4ed
 */
/**
 * Created by fahim on 9/6/17.
 * Project: MealTracker
 */
class RealmBackupRestore(private val appContext: Context, private val firebaseStorage: FirebaseStorage) {

    private val EXPORT_REALM_PATH = File(appContext.filesDir, "/backup")
    private val EXPORT_REALM_FILE_NAME = "mealtracker.realm"
    private val IMPORT_REALM_FILE_NAME = "default.realm" // Eventually replace this if you're using a custom db name
    private val realm: Realm = CustomerController().getRealmIstance()

    fun backup() {
        val exportRealmFile: File

        Log.d(TAG, "Realm DB Path = " + realm.getPath())

        try {
            EXPORT_REALM_PATH.mkdirs()
            exportRealmFile = File(EXPORT_REALM_PATH, EXPORT_REALM_FILE_NAME)
            exportRealmFile.delete()
            realm.writeCopyTo(exportRealmFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val msg = "File exported to Path: $EXPORT_REALM_PATH/$EXPORT_REALM_FILE_NAME"
        Log.d(TAG, msg)
        realm.close()
    }

    fun createBackupDir(){
        try {
            EXPORT_REALM_PATH.mkdirs()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun restore() {
        val restoreFilePath = "$EXPORT_REALM_PATH/$EXPORT_REALM_FILE_NAME"
        Log.d(TAG, "oldFilePath = " + restoreFilePath)
        copyBundledRealmFile(restoreFilePath, IMPORT_REALM_FILE_NAME)
        Log.d(TAG, "Data restore is done")
    }

    fun uploadToFirebase(){

        val file = Uri.fromFile(File("$EXPORT_REALM_PATH/$EXPORT_REALM_FILE_NAME"))
        val riversRef = firebaseStorage.getReference("backup/").child(EXPORT_REALM_FILE_NAME)

        riversRef.putFile(file).addOnSuccessListener({ _ ->
            // Get a URL to the uploaded content
        }).addOnFailureListener({
            // Handle unsuccessful uploads
        })
    }

    fun downloadFromFirebase(){
        val islandRef = firebaseStorage.getReference("backup/").child(EXPORT_REALM_FILE_NAME)
        val downloadDir = File(EXPORT_REALM_PATH, EXPORT_REALM_FILE_NAME)

        createBackupDir()
        islandRef.getFile(downloadDir).addOnSuccessListener({
            // Handle Success
        }).addOnFailureListener({
            // Handle any errors
        })
    }

    private fun copyBundledRealmFile(oldFilePath: String, outFileName: String): String? {
        try {
            val file = File(appContext.filesDir, outFileName)

            val outputStream = FileOutputStream(file)
            val inputStream = FileInputStream(File(oldFilePath))

            val buf = ByteArray(1024)
            var bytesRead= inputStream.read(buf)
            while (bytesRead > 0) {
                outputStream.write(buf, 0, bytesRead)
                bytesRead  = inputStream.read(buf)
            }
            outputStream.close()
            return file.getAbsolutePath()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun dbPath(): String {
        return realm.getPath()
    }

    companion object {
        private val TAG = RealmBackupRestore::class.java.name
    }
}