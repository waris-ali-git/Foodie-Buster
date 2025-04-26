package com.example.foodiebuster

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodiebuster.databinding.SearchRvBinding

class SearchAdapter(var dataList: ArrayList<Recipe>, var context: Context): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList[position]
        try {
            // Map image names to resource IDs
            val imageResId = when (recipe.img) {
                // Category images
                "category_salad" -> R.drawable.category_salad
                "category_main" -> R.drawable.category_main
                "category_dessert" -> R.drawable.category_dessert
                "drinks" -> R.drawable.drinks
                else -> {
                    val resourceId = context.resources.getIdentifier(
                        recipe.img, "drawable", context.packageName
                    )
                    if (resourceId != 0) resourceId else R.drawable.category_salad // Default to category_salad for salads
                }
            }
            
            Glide.with(context)
                .load(imageResId)
                .placeholder(R.drawable.category_salad)
                .error(R.drawable.category_salad)
                .into(holder.binding.searchImg)
            
        } catch (e: Exception) {
            Log.e("SearchAdapter", "Error loading image for ${recipe.tittle}: ${recipe.img}", e)
            // Load default salad image in case of error
            Glide.with(context)
                .load(R.drawable.category_salad)
                .into(holder.binding.searchImg)
        }
        
        holder.binding.searchText.text = recipe.tittle

        // Set click listener to navigate to Recipe Activity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("img",dataList.get(position).img)
            intent.putExtra("tittle",dataList.get(position).tittle)
            intent.putExtra("des",dataList.get(position).des)
            intent.putExtra("ing",dataList.get(position).ing.joinToString("\n"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: ArrayList<Recipe>) {
        dataList = filterList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(var binding: SearchRvBinding) : RecyclerView.ViewHolder(binding.root)
}