package com.adhibuchori.finalsubmissionbfaa.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adhibuchori.finalsubmissionbfaa.data.remote.response.ItemGitHubUser
import com.adhibuchori.finalsubmissionbfaa.databinding.ItemRowGitHubUserBinding
import com.bumptech.glide.Glide

class ListGitHubUserAdapter(
    private val listGitHubUser: List<ItemGitHubUser>,
    private val onClick: (ItemGitHubUser) -> Unit
) : RecyclerView.Adapter<ListGitHubUserAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemRowGitHubUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemRowGitHubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvItemUsername.text = listGitHubUser[holder.bindingAdapterPosition].login
        holder.binding.tvItemType.text = listGitHubUser[holder.bindingAdapterPosition].type
        Glide.with(holder.itemView)
            .load(listGitHubUser[holder.bindingAdapterPosition].avatarUrl)
            .into(holder.binding.imgItemGitHubUser)

        holder.itemView.setOnClickListener {
            onClick(listGitHubUser[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listGitHubUser.size
}