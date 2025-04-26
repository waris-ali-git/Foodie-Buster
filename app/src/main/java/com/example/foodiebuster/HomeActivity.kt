package com.example.foodiebuster

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodiebuster.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var database: RecipeDatabase
    private lateinit var recipeDao: RecipeDao
    private val dataList = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDatabase()
        setupPopularRecyclerView()
        loadPopularRecipes()


        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.saladIMG.setOnClickListener {
            val myIntent = Intent(this, CategoryActivity::class.java)
            myIntent.putExtra("TITTLE", "Salad")
            myIntent.putExtra("CATEGORY", "Salad")
            startActivity(myIntent)
        }
        binding.DessertIMG.setOnClickListener {
            val myIntent = Intent(this, CategoryActivity::class.java)
            myIntent.putExtra("TITTLE", "Dessert")
            myIntent.putExtra("CATEGORY", "Desserts")
            startActivity(myIntent)
        }
        binding.DrinksIMG.setOnClickListener {
            val myIntent = Intent(this, CategoryActivity::class.java)
            myIntent.putExtra("TITTLE", "Drinks")
            myIntent.putExtra("CATEGORY", "Drinks")
            startActivity(myIntent)
        }
        binding.complementaryIMG.setOnClickListener {
            val myIntent = Intent(this, CategoryActivity::class.java)
            myIntent.putExtra("TITTLE", "Dish")
            myIntent.putExtra("CATEGORY", "Dish")
            startActivity(myIntent)
        }

        binding.menuIcon.setOnClickListener {
            var dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog.setContentView(R.layout.bottom_sheet)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.window?.setGravity(Gravity.BOTTOM)
        }
    }

    private fun setupDatabase() {
        Log.d("HomeActivity", "Starting database initialization")
        try {
            database = RecipeDatabase.getDatabase(this)
            recipeDao = database.getRecipeDao()
            Log.d("HomeActivity", "Database initialization completed successfully")
        } catch (e: Exception) {
            Log.e("HomeActivity", "Error initializing database", e)
            Toast.makeText(this, "Error initializing database: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupPopularRecyclerView() {
        Log.d("HomeActivity", "Setting up RecyclerView")
        popularAdapter = PopularAdapter(dataList, this)
        binding.rvPopularRecipe.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
            setHasFixedSize(true)
        }
        Log.d("HomeActivity", "RecyclerView setup completed")
    }

    private fun loadPopularRecipes() {
        Log.d("HomeActivity", "Starting to load popular recipes")
        try {
            val recipes = recipeDao.getAllRecipes()
            Log.d("HomeActivity", "Received ${recipes.size} recipes")

            if (recipes.isEmpty()) {
                Log.d("HomeActivity", "No recipes found, adding sample data")
                addSampleData()
            } else {
                val popularRecipes = recipes.filter {
                    it.category.equals("Popular", ignoreCase = true) ||
                    it.category.equals("Salad", ignoreCase = true)
                }

                if (popularRecipes.isEmpty()) {
                    Log.d("HomeActivity", "No popular/salad recipes found, adding sample data")
                    addSampleData()
                } else {
                    updateRecyclerView(popularRecipes)
                }
            }
        } catch (e: Exception) {
            Log.e("HomeActivity", "Error loading recipes", e)
            addSampleData()
        }
    }

    private fun addSampleData() {
        try {
            Log.d("HomeActivity", "Adding sample data to database")
            val sampleRecipes = listOf(
                Recipe(
                    tittle = "Lazania",
                    des = "A rich and cheesy Italian dish layered with pasta, savory meat sauce, and creamy béchamel.\n\n" +
                            "Steps to prepare:\n" +
                            "1. Cook lasagna noodles according to package instructions.\n" +
                            "2. Prepare the meat sauce by sautéing onions and garlic, adding ground beef, and cooking until browned.\n" +
                            "3. Stir in tomato sauce, diced tomatoes, and Italian herbs. Simmer for 15 minutes.\n" +
                            "4. Prepare the béchamel sauce by melting butter, whisking in flour, and gradually adding milk.\n" +
                            "5. Layer lasagna: start with meat sauce, then noodles, then béchamel, then cheese.\n" +
                            "6. Repeat layers and top with shredded mozzarella.\n" +
                            "7. Bake at 180°C (350°F) for 40 minutes until golden and bubbly.\n" +
                            "8. Let it rest for 10 minutes before slicing. Serve and enjoy!",
                    category = "Popular",
                    ing = listOf("90 min",
                        "12 lasagna noodles",
                        "500g ground beef",
                        "1 onion, chopped",
                        "2 cloves garlic, minced",
                        "1 can (400g) tomato sauce",
                        "1 can (400g) diced tomatoes",
                        "1 tsp oregano",
                        "1 tsp basil",
                        "2 tbsp butter",
                        "2 tbsp flour",
                        "2 cups milk",
                        "1 cup ricotta cheese",
                        "2 cups mozzarella cheese",
                        "½ cup parmesan cheese",
                        "Salt and pepper to taste"),
                    img = "lazania"
                ),
                Recipe(
                    tittle = "Delicious Pizza",
                    des = "A tasty homemade pizza with fresh toppings",
                    category = "Popular",
                    ing = listOf("⏲ 30 min", "cheese", "tomato", "flour"),
                    img = "pizza_sample"
                ),
                Recipe(
                    tittle = "Sweet Dessert",
                    des = "Juicy grilled chicken with herbs",
                    category = "Popular",
                    ing = listOf("⏲ 45 min", "chicken", "herbs", "spices"),
                    img = "dessert"
                ),
                Recipe(
                    tittle = "Fresh Garden Salad",
                    des = "Crispy and healthy garden salad",
                    category = "Salad",
                    ing = listOf("⏲ 15 min", "lettuce", "tomatoes", "cucumber"),
                    img = "salad"
                ),
                Recipe(
                    tittle = "Chocolate Cake",
                    des = "Rich chocolate cake with creamy frosting",
                    category = "Popular",
                    ing = listOf("⏲ 45 min", "flour", "cocoa", "sugar"),
                    img = "cake"
                ),
                Recipe(
                    tittle = "Refreshments",
                    des = "Light weight snacks and refreshing light meal",
                    category = "Popular",
                    ing = listOf("⏲ 20 min", "bread", "vegetables", "sauce"),
                    img = "refreshment"
                )
            )

            recipeDao.insertRecipes(sampleRecipes)
            Log.d("HomeActivity", "Sample data added successfully")
            updateRecyclerView(sampleRecipes)
        } catch (e: Exception) {
            Log.e("HomeActivity", "Error adding sample data", e)
            Toast.makeText(this, "Error adding sample data: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateRecyclerView(recipes: List<Recipe>) {
        Log.d("HomeActivity", "Updating RecyclerView with ${recipes.size} recipes")
        dataList.clear()
        dataList.addAll(recipes)
        popularAdapter.notifyDataSetChanged()
        binding.rvPopularRecipe.visibility = android.view.View.VISIBLE
    }
}