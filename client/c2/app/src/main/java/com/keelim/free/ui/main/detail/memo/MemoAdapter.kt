package com.keelim.free.ui.main.detail.memo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keelim.data.model.fold.Memo
import com.keelim.free.databinding.ItemLeftBinding
import com.keelim.free.databinding.ItemRightBinding


class MemoAdapter : ListAdapter<Memo, RecyclerView.ViewHolder>(diffUtil) {
    inner class LeftViewHolder(val binding: ItemLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun leftBind(item: Memo) = with(binding) {
            chatItemLeftText.text = item.content
            date.text = "date: ${item.updatedAt}"
        }
    }

    inner class RightViewHolder(val binding: ItemRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun rightBinds(item: Memo) = with(binding) {
            chatItemRightText.text = item.content
            date.text = "date: ${item.updatedAt}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LEFT -> LeftViewHolder(ItemLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> RightViewHolder(ItemRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            LEFT -> (holder as LeftViewHolder).leftBind(currentList[position])
            else -> (holder as RightViewHolder).rightBinds(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position%2 == 0) LEFT else RIGHT
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Memo>() {
            override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem == newItem
            }
        }

        const val LEFT = 1
        const val RIGHT = 2
    }
}