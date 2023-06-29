package com.adhibuchori.initialsubmissionbfaa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adhibuchori.initialsubmissionbfaa.databinding.ItemRowGitHubUserBinding
import com.adhibuchori.initialsubmissionbfaa.response.ItemsItem
import com.bumptech.glide.Glide

class ListGitHubUserAdapter(
    private val listGitHubUser: List<ItemsItem>,
    private val onClick: (ItemsItem) -> Unit
) : RecyclerView.Adapter<ListGitHubUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        ItemRowGitHubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(listGitHubUser[holder.adapterPosition].avatarUrl)
                .into(imgItemGitHubUser)
            tvItemUsername.text = listGitHubUser[holder.adapterPosition].login
            tvItemType.text = listGitHubUser[holder.adapterPosition].type
        }

        holder.itemView.setOnClickListener {
            onClick(listGitHubUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listGitHubUser.size

    class ListViewHolder(var binding: ItemRowGitHubUserBinding) :
        RecyclerView.ViewHolder(binding.root)
}