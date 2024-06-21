package com.example.dishdelight.Adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dishdelight.R
import com.example.dishdelight.data.entity.RecommendationsItem


class AdapterPopularRecipeFragmentHome(private val listCategory: List<RecommendationsItem>) :
    RecyclerView.Adapter<AdapterPopularRecipeFragmentHome.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_popular_food, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val item = listCategory[position]

        val requestOptions = RequestOptions()
            .error(R.drawable.image_siomay)

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .apply(requestOptions)
            .into(holder.categoryImg)

        holder.categoryTxt.text = item.menuName

        // the id doesn't exist in the model
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(position+1)
        }

        if (item.isFavorite)
            holder.btnFav.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        else
            holder.btnFav.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.img_highlight_recipe)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_name)
        val btnFav: ImageButton = itemView.findViewById(R.id.btn_fav)
    }

    interface OnItemClickCallback {
        fun onItemClicked(menuId: Int)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}

