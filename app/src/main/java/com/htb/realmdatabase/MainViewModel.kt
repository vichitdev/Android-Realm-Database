package com.htb.realmdatabase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm
import io.realm.kotlin.where
import java.util.*

class MainViewModel : ViewModel() {
    private var realm: Realm = Realm.getDefaultInstance()

    val allArticles by lazy { MutableLiveData<List<ArticleModel>>() }

    fun addArticle(title: String, des: String) {
        realm.executeTransaction {
            val article = it.createObject(ArticleModel::class.java, UUID.randomUUID().toString())
            article.title = title
            article.description = des
            realm.insertOrUpdate(article)
        }
    }

    fun getAllArticle() {
        val articles = realm.where(ArticleModel::class.java).findAll()
        allArticles.value = realm.copyFromRealm(articles)
    }

    fun deleteArticle(id: String) {
        val article = realm.where(ArticleModel::class.java).equalTo("id", id).findFirst()
        realm.executeTransaction {
            article?.deleteFromRealm()
        }
    }

    fun updateArticle(id: String, title: String, des: String) {
        val article = realm.where(ArticleModel::class.java).equalTo("id", id).findFirst()

        realm.executeTransaction {
            article?.title = title
            article?.description = des
            article?.let { it1 -> realm.insertOrUpdate(it1) }
        }
    }
}