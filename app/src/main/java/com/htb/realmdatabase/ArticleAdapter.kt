package com.htb.realmdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter : ListAdapter<ArticleModel, ArticleViewHolder>(MyDiffUtil) {
    private var onClick: OnActionClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bindingView(article, onClick)
    }

    fun setOnClickItem(onClick: OnActionClick) {
        this.onClick = onClick
    }

    object MyDiffUtil : DiffUtil.ItemCallback<ArticleModel>() {
        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

class ArticleViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDes: AppCompatTextView
    private lateinit var ivDelete: AppCompatImageView
    private lateinit var ivEdit: AppCompatImageView

    fun bindingView(model: ArticleModel, onClick: OnActionClick?) {
        tvTitle = view.findViewById(R.id.tv_title)
        tvDes = view.findViewById(R.id.tv_des)
        ivDelete = view.findViewById(R.id.iv_delete)
        ivEdit = view.findViewById(R.id.iv_edit)

        tvTitle.text = model.title
        tvDes.text = model.description

        ivDelete.setOnClickListener { onClick?.onClickDelete(it, model) }
        ivEdit.setOnClickListener { onClick?.onClickUpdate(it, model) }
    }

    companion object {
        const val LAYOUT = R.layout.layout_article_viewholder
        fun create(parent: ViewGroup) = ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(LAYOUT, parent, false)
        )
    }
}

interface OnActionClick {
    fun onClickDelete(view: View, model: ArticleModel) {}
    fun onClickUpdate(view: View, model: ArticleModel) {}
}
