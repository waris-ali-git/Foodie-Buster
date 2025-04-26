package com.example.foodiebuster

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodiebuster.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val dataList = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get category from intent
        val category = intent.getStringExtra("CATEGORY") ?: return
        binding.tittle.text = intent.getStringExtra("TITTLE") ?: category

        // Set up back button
        binding.backbtn.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        loadRecipesByCategory(category)
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter(dataList, this)
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(this@CategoryActivity)
            adapter = categoryAdapter
        }
    }

    private fun loadRecipesByCategory(category: String) {
        try {
            val db = RecipeDatabase.getDatabase(this)
            val recipes = db.getRecipeDao().getAllRecipes()
            
            // Filter recipes by category
            val categoryRecipes = recipes.filter { 
                it.category?.equals(category, ignoreCase = true) == true 
            }

            if (categoryRecipes.isEmpty()) {
                // If no recipes found, load sample data
                when (category.lowercase()) {
                    "salad" -> loadSaladRecipes()
                    "drinks" -> loadDrinkRecipes()
                    "desserts" -> loadDessertRecipes()
                    "dish" -> loadMainDishRecipes()
                }
            } else {
                // Update RecyclerView with found recipes
                dataList.clear()
                dataList.addAll(categoryRecipes)
                categoryAdapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            Log.e("CategoryActivity", "Error loading recipes", e)
            // Load appropriate sample data on error
            when (category.lowercase()) {
                "salad" -> loadSaladRecipes()
                "drinks" -> loadDrinkRecipes()
                "desserts" -> loadDessertRecipes()
                "dish" -> loadMainDishRecipes()
            }
        }
    }

    private fun loadSaladRecipes() {
        val sampleRecipes = listOf(
            Recipe(
                tittle = "Mix Salad",
                des = "A fresh and vibrant mixed salad perfect for any occasion.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Wash and chop all vegetables\n" +
                        "2. In a large bowl, combine lettuce, tomatoes, and cucumber\n" +
                        "3. Add sliced onions and bell peppers\n" +
                        "4. For the dressing, mix olive oil, lemon juice, and seasonings\n" +
                        "5. Pour dressing over salad and toss gently\n" +
                        "6. Add croutons and parmesan cheese\n" +
                        "7. Serve immediately while fresh and crispy",
                category = "Salad",
                ing = listOf(
                    "15 min",
                    "2 cups fresh lettuce",
                    "2 medium tomatoes",
                    "1 cucumber",
                    "1 red onion",
                    "1 bell pepper",
                    "½ cup olive oil",
                    "2 tbsp lemon juice",
                    "Salt and pepper to taste",
                    "1 cup croutons",
                    "¼ cup parmesan cheese"
                ),
                img = "as1"
            ),
            Recipe(
                tittle = "Green Salad",
                des = "A nutritious green salad with a delicious Caesar dressing.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Wash and dry romaine lettuce thoroughly\n" +
                        "2. Cut lettuce into bite-sized pieces\n" +
                        "3. Prepare Caesar dressing by mixing ingredients\n" +
                        "4. Toast bread cubes for homemade croutons\n" +
                        "5. Toss lettuce with dressing\n" +
                        "6. Add croutons and shaved parmesan\n" +
                        "7. Season with black pepper\n" +
                        "8. Serve immediately",
                category = "Salad",
                ing = listOf(
                    "25 min",
                    "3 heads romaine lettuce",
                    "1 cup parmesan cheese",
                    "2 cups croutons",
                    "4 cloves garlic",
                    "2 anchovy fillets",
                    "1 egg yolk",
                    "2 tbsp Dijon mustard",
                    "½ cup olive oil",
                    "2 tbsp Worcestershire sauce",
                    "Salt and black pepper"
                ),
                img = "as2"
            ),
            Recipe(
                tittle = "Carrot Salad",
                des = "A sweet and crunchy carrot salad with raisins.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Peel and grate fresh carrots\n" +
                        "2. Soak raisins in warm water for 10 minutes\n" +
                        "3. Mix mayonnaise, honey, and lemon juice\n" +
                        "4. Drain raisins and pat dry\n" +
                        "5. Combine carrots, raisins, and dressing\n" +
                        "6. Add chopped walnuts\n" +
                        "7. Chill for 1 hour before serving",
                category = "Salad",
                ing = listOf(
                    "15 min",
                    "4 large carrots",
                    "½ cup raisins",
                    "⅓ cup mayonnaise",
                    "2 tbsp honey",
                    "1 tbsp lemon juice",
                    "¼ cup chopped walnuts",
                    "¼ tsp salt",
                    "Pinch of cinnamon"
                ),
                img = "as3"
            ),
            Recipe(
                tittle = "Broccoli Salad",
                des = "A crunchy and refreshing broccoli salad packed with flavors.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Chop fresh broccoli florets\n" +
                        "2. Dice red onion and crispy bacon\n" +
                        "3. Mix mayonnaise, apple cider vinegar, and sugar for dressing\n" +
                        "4. Combine broccoli, onion, bacon, and shredded cheese\n" +
                        "5. Pour dressing and mix well\n" +
                        "6. Let sit for 30 minutes before serving",
                category = "Salad",
                ing = listOf(
                    "20 min",
                    "3 cups fresh broccoli florets",
                    "½ cup red onion (diced)",
                    "6 slices cooked bacon (crumbled)",
                    "1 cup shredded cheddar cheese",
                    "½ cup mayonnaise",
                    "2 tbsp apple cider vinegar",
                    "1 tbsp sugar"
                ),
                img = "as5"
            ),
            Recipe(
                tittle = "Healthy Salad",
                des = "A nutritious and refreshing salad loaded with superfoods.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Chop kale, spinach, and arugula\n" +
                        "2. Dice avocados and cherry tomatoes\n" +
                        "3. Add quinoa and mix well\n" +
                        "4. Prepare dressing with olive oil and balsamic vinegar\n" +
                        "5. Toss everything together and serve fresh",
                category = "Salad",
                ing = listOf(
                    "10 min",
                    "1 cup kale (chopped)",
                    "1 cup spinach",
                    "½ cup arugula",
                    "1 avocado (diced)",
                    "½ cup cherry tomatoes (halved)",
                    "1 cup cooked quinoa",
                    "2 tbsp olive oil",
                    "1 tbsp balsamic vinegar",
                    "Salt and pepper to taste"
                ),
                img = "as6"
            ),
            Recipe(
                tittle = "Vegetable Salad",
                des = "A colorful and delicious vegetable salad.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Chop bell peppers, carrots, and cucumbers\n" +
                        "2. Add red cabbage and green onions\n" +
                        "3. Prepare a tangy dressing with lemon juice and mustard\n" +
                        "4. Mix all ingredients well and serve",
                category = "Salad",
                ing = listOf(
                    "15 min",
                    "1 red bell pepper",
                    "1 yellow bell pepper",
                    "1 cucumber",
                    "1 cup shredded red cabbage",
                    "2 green onions",
                    "2 tbsp lemon juice",
                    "1 tbsp Dijon mustard",
                    "Salt and pepper to taste"
                ),
                img = "as7"
            ),
            Recipe(
                tittle = "Fruit Mix Salad",
                des = "A refreshing and juicy fruit salad with a citrus twist.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Chop fresh fruits like apples, bananas, and oranges\n" +
                        "2. Add grapes, berries, and pineapple chunks\n" +
                        "3. Drizzle with honey and a splash of orange juice\n" +
                        "4. Toss everything together and chill before serving",
                category = "Salad",
                ing = listOf(
                    "15 min",
                    "1 apple (diced)",
                    "1 banana (sliced)",
                    "1 orange (peeled and segmented)",
                    "½ cup grapes",
                    "½ cup mixed berries",
                    "½ cup pineapple chunks",
                    "2 tbsp honey",
                    "1 tbsp orange juice"
                ),
                img = "as4"
            )
        )
        updateRecyclerView(sampleRecipes)
    }

    private fun loadDrinkRecipes() {
        val sampleRecipes = listOf(
            Recipe(
                tittle = "Fresh Orange Juice",
                des = "Pure and refreshing orange juice made from fresh oranges.\n\n" +
                    "Steps to prepare:\n" +
                    "1. Wash oranges thoroughly\n" +
                    "2. Cut oranges in half\n" +
                    "3. Using a citrus juicer, extract juice\n" +
                    "4. Strain pulp if desired\n" +
                    "5. Add honey if needed\n" +
                    "6. Chill before serving\n" +
                    "7. Garnish with orange slice",
                category = "Drinks",
                ing = listOf(
                    " 5 min",
                    "6 fresh oranges",
                    "1 tbsp honey (optional)",
                    "Ice cubes",
                    "Mint leaves for garnish"
                ),
                img = "j1"
            ),
            Recipe(
                tittle = "Orange Mango Combo",
                des = "A tropical blend of orange and mango.\n\n" +
                    "Steps to prepare:\n" +
                    "1. Peel and cube mango\n" +
                    "2. Extract orange juice\n" +
                    "3. Blend mango chunks until smooth\n" +
                    "4. Mix with orange juice\n" +
                    "5. Add honey and blend again\n" +
                    "6. Strain if needed\n" +
                    "7. Serve over ice",
                category = "Drinks",
                ing = listOf(
                    " 10 min",
                    "2 ripe mangoes",
                    "3 oranges",
                    "2 tbsp honey",
                    "1 cup ice",
                    "Mint leaves",
                    "Orange slice for garnish"
                ),
                img = "j2"
            ),
            Recipe(
                tittle = "Slushes",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "j3"
            ),
            Recipe(
                tittle = "Strawberry Shake",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "j4"
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
                tittle = "Mango Juice",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "j8"
            ),
            Recipe(
                tittle = "Strawberry Smoothie",
                des = "Classic Caesar salad with croutons",
                category = "Drinks",
                ing = listOf(" 20 min", "romaine", "parmesan", "croutons"),
                img = "drinks"
            )
        )
        updateRecyclerView(sampleRecipes)
    }

    private fun loadDessertRecipes() {
        val sampleRecipes = listOf(
            Recipe(
                tittle = "Strawberry Cake",
                des = "A soft, moist, and delicious strawberry cake made with fresh strawberries and topped with a rich cream cheese frosting.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Wash and hull fresh strawberries, then blend them into a smooth puree.\n" +
                        "2. In a bowl, sift together flour, baking powder, and salt.\n" +
                        "3. In a separate bowl, beat butter and sugar until light and fluffy.\n" +
                        "4. Add eggs one at a time, mixing well after each addition.\n" +
                        "5. Stir in vanilla extract and strawberry puree.\n" +
                        "6. Gradually add dry ingredients to the wet mixture, alternating with milk, and mix until just combined.\n" +
                        "7. Preheat oven to 350°F (175°C) and grease two round cake pans.\n" +
                        "8. Pour the batter into the pans and bake for 30-35 minutes or until a toothpick inserted in the center comes out clean.\n" +
                        "9. For the frosting, beat cream cheese and butter until smooth.\n" +
                        "10. Add powdered sugar and vanilla extract, mixing until fluffy.\n" +
                        "11. Once cakes are cooled, spread frosting over the first layer, then place the second layer on top and frost the entire cake.\n" +
                        "12. Garnish with fresh strawberries and serve chilled.",
                category = "Desserts",
                ing = listOf(
                    "60 min",
                    "2 ½ cups all-purpose flour",
                    "1 cup strawberry puree",
                    "1 cup granulated sugar",
                    "1 cup unsalted butter (softened)",
                    "3 large eggs",
                    "1 tsp vanilla extract",
                    "1 tbsp baking powder",
                    "½ tsp salt",
                    "1 cup whole milk",
                    "8 oz cream cheese",
                    "½ cup unsalted butter",
                    "2 cups powdered sugar",
                    "1 tsp vanilla extract"
                ),
                img = "d1"
            ),
            Recipe(
                tittle = "Rainbow Cream Cake",
                des = "A colorful, moist, and creamy rainbow cake with layers of vanilla-flavored sponge and a rich buttercream frosting.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Preheat oven to 350°F (175°C) and grease multiple cake pans.\n" +
                        "2. In a bowl, sift together flour, baking powder, and salt.\n" +
                        "3. In a separate large bowl, beat butter and sugar until light and fluffy.\n" +
                        "4. Add eggs one at a time, mixing well after each addition.\n" +
                        "5. Stir in vanilla extract and mix until smooth.\n" +
                        "6. Gradually alternate adding the dry ingredients and milk, mixing until combined.\n" +
                        "7. Divide the batter into separate bowls and add different food coloring to each portion.\n" +
                        "8. Pour the colored batters into separate cake pans and bake for 20-25 minutes.\n" +
                        "9. Let the cakes cool completely before assembling.\n" +
                        "10. For the frosting, beat heavy cream, powdered sugar, and vanilla extract until fluffy.\n" +
                        "11. Layer each cake with frosting in between and cover the entire cake with a smooth layer.\n" +
                        "12. Decorate with rainbow sprinkles and serve chilled.",
                category = "Desserts",
                ing = listOf(
                    "55 min",
                    "2 ½ cups all-purpose flour",
                    "1 tbsp baking powder",
                    "½ tsp salt",
                    "1 cup unsalted butter (softened)",
                    "1 ½ cups granulated sugar",
                    "4 large eggs",
                    "1 tbsp vanilla extract",
                    "1 cup whole milk",
                    "Food coloring (red, orange, yellow, green, blue, purple)",
                    "2 cups heavy cream",
                    "1 cup powdered sugar",
                    "Rainbow sprinkles"
                ),
                img = "d2"
            ),
            Recipe(
                tittle = "Choco Vanilla Cake",
                des = "A delicious blend of rich chocolate and smooth vanilla flavors layered in a moist cake with a creamy frosting.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Preheat oven to 350°F (175°C) and grease two cake pans.\n" +
                        "2. In a bowl, mix flour, baking powder, and salt.\n" +
                        "3. In another bowl, beat butter and sugar until fluffy.\n" +
                        "4. Add eggs, one at a time, then mix in vanilla extract.\n" +
                        "5. Divide the batter into two portions—mix cocoa powder in one.\n" +
                        "6. Pour vanilla and chocolate batters alternately into the pans.\n" +
                        "7. Bake for 30-35 minutes and let them cool.\n" +
                        "8. Prepare vanilla frosting and spread between layers.\n" +
                        "9. Top with chocolate drizzle and serve chilled.",
                category = "Desserts",
                ing = listOf(
                    "60 min",
                    "2 ½ cups flour",
                    "1 tbsp baking powder",
                    "½ tsp salt",
                    "1 cup butter",
                    "1 ½ cups sugar",
                    "4 eggs",
                    "1 tbsp vanilla extract",
                    "½ cup cocoa powder",
                    "1 cup milk",
                    "2 cups whipped cream",
                    "½ cup chocolate syrup"
                ),
                img = "d3"
            ),
            Recipe(
                tittle = "Decent Creamy Cake",
                des = "A soft and creamy cake with a smooth vanilla-flavored frosting, perfect for special occasions.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Preheat oven to 350°F (175°C) and grease a cake pan.\n" +
                        "2. Beat butter and sugar until fluffy, then add eggs one by one.\n" +
                        "3. Sift flour, baking powder, and salt and gradually mix into the batter.\n" +
                        "4. Stir in vanilla extract and milk, then mix until smooth.\n" +
                        "5. Pour batter into the pan and bake for 35-40 minutes.\n" +
                        "6. Let the cake cool before frosting.\n" +
                        "7. Prepare a creamy frosting using heavy cream and powdered sugar.\n" +
                        "8. Spread the frosting evenly over the cake and garnish with fruit or chocolate shavings.",
                category = "Desserts",
                ing = listOf(
                    "90 min",
                    "2 ¾ cups flour",
                    "1 tbsp baking powder",
                    "½ tsp salt",
                    "1 cup butter",
                    "1 ¾ cups sugar",
                    "5 eggs",
                    "1 tbsp vanilla extract",
                    "1 cup milk",
                    "2 cups heavy cream",
                    "1 cup powdered sugar",
                    "Fruit slices (optional)",
                    "Chocolate shavings (optional)"
                ),
                img = "d4"
            ),
            Recipe(
                tittle = "Vanilla Cake",
                des = "A light and fluffy vanilla cake with a rich buttercream frosting, perfect for any celebration.\n\n" +
                        "Steps to prepare:\n" +
                        "1. Preheat oven to 350°F (175°C) and grease a cake pan.\n" +
                        "2. Beat butter and sugar until fluffy, then add eggs one at a time.\n" +
                        "3. Mix flour, baking powder, and salt separately, then gradually add to the batter.\n" +
                        "4. Stir in vanilla extract and milk, mixing until smooth.\n" +
                        "5. Pour batter into the pan and bake for 30-35 minutes.\n" +
                        "6. Let the cake cool completely before frosting.\n" +
                        "7. Prepare a smooth buttercream frosting and spread over the cake.\n" +
                        "8. Garnish with sprinkles or fruit slices and serve.",
                category = "Desserts",
                ing = listOf(
                    "60 min",
                    "2 ½ cups all-purpose flour",
                    "1 tbsp baking powder",
                    "½ tsp salt",
                    "1 cup unsalted butter",
                    "1 ½ cups sugar",
                    "4 eggs",
                    "1 tbsp vanilla extract",
                    "1 cup milk",
                    "2 cups buttercream frosting",
                    "Rainbow sprinkles (optional)",
                    "Fruit slices (optional)"
                ),
                img = "d5"
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
            )
        )
        updateRecyclerView(sampleRecipes)
    }

    private fun loadMainDishRecipes() {
        val sampleRecipes = listOf(
            Recipe(
                tittle = "Grilled Chicken",
                des = "Juicy grilled chicken with herbs",
                category = "Dish",
                ing = listOf(" 45 min", "chicken", "herbs", "spices"),
                img = "m1"
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
                tittle = "Hot Wings",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 40 min", "romaine", "parmesan", "croutons"),
                img = "m9"
            ),
            Recipe(
                tittle = "Garlic Mayo Wings",
                des = "Classic Caesar salad with croutons",
                category = "Salad",
                ing = listOf(" 30 min", "romaine", "parmesan", "croutons"),
                img = "m10"
            )
        )
        updateRecyclerView(sampleRecipes)
    }

    private fun updateRecyclerView(recipes: List<Recipe>) {
        dataList.clear()
        dataList.addAll(recipes)
        categoryAdapter.notifyDataSetChanged()
    }
}