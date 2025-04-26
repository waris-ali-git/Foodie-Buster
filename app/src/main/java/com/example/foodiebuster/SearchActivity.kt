package com.example.foodiebuster

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodiebuster.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var dataList: ArrayList<Recipe>
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var database: RecipeDatabase
    private lateinit var recipeDao: RecipeDao
    private var allRecipes: List<Recipe> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupDatabase()
        setupRecyclerView()
        loadRecipes()

        // Set up back button
        binding.goBackHome.setOnClickListener {
            finish()
        }

        // Set up search functionality
        binding.Search2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    filterData(s.toString())
                } else {
                    // If search is empty, show all recipes
                    updateRecyclerView(allRecipes)
                }
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })

        // Request focus on search view
        binding.Search2.requestFocus()
    }

    private fun setupDatabase() {
        try {
            database = RecipeDatabase.getDatabase(this)
            recipeDao = database.getRecipeDao()
            Log.d("SearchActivity", "Database initialized successfully")
        } catch (e: Exception) {
            Log.e("SearchActivity", "Error initializing database", e)
            loadSampleData()
        }
    }

    private fun setupRecyclerView() {
        dataList = ArrayList()
        searchAdapter = SearchAdapter(dataList, this)
        
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
            setHasFixedSize(true)
        }
        Log.d("SearchActivity", "RecyclerView setup completed")
    }

    private fun loadRecipes() {
        try {
            allRecipes = recipeDao.getAllRecipes()
            Log.d("SearchActivity", "Loaded ${allRecipes.size} recipes")
            
            if (allRecipes.isEmpty()) {
                Log.d("SearchActivity", "No recipes found, loading sample data")
                loadSampleData()
            } else {
                updateRecyclerView(allRecipes)
            }
        } catch (e: Exception) {
            Log.e("SearchActivity", "Error loading recipes", e)
            loadSampleData()
        }
    }

    private fun filterData(filterText: String) {
        try {
            val filteredList = allRecipes.filter { recipe ->
                recipe.tittle.lowercase().contains(filterText.lowercase())
            }
            updateRecyclerView(filteredList)
            Log.d("SearchActivity", "Filtered ${filteredList.size} recipes for query: $filterText")
        } catch (e: Exception) {
            Log.e("SearchActivity", "Error filtering recipes", e)
        }
    }

    private fun loadSampleData() {
        val sampleRecipes = listOf(
            Recipe(
                tittle = "Lazania",
                des = "Rich chocolate cake with frosting",
                category = "Desserts",
                ing = listOf(" 60 min", "flour", "cocoa", "sugar"),
                img = "lazania"
            ),
            Recipe(
                tittle = "Chocolate Cake",
                des = "Juicy grilled chicken with herbs",
                category = "Popular",
                ing = listOf("⏲ 45 min", "chicken", "herbs", "spices"),
                img = "cake"
            ),Recipe(
                tittle = "Fresh Orange Juice",
                des = "Freshly squeezed orange juice",
                category = "Drinks",
                ing = listOf(" 5 min", "oranges"),
                img = "j1"
            ),
            Recipe(
                tittle = "Creamy Donuts",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 45 min", "romaine", "parmesan", "croutons"),
                img = "d6"
            ),
            Recipe(
                tittle = "Sprinkled Cream Donuts",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 35 min", "romaine", "parmesan", "croutons"),
                img = "d7"
            ),
            Recipe(
                tittle = "Cookie Buster",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 35 min", "romaine", "parmesan", "croutons"),
                img = "d8"
            ),

            Recipe(
                tittle = "Hot Wings",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 40 min", "romaine", "parmesan", "croutons"),
                img = "m9"
            ),Recipe(
                tittle = "Strawberry Smoothie",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "drinks"
            ),
            Recipe(
                tittle = "Rainbow Cream Cake",
                des = "Creamy vanilla ice cream",
                category = "Desserts",
                ing = listOf(" 55 min", "cream", "vanilla", "sugar"),
                img = "d2"
            ),
            Recipe(
                tittle = "Choco Vanilla Cake",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 60 min", "romaine", "parmesan", "croutons"),
                img = "d3"
            ),
            Recipe(
                tittle = "Decent Creamy",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 90 min", "romaine", "parmesan", "croutons"),
                img = "d4"
            ),
            Recipe(
                tittle = "Vanilla Cake",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 60 min", "romaine", "parmesan", "croutons"),
                img = "d5"
            ),
            Recipe(
                tittle = "Garlic Mayo Wings",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 30 min", "romaine", "parmesan", "croutons"),
                img = "m10"
            ),
            Recipe(
                tittle = "Pasta Carbonara",
                des = "Classic Italian pasta dish",
                category = "Dish",
                ing = listOf(" 30 min", "pasta", "eggs", "cheese"),
                img = "m2"
            ),
            Recipe(
                tittle = "Big Bang",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 40 min", "romaine", "parmesan", "croutons"),
                img = "m3"
            ),
            Recipe(
                tittle = "Mayo Fries",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 50 min", "romaine", "parmesan", "croutons"),
                img = "m4"
            ),
            Recipe(
                tittle = "Crispy Fries",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 50 min", "romaine", "parmesan", "croutons"),
                img = "m5"
            ),
            Recipe(
                tittle = "Orange Mango Combo",
                des = "Creamy strawberry smoothie",
                category = "Drinks",
                ing = listOf(" 10 min", "strawberries", "milk", "yogurt"),
                img = "j2"
            ),
            Recipe(
                tittle = "Slushes",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "j3"
            ),Recipe(
                tittle = "Mix Salad",
                des = "Crispy and healthy garden salad",
                category = "Salad",
                ing = listOf(" 15 min", "lettuce", "tomatoes", "cucumber"),
                img = "s1"
            ),
            Recipe(
                tittle = "Green Salad",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 25 min", "romaine", "parmesan", "croutons"),
                img = "s2"
            ),
            Recipe(
                tittle = "Mango Juice",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "j8"
            ),
            Recipe(
                tittle = "Loaded Fries",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 40 min", "romaine", "parmesan", "croutons"),
                img = "m6"
            ),
            Recipe(
                tittle = "Combat Fries",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 30 min", "romaine", "parmesan", "croutons"),
                img = "m7"
            ),
            Recipe(
                tittle = "Chicken Wings",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 50 min", "romaine", "parmesan", "croutons"),
                img = "m8"
            ),
            Recipe(
                tittle = "Carrot Juice",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 10 min", "romaine", "parmesan", "croutons"),
                img = "j5"
            ),
            Recipe(
                tittle = "Mint Flavors",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 10 min", "romaine", "parmesan", "croutons"),
                img = "j6"
            ),
            Recipe(
                tittle = "Carrot Salad",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 15 min", "romaine", "parmesan", "croutons"),
                img = "s3"
            ),
            Recipe(
                tittle = "Fruit mix Salad",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 15 min", "romaine", "parmesan", "croutons"),
                img = "s4"
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
            ), Recipe(
                tittle = "Soft Sweets",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "d9"
            ),
            Recipe(
                tittle = "Delight Taste Sweet",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "d10"
            ),
            Recipe(
                tittle = "Strawberry Shake",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "j4"
            ),Recipe(
                tittle = "Grilled Chicken",
                des = "Juicy grilled chicken with herbs",
                category = "Dish",
                ing = listOf(" 45 min", "chicken", "herbs", "spices"),
                img = "m1"
            ),
            Recipe(
                tittle = "Fresh Juices",
                des = "Fresh Juices of all Fruits",
                category = "Popular",
                ing = listOf("⏲ 45 min", "chicken", "herbs", "spices"),
                img = "juice"
            ),
            Recipe(
                tittle = "Refreshments",
                des = "Light weight snacks and refreshing light meal",
                category = "Popular",
                ing = listOf("⏲ 45 min", "chicken", "herbs", "spices"),
                img = "refreshment"
            )
        )
        
        try {
            recipeDao.insertRecipes(sampleRecipes)
            allRecipes = sampleRecipes
            Log.d("SearchActivity", "Sample data added successfully")
        } catch (e: Exception) {
            Log.e("SearchActivity", "Error adding sample data to database", e)
        }
        
        updateRecyclerView(sampleRecipes)
    }

    private fun updateRecyclerView(recipes: List<Recipe>) {
        dataList.clear()
        dataList.addAll(recipes)
        searchAdapter.notifyDataSetChanged()
        Log.d("SearchActivity", "Updated RecyclerView with ${recipes.size} recipes")
        
        // Log the recipes for debugging
        recipes.forEach { recipe ->
            Log.d("SearchActivity", "Recipe: ${recipe.tittle}, Image: ${recipe.img}, Category: ${recipe.category}")
        }
    }
}