package com.example.dishdelight.ui.result.grafik

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dishdelight.R

class AdapterNutrisi(private val nutrisiList: List<Nutrisi>) :
    RecyclerView.Adapter<AdapterNutrisi.NutrisiViewHolder>() {

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
        val nutrisi = nutrisiList[position]
        holder.tvNutrisiName.text = nutrisi.name
        holder.progressBar.progress = nutrisi.amount
        holder.tvNutrisiAmount.text = "${nutrisi.amount} gm"
    }

    override fun getItemCount(): Int {
        return nutrisiList.size
    }
}