package com.example.foodiebuster

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodiebuster.databinding.CategoryRvBinding

class CategoryAdapter(var dataList: ArrayList<Recipe>, var context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList[position]
        try {
            // Map image names to resource IDs with proper salad images
            val imageResId = when {
                // Other category images
                recipe.img == "category_salad" -> R.drawable.category_salad
                recipe.img == "category_main" -> R.drawable.category_main
                recipe.img == "category_dessert" -> R.drawable.category_dessert
                recipe.img == "drinks" -> R.drawable.drinks
                else -> {
                    val resourceId = context.resources.getIdentifier(
                        recipe.img, "drawable", context.packageName
                    )
                    if (resourceId != 0) resourceId else R.drawable.category_salad
                }
            }
            
            // Load the image with Glide
            Glide.with(context)
                .load(imageResId)
                .placeholder(R.drawable.category_salad)
                .error(R.drawable.category_salad)
                .into(holder.binding.img)
                
            Log.d("CategoryAdapter", "Loading image for ${recipe.tittle}: ${recipe.img}")
            
        } catch (e: Exception) {
            Log.e("CategoryAdapter", "Error loading image for ${recipe.tittle}", e)
            // Load default salad image in case of error
            Glide.with(context)
                .load(R.drawable.category_salad)
                .into(holder.binding.img)
        }

        // Set the title
        holder.binding.foodtittle.text = recipe.tittle

        // Set the time from ingredients
        try {
            val time = recipe.ing.firstOrNull() ?: "⏲ N/A"
            holder.binding.time.text = time
        } catch (e: Exception) {
            Log.e("CategoryAdapter", "Error setting time for ${recipe.tittle}", e)
            holder.binding.time.text = "⏲ N/A"
        }
        holder.binding.nextButton.setOnClickListener {
            var intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("img",dataList.get(position).img)
            intent.putExtra("tittle",dataList.get(position).tittle)
            intent.putExtra("des",dataList.get(position).des)
            intent.putExtra("ing",dataList.get(position).ing.joinToString("\n"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = dataList.size

    inner class ViewHolder(var binding: CategoryRvBinding) : RecyclerView.ViewHolder(binding.root)
}