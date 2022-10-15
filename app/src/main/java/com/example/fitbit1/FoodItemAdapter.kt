package com.example.fitbit1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodItemAdapter(private val foodList: List<DisplayFoodItem>) : RecyclerView.Adapter<FoodItemAdapter.ViewHolder>() {
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Your holder should contain a member variable for any view that will be set as you render
        // a row

        val itemNameTextView: TextView
        val itemCaloriesTextView: TextView


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each sub-view
        init {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            itemNameTextView = itemView.findViewById(R.id.food_name)
            itemCaloriesTextView = itemView.findViewById(R.id.food_calories)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.item_food, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val foodItem = foodList.get(position)
        // Set item views based on views and data model
        holder.itemNameTextView.text = foodItem.foodName
        holder.itemCaloriesTextView.text = foodItem.foodCalories
    }

    override fun getItemCount(): Int {
        return foodList.size
    }


}