package com.htb.realmdatabase

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {
    var configuration: RealmConfiguration? = null

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        configuration = RealmConfiguration.Builder()
            .name("article.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true).build()

        configuration?.let { Realm.setDefaultConfiguration(it) }
    }
}