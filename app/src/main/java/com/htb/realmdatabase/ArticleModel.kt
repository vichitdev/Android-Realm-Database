package com.htb.realmdatabase

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.util.*

@RealmClass
open class ArticleModel : RealmModel {
    @PrimaryKey
    var id: String? = ""

    @Required
    var title: String? = ""

    @Required
    var description: String? = ""
}