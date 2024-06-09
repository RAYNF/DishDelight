package com.example.dishdelight.ui.result.listnutrion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.ui.home.listcategory.FoodCategory

class AdapterNutrion(private val listNutrion: ArrayList<FoodNutrion>): RecyclerView.Adapter<AdapterNutrion.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterNutrion.CategoryViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterNutrion.CategoryViewHolder, position: Int) {
        val name = listNutrion[position].name
        holder.categoryTxt.text = name.toString()
    }

    override fun getItemCount(): Int {
        return listNutrion.size
    }

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_category)
    }

}