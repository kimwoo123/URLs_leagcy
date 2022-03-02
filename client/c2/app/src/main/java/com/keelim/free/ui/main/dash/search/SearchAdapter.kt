/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.keelim.free.ui.main.dash.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.keelim.data.model.Topic
import com.keelim.free.databinding.ItemSearchBinding

class SearchAdapter : ListAdapter<Topic, SearchAdapter.SearchViewHolder>(diffUtil) {
    inner class SearchViewHolder(
        private val binding: ItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: Topic) {
            binding.run {
                this.topic = topic
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        object diffUtil : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Topic, newItem: Topic) = oldItem == newItem
        }
    }
}

