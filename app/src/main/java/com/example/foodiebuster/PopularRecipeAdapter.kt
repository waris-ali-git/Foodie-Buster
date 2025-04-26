package com.example.foodiebuster



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PopularRecipeAdapter(private val recipes: List<PopularRecipe>) :
    RecyclerView.Adapter<PopularRecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.popular_img)
        val titleText: TextView = view.findViewById(R.id.popular_text)
        val timeText: TextView = view.findViewById(R.id.popular_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.imageView.setImageResource(recipe.imageResource)
        holder.titleText.text = recipe.tittle
        holder.timeText.text = "⏲ ${recipe.cookingTime} min"
    }

    override fun getItemCount() = recipes.size
}

data class PopularRecipe(
    val imageResource: Int,
    val tittle: String,
    val cookingTime: Int
) 