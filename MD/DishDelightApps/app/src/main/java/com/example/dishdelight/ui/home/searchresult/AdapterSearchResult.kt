package com.example.dishdelight.ui.home.searchresult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.ui.home.listpopular.PopularFood

class AdapterSearchResult(private val listCategory: ArrayList<PopularFood>): RecyclerView.Adapter<AdapterSearchResult.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterSearchResult.ProgramViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_food, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterSearchResult.ProgramViewHolder, position: Int) {
        val name = listCategory[position].name
        val image = listCategory[position].image
        val description = listCategory[position].dataDescription

        holder.categoryImg.setImageResource(image)
        holder.categoryTxt.text = name
        holder.descriptionTxt.text = description
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ProgramViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.img_program)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_name)
        val descriptionTxt: TextView = itemView.findViewById(R.id.tv_description)
    }

}