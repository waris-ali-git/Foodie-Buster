package com.example.foodiebuster

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.foodiebuster.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    private var imgCrop = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load recipe data
        setupRecipeData()
        // Setup click listeners
        setupClickListeners()
    }

    private fun setupRecipeData() {
        try {
            // Load image using Glide instead of direct resource loading
            val imgName = intent.getStringExtra("img") ?: "pizza_sample"
            val imageResourceId = when (imgName) {
                "pizza_sample" -> R.drawable.pizza_sample
                "category_main" -> R.drawable.category_main
                "category_dessert" -> R.drawable.category_dessert
                "category_salad" -> R.drawable.category_salad
                "drinks" -> R.drawable.drinks
                else -> {
                    // Try to get resource ID dynamically
                    val resId = resources.getIdentifier(imgName, "drawable", packageName)
                    if (resId != 0) resId else R.drawable.pizza_sample
                }
            }
            
            Glide.with(this)
                .load(imageResourceId)
                .placeholder(R.drawable.pizza_sample)
                .error(R.drawable.pizza_sample)
                .into(binding.itemImg)

            // Set text data
            binding.title.text = intent.getStringExtra("tittle")
            binding.stepsData.text = intent.getStringExtra("des")
            
            // Handle ingredients
            val ingredients = intent.getStringExtra("ing")?.split("\n")
            if (!ingredients.isNullOrEmpty()) {
                binding.time.text = ingredients[0]  // First item is time
                val ingredientsList = ingredients.drop(1)  // Rest are ingredients
                binding.ingData.text = ingredientsList.joinToString("\n") { "🟢 $it" }
            }
        } catch (e: Exception) {
            Log.e("RecipeActivity", "Error loading recipe data", e)
            Toast.makeText(this, "Error loading recipe", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setupClickListeners() {
        // Back button
        binding.backBtmn.setOnClickListener {
            finish()
        }
        // Full screen toggle with proper zoom functionality
        binding.fullScreen.setOnClickListener {
            if (imgCrop) {
                // Zoom out to show full image
                binding.itemImg.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                binding.itemImg.scaleType = ImageView.ScaleType.FIT_CENTER
                binding.shadow.visibility = View.GONE
                binding.fullScreen.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                binding.backBtmn.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
            } else {
                // Return to original size
                binding.itemImg.layoutParams.height = resources.getDimensionPixelSize(R.dimen.recipe_image_height)
                binding.itemImg.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.shadow.visibility = View.VISIBLE
                binding.fullScreen.visibility = View.VISIBLE
                binding.fullScreen.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                binding.backBtmn.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            }
            imgCrop = !imgCrop
        }
        binding.steps.background = null
        binding.steps.setTextColor(getColor(R.color.black))

        // Toggle between ingredients and steps
        binding.ing.setOnClickListener {
            binding.ingData.visibility = View.VISIBLE
            binding.stepsData.visibility = View.GONE
            binding.ing.setBackgroundResource(R.drawable.btn_ing)
            binding.ing.setTextColor(getColor(R.color.white))
            binding.steps.background = null
            binding.steps.setTextColor(getColor(R.color.black))
        }

        binding.steps.setOnClickListener {
            binding.ingData.visibility = View.GONE
            binding.stepsData.visibility = View.VISIBLE
            binding.steps.setBackgroundResource(R.drawable.btn_ing)
            binding.steps.setTextColor(getColor(R.color.white))
            binding.ing.background = null
            binding.ing.setTextColor(getColor(R.color.black))
        }
    }
}