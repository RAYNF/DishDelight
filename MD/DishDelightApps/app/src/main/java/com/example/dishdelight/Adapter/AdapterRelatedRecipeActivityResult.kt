package com.example.dishdelight.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dishdelight.R
import com.example.dishdelight.data.dataclass.DataClassRelatedRecipeActivityResult
import com.example.dishdelight.data.remote.entity.RecommendationsItem
import com.example.dishdelight.data.remote.entity.SearchResultsItem
import com.example.dishdelight.view.detailrecipe.DetailRecipeActivity

class AdapterRelatedRecipeActivityResult(private val listCategory: List<RecommendationsItem>): RecyclerView.Adapter<AdapterRelatedRecipeActivityResult.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_food, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val name = listCategory[position].menuName
//        val description = listCategory[position].dataDescription
        val requestOptions = RequestOptions()
            .error(R.drawable.image_siomay)


        Glide.with(holder.itemView.context)
            .load(listCategory[position].imageUrl)
            .apply(requestOptions)
            .into(holder.categoryImg)

        holder.categoryTxt.text = name

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val intent = Intent(holder.itemView.context, DetailRecipeActivity::class.java)
                intent.putExtra("ID_MENU", position+1)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ProgramViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.img_highlight_recipe)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_name)
    }

}