package com.example.fitbit1

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class FoodsFragment : Fragment() {

    private val foodList = mutableListOf<DisplayFoodItem>()
    private lateinit var foodItemsRv: RecyclerView
    private lateinit var foodAdapter: FoodItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_foods, container, false)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_foods, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        foodItemsRv = view.findViewById(R.id.foods)
        foodItemsRv.layoutManager = layoutManager
        foodItemsRv.setHasFixedSize(true)
        foodAdapter = FoodItemAdapter(foodList)
        foodItemsRv.adapter = foodAdapter

        // get database info to update foodList
        lifecycleScope.launch {
            (activity?.application as FoodItemApplication).db.foodItemDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayFoodItem(
                        entity.foodName,
                        entity.foodCalories
                    )
                }.also { mappedList ->
                    foodList.clear()
                    foodList.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
//                Log.d("DB TEst", databaseList.toString())
//                Log.d("foodList TEst", foodList.toString())
            }
        }

        view.findViewById<Button>(R.id.add_food_button).setOnClickListener {
            val intentToInput = Intent(context, FoodInputActivity::class.java)
            startActivity(intentToInput)
        }

        // Update the return statement to return the inflated view from above
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance(): FoodsFragment {
            return FoodsFragment()
        }
    }
}