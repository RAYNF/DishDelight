package com.example.dishdelight.Adapter

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
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
import com.example.dishdelight.data.dataclass.DataClassFavoriteRecipeFragmentNotification
import com.example.dishdelight.data.remote.entity.FavoriteMenusItem
import com.example.dishdelight.view.detailrecipe.DetailRecipeActivity

class AdapterFavoriteRecipeFragmentNotification(private val listCategory: List<FavoriteMenusItem>): RecyclerView.Adapter<AdapterFavoriteRecipeFragmentNotification.ProgramViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProgramViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_favorit, parent, false)
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
        holder.ratingTXT.text = item.menuRating?.toString()?:"0"

        holder.btnFav.setOnClickListener {
            Log.d("posisi", position.toString())
//            onItemClickCallback.onItemClicked(position+1)
//            onItemClickCallback.onItemClicked(reversePosition)
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val reversePosition = itemCount - position
                print(reversePosition)
//                mainViewModel.AddFavorite(reversePosition, token)
//                mainViewModel.message.observe(lifecyleOwner){
//                    Toast.makeText(holder.itemView.context,it,Toast.LENGTH_SHORT).show()
//                }

            }
        }



        holder.itemView.setOnClickListener {
            Log.d("posisi", position.toString())
//            onItemClickCallback.onItemClicked(position+1)
//            onItemClickCallback.onItemClicked(reversePosition)
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val reversePosition = itemCount - position

                val intent = Intent(holder.itemView.context, DetailRecipeActivity::class.java)
                intent.putExtra("ID_MENU", reversePosition)
                holder.itemView.context.startActivity(intent)
            }
        }

        if (item.isFavorite)
            holder.btnFav.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        else
            holder.btnFav.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    class ProgramViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val categoryImg: ImageView = itemView.findViewById(R.id.img_highlight_recipe)
        val categoryTxt: TextView = itemView.findViewById(R.id.tv_name)
//        val btnFav :ImageButton = itemView.findViewById(R.id.btn_fav)
//        val ratingTXT : TextView = itemView.findViewById(R.id.tv_rating)
        val  ratingTXT : TextView = itemView.findViewById(R.id.tv_rating)
        val btnFav : ImageButton = itemView.findViewById(R.id.btn_fav)
    }

}