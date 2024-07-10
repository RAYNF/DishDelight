package com.example.dishdelight.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dishdelight.R
import com.example.dishdelight.data.dataclass.DataClassRecipeIngredientsFragmentDashboard
import com.example.dishdelight.data.remote.entity.MenuDetail
import kotlin.random.Random


class AdapterDetailIngredientRecipeFragmentIngredient(private val listIngredient: MenuDetail) :
    RecyclerView.Adapter<AdapterDetailIngredientRecipeFragmentIngredient.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_detail_ingredient, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val name = listIngredient.ingredients[position]
        val requestOptions = RequestOptions()
            .error(R.drawable.image_siomay)

        holder.categoryTxt.text = name
        Glide.with(holder.itemView.context).setDefaultRequestOptions(requestOptions)
            .load("https://umsu.ac.id/health/wp-content/uploads/2023/08/Wortel-Kandungan-dan-Manfaat-Untuk-Kesehatan-scaled.jpg")
            .into(holder.categoryImg)
        val randomCount = Random.nextInt(1, 6)
        holder.countTxt.text = randomCount.toString()


    }

    override fun getItemCount(): Int {
        return listIngredient.ingredients.size
    }

    class ProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.img_highlight_recipe)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_name)
        val countTxt: TextView = itemView.findViewById(R.id.title2)
    }

}