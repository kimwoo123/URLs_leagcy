package com.keelim.free.ui.main.personal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.keelim.data.model.Folder
import com.keelim.free.R
import com.keelim.free.databinding.ItemFolderBinding


class PersonalAdapter(
    private val click: (Folder) -> Unit,
    private val longClick: (Folder) -> Unit,
) : ListAdapter<Folder, PersonalAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemFolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Folder) = with(binding) {
            accept.setOnClickListener {
                click(item)
            }
            root.setOnLongClickListener {
                longClick(item)
                return@setOnLongClickListener true
            }
            root.setOnClickListener {
                click(item)
            }
            if (item.shared) {
                permission.text = "공유된 폴더 입니다."
            } else {
                permission.text = "개인 폴더 입니다."
            }
            description.text = item.folder_name
            if(item.shared){
                permission.text =  "팀 공유 폴더"
                ivPermission.load(R.drawable.ic_baseline_groups_24)
            } else{
                permission.text = "개인 폴더"
                ivPermission.load(R.drawable.ic_baseline_emoji_people_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFolderBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<Folder>() {
            override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
                return oldItem == newItem
            }
        }
    }
}