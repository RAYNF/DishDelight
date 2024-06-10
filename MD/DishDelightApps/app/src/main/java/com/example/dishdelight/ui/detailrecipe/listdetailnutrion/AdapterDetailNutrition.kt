package com.example.dishdelight.ui.detailrecipe.listdetailnutrion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.ui.home.listpopular.PopularFood

class AdapterDetailNutrition(private val listCategory: ArrayList<DetailNutrition>): RecyclerView.Adapter<AdapterDetailNutrition.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterDetailNutrition.ProgramViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_nutrion_detail, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterDetailNutrition.ProgramViewHolder, position: Int) {
        val name = listCategory[position].name
        val image = listCategory[position].image

        holder.categoryImg.setImageResource(image)
        holder.categoryTxt.text = name
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ProgramViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.img_program_nutrion)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_nutrions_Detail)
    }

}