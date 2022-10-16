package com.example.fitbit1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    var foodList: MutableList<DisplayFoodItem> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lookup the RecyclerView in activity layout
        val foodItemsRv = findViewById<RecyclerView>(R.id.foods)

        // Create adapter passing in the list of food items
        val adapter = FoodItemAdapter(foodList)



        // get database info to update foodList
        lifecycleScope.launch {
            (application as FoodItemApplication).db.foodItemDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFoodItem(
                        entity.foodName,
                        entity.foodCalories
                    )
                }.also { mappedList ->
                    foodList.clear()
                    foodList.addAll(mappedList)
                    adapter.notifyDataSetChanged()
                }
                Log.d("DB TEst", databaseList.toString())
                Log.d("foodList TEst", foodList.toString())
            }
        }



        // Attach the adapter to the RecyclerView to populate items
        foodItemsRv.adapter = adapter


        // Set layout manager to position the items
        foodItemsRv.layoutManager = LinearLayoutManager(this)


        findViewById<Button>(R.id.add_food_button).setOnClickListener {

            val intentToInput = Intent(this, FoodInputActivity::class.java)
            startActivity(intentToInput)

        }

    }
}