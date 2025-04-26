package com.example.foodiebuster

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,

    @ColumnInfo(name = "img")
    val img: String,

    @ColumnInfo(name = "tittle")
    val tittle: String,

    @ColumnInfo(name = "des")
    val des: String,

    @ColumnInfo(name = "ing")
    @TypeConverters(Converters::class)
    val ing: List<String>,

    @ColumnInfo(name = "category")
    val category: String
) {
    fun getImageResourceId(context: Context): Int {
        return try {
            when (img) {
                "pizza_sample" -> R.drawable.pizza_sample
                "category_main" -> R.drawable.category_main
                "category_dessert" -> R.drawable.category_dessert
                "category_salad" -> R.drawable.category_salad
                "drinks" -> R.drawable.drinks
                else -> {
                    val resId = context.resources.getIdentifier(img, "drawable", context.packageName)
                    if (resId != 0) resId else R.drawable.pizza_sample
                }
            }
        } catch (e: Exception) {
            R.drawable.pizza_sample
        }
    }
}