package com.example.dishdelight.ui.home.listcategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.ui.home.listprogram.FoodProgram

//masih error
class AdapterCategory(private val listCategory: ArrayList<FoodCategory>): RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterCategory.CategoryViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterCategory.CategoryViewHolder, position: Int) {
        val name = listCategory[position].name
        holder.categoryTxt.text = name.toString()
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_category)
    }

}