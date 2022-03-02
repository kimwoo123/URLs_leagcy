package com.keelim.free.ui.main.dash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keelim.data.model.Recommend
import com.keelim.free.databinding.ItemRecommendBinding

class RecommendAdapter(
    private val click : (String) -> Unit
) : ListAdapter<Recommend, RecommendAdapter.ViewHolder>(diffUtil) {


    inner class ViewHolder(private val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recommend, position: Int) = with(binding) {
            index.text = (position).toString()

            url.text = if (item.title == null || item.title!!.length <= 3) {
                item.url
            } else {
                item.title
            }
            click(item.url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecommendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Recommend>() {
            override fun areItemsTheSame(oldItem: Recommend, newItem: Recommend): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Recommend, newItem: Recommend): Boolean {
                return oldItem == newItem
            }
        }
    }
}