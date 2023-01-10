package com.htb.realmdatabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var btnInsert: Button
    private lateinit var btnGet: Button
    private lateinit var edTitle: AppCompatEditText
    private lateinit var edDes: AppCompatEditText
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewModel: MainViewModel
    private var articleAdapter: ArticleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        initView()
        initRecyclerView()

        viewModel.allArticles.observe(this) { articleAdapter?.submitList(it) }

        btnInsert.setOnClickListener { addArticle() }
        btnGet.setOnClickListener { getAllArticle() }
    }

    private fun addArticle() {
        val title = edTitle.text.toString()
        val des = edDes.text.toString()
        viewModel.addArticle(title, des)
    }

    private fun getAllArticle() {
        viewModel.getAllArticle()
    }

    private fun showDialogUpdate(article: ArticleModel) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_input, null)
        val edTitle = dialogView.findViewById<TextInputEditText>(R.id.ed_title)
        val edDes = dialogView.findViewById<TextInputEditText>(R.id.ed_des)
        val btnUpdate = dialogView.findViewById<Button>(R.id.btn_update)

        //Set value to EditText
        edTitle.setText(article.title.toString())
        edDes.setText(article.description.toString())

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        builder.setTitle(null)
        builder.setMessage(null)

        val alertDialog = builder.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            val id = article.id.toString()
            val title = article.title.toString()
            val des = article.description.toString()
            viewModel.updateArticle(id, title, des)
            Toast.makeText(this, "Article Updated...", Toast.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }
    }

    private fun showAlertDeleteItem(article: ArticleModel) {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.ic_delete)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure to delete item?")
        builder.setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }
        builder.setPositiveButton("YES") { dialog, _ ->
            viewModel.deleteArticle(article.id.toString())
            viewModel.getAllArticle()
            Toast.makeText(this, "Article is deleted...", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter()
        recyclerView.adapter = articleAdapter
        articleAdapter?.setOnClickItem(object : OnActionClick {
            override fun onClickDelete(view: View, model: ArticleModel) {
                showAlertDeleteItem(model)
            }

            override fun onClickUpdate(view: View, model: ArticleModel) {
                showDialogUpdate(model)
            }
        })
    }

    private fun initView() {
        btnGet = findViewById(R.id.btn_get)
        btnInsert = findViewById(R.id.btn_add)
        edTitle = findViewById(R.id.ed_title)
        edDes = findViewById(R.id.ed_des)
        recyclerView = findViewById(R.id.rv_article)
    }
}