package com.keelim.free.ui.main.dash.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keelim.data.model.dash.More
import com.keelim.free.R
import com.keelim.free.databinding.ItemMoreBinding

class MoreAdapter(
    private val longClick: (More) -> Unit,
) : ListAdapter<More, MoreAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: ItemMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: More) = with(binding) {

            title.text = item.ranking_title
            number.text = item.rank.toString()
            val random = arrayOf(
                R.color.bg_orange, R.color.orange, R.color.orange_w,
            ).random()
            val color = itemView.context.resources.getColor(random)
            container.setBackgroundColor(color)

            root.setOnLongClickListener {
                longClick.invoke(item)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<More>() {
            override fun areItemsTheSame(oldItem: More, newItem: More): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: More, newItem: More): Boolean {
                return oldItem == newItem
            }
        }
    }
}