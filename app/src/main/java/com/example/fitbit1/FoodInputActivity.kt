package com.example.fitbit1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


private const val TAG = "FoodInputActivity"

class FoodInputActivity : AppCompatActivity() {
    private lateinit var nameInputView: EditText
    private lateinit var caloriesInputView: EditText
    private lateinit var inputFoodButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_input)

        // TODO: Find the views for the screen
        nameInputView = findViewById(R.id.inputFoodName)
        caloriesInputView = findViewById(R.id.inputCalories)
        inputFoodButton = findViewById<Button>(R.id.inputFoodButton)


        inputFoodButton.setOnClickListener {
//            val newFoodItem: DisplayFoodItem = DisplayFoodItem(nameInputView.text.toString(), caloriesInputView.text.toString())
            val newFoodName = nameInputView.text.toString()
            val newFoodCalories = caloriesInputView.text.toString()

            val newEntity = FoodItemEntity(foodName = newFoodName, foodCalories = newFoodCalories)

            // update database with new food item
            lifecycleScope.launch(IO) {
                (application as FoodItemApplication).db.foodItemDao().insert(newEntity)
            }


            // go back to main activity
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
        }
    }
}