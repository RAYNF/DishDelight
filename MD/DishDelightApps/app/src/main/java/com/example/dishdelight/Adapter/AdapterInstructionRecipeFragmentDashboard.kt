package com.example.dishdelight.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.data.dataclass.DataClassRecipeIntructionFragmentDashboard

class AdapterInstructionRecipeFragmentDashboard(private val listCategory: ArrayList<DataClassRecipeIntructionFragmentDashboard>) :
    RecyclerView.Adapter<AdapterInstructionRecipeFragmentDashboard.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_intruction, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val name = listCategory[position].name
        val step = listCategory[position].step

        holder.categoryTxt.text = name
        holder.categoryStep.text = step
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryStep: TextView = itemView.findViewById(R.id.tv_urutan)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_intruksi)
    }

}