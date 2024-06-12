package com.example.dishdelight.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R
import com.example.dishdelight.data.dataclass.DataClassGrafikNutrisiActivityResult

class AdapterGrafikNutrisiActivityResult(private val dataClassGrafikNutrisiActivityResultList: List<DataClassGrafikNutrisiActivityResult>) :
    RecyclerView.Adapter<AdapterGrafikNutrisiActivityResult.NutrisiViewHolder>() {

    class NutrisiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNutrisiName: TextView = view.findViewById(R.id.tvNutrisiName)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val tvNutrisiAmount: TextView = view.findViewById(R.id.tvNutrisiAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrisiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nutrisi, parent, false)
        return NutrisiViewHolder(view)
    }

    override fun onBindViewHolder(holder: NutrisiViewHolder, position: Int) {
        val nutrisi = dataClassGrafikNutrisiActivityResultList[position]
        holder.tvNutrisiName.text = nutrisi.name
        holder.progressBar.progress = nutrisi.amount
        holder.tvNutrisiAmount.text = "${nutrisi.amount} gm"
    }

    override fun getItemCount(): Int {
        return dataClassGrafikNutrisiActivityResultList.size
    }
}