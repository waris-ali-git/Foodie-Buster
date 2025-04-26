package com.example.foodiebuster

import androidx.room.*

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE category IN ('Popular', 'popular', 'Salad', 'salad')")
    fun getPopularRecipes(): List<Recipe>

    @Query("SELECT * FROM recipe WHERE uid = :id")
    fun getRecipeById(id: Int): Recipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<Recipe>)

    @Update
    fun updateRecipe(recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Query("DELETE FROM recipe")
    fun deleteAllRecipes()
} 