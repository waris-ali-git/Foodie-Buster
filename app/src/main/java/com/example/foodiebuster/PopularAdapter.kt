package com.example.foodiebuster

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodiebuster.databinding.PopularRvItemBinding

class PopularAdapter(
    private val dataList: ArrayList<Recipe>,
    private val context: Context
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    class ViewHolder(val binding: PopularRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList[position]
        holder.binding.apply {
            popularText.text = recipe.tittle
            // Set cooking time from first ingredient (assumed to be time)
            recipe.ing.firstOrNull()?.let { time ->
                popularTime.text = time
            }

            // Load image with proper error handling
            try {
                val imgName = recipe.img
                Log.d("PopularAdapter", "Loading image for ${recipe.tittle} (${recipe.category}): $imgName")
                
                // Get resource ID for the image
                val resourceId = when (imgName) {
                    "pizza_sample" -> R.drawable.pizza_sample
                    "category_main" -> R.drawable.category_main
                    "category_dessert" -> R.drawable.category_dessert
                    "category_salad" -> R.drawable.category_salad
                    "drinks" -> R.drawable.drinks
                    else -> {
                        // Try to get resource ID dynamically
                        val resId = context.resources.getIdentifier(
                            imgName, "drawable", context.packageName
                        )
                        if (resId != 0) resId else R.drawable.pizza_sample // Fallback to default
                    }
                }
                
                Log.d("PopularAdapter", "Loading image resource: $resourceId")
                popularImg.setImageResource(resourceId)
                
            } catch (e: Exception) {
                Log.e("PopularAdapter", "Error loading image for recipe: ${recipe.tittle}", e)
                // Load default image in case of error
                popularImg.setImageResource(R.drawable.pizza_sample)
            }
        }
        
        // Set click listener for the entire item
        holder.itemView.setOnClickListener {
            val recipe = dataList[position]
            val intent = Intent(context, RecipeActivity::class.java).apply {
                putExtra("recipe_id", recipe.uid)
                putExtra("recipe_title", recipe.tittle)
                putExtra("recipe_description", recipe.des)
                putExtra("recipe_ingredients", recipe.ing.joinToString("\n"))
                putExtra("recipe_instructions", recipe.des) // Using description as instructions
                putExtra("recipe_image", recipe.img)
                putExtra("img", recipe.img)
                putExtra("tittle", recipe.tittle)
                putExtra("des", recipe.des)
                putExtra("ing", recipe.ing.joinToString("\n"))
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    fun updateData(newData: List<Recipe>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}