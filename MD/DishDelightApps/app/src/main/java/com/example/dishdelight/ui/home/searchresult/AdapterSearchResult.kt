package com.example.dishdelight.ui.home.searchresult

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.databinding.ItemSearchResultBinding

class AdapterSearchResult(private val searchResults: List<SearchResult>) :
    RecyclerView.Adapter<AdapterSearchResult.ViewHolder>() {

    class ViewHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchResult = searchResults[position]
        holder.binding.imageViewFood.setImageResource(searchResult.imageResId)
        holder.binding.textViewFoodName.text = searchResult.name
        holder.binding.textViewFoodLocation.text = searchResult.location
        holder.binding.textViewFoodLikes.text = searchResult.likes
    }

    override fun getItemCount(): Int = searchResults.size
}