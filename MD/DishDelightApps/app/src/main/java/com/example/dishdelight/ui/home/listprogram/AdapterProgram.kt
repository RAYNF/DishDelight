package com.example.dishdelight.ui.home.listprogram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R

class AdapterProgram(private val listCategory: ArrayList<FoodProgram>): RecyclerView.Adapter<AdapterProgram.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterProgram.ProgramViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_program, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterProgram.ProgramViewHolder, position: Int) {
        val (name, img) = listCategory[position]
        holder.categoryImg.setImageResource(img)
        holder.categoryTxt.text = name
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ProgramViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.img_program)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_program)
    }

}