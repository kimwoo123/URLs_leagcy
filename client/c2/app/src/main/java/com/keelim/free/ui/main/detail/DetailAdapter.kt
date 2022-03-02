package com.keelim.free.ui.main.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.chip.Chip
import com.keelim.data.model.open.Url
import com.keelim.free.R
import com.keelim.free.databinding.ItemDetailBinding

class DetailAdapter(
    val click_move: (Url) -> Unit,
    val click_memo: (String) -> Unit,
    val ctx: Context
) : ListAdapter<Url, DetailAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        private val colors = arrayOf(
            R.color.bg_orange,
            R.color.orange,
            R.color.orange_w,
        )

        fun bind(item: Url) = with(binding) {
            header.text = item.url
            if(item.thumbnail.isEmpty()){
                thumbnail.visibility = View.GONE
            } else{
                thumbnail.load(item.thumbnail)
            }
            item.tags.map {
                tags.addView(
                    Chip(ctx).apply {
                        text = it
                        setTextAppearanceResource(R.style.ChipTextStyle_Selected)
                        setChipBackgroundColorResource(colors.random())
                    }
                )
            }
            memo.setOnClickListener {
                click_memo(item.memos_id)
            }
            root.setOnClickListener{
                click_move(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Url>() {
            override fun areItemsTheSame(oldItem: Url, newItem: Url): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Url, newItem: Url): Boolean {
                return oldItem == newItem
            }
        }
    }
}