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
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var foodList: MutableList<DisplayFoodItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Lookup the RecyclerView in activity layout
        val foodItemsRv = findViewById<RecyclerView>(R.id.foods)


        // Create adapter passing in the list of wish items
        val adapter = FoodItemAdapter(foodList)

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
            }

        }

//        Log.d("entity test", foodList.toString())


        // Attach the adapter to the RecyclerView to populate items
        foodItemsRv.adapter = adapter
        // Set layout manager to position the items
        foodItemsRv.layoutManager = LinearLayoutManager(this)

//        var nameInput = findViewById<EditText>(R.id.nameEditText)
//        var priceInput = findViewById<EditText>(R.id.priceEditText)



        findViewById<Button>(R.id.add_food_button).setOnClickListener {

            val intentToInput = Intent(this, FoodInputActivity::class.java)
            startActivity(intentToInput)
//
//            // Get text form EditText views and create wish item
//            val newWishItem = WishItem(nameInput.text.toString(), priceInput.text.toString(), urlInput.text.toString())
//            // Add new item to existing list of wish items
//            wishlist.add(newWishItem)
//            // Notify the adapter there's a new wish item so the RecyclerView layout is updated
//            adapter.notifyDataSetChanged()
//
//            nameInput.setText("")
//            priceInput.setText("")
//            urlInput.setText("")


        }

    }
}