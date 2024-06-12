package com.example.dishdelight.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.data.dataclass.DataClassCategorizeNutrionValueActivityResult

class AdapterCategorizeNutrionValueActivityResult(private val listNutrion: ArrayList<DataClassCategorizeNutrionValueActivityResult>): RecyclerView.Adapter<AdapterCategorizeNutrionValueActivityResult.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
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