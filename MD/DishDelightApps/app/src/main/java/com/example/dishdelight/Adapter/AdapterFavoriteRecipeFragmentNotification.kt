package com.example.dishdelight.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.data.dataclass.DataClassFavoriteRecipeFragmentNotification

class AdapterFavoriteRecipeFragmentNotification(private val listCategory: ArrayList<DataClassFavoriteRecipeFragmentNotification>): RecyclerView.Adapter<AdapterFavoriteRecipeFragmentNotification.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_favorit, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val name = listCategory[position].name
        val image = listCategory[position].image
        val description = listCategory[position].dataDescription

        holder.categoryImg.setImageResource(image)
        holder.categoryTxt.text = name
        holder.btnFav.setColorFilter(Color.RED)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ProgramViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.img_highlight_recipe)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_name)
        val btnFav: ImageButton = itemView.findViewById(R.id.btn_fav_saved)
    }

}