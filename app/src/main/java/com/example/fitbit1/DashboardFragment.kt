package com.example.fitbit1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DashboardFragment : Fragment() {

    private val foodList = mutableListOf<DisplayFoodItem>()
    private lateinit var totalCalTextView: TextView
    private lateinit var avgCalTextView: TextView
    private lateinit var clearDataButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        totalCalTextView = view.findViewById(R.id.totalCalories)
        avgCalTextView = view.findViewById(R.id.avgCalories)
        clearDataButton = view.findViewById(R.id.clearDataButton)

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
                }
                setCaloriesAndAvg(foodList)

            }
        }

        clearDataButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                (activity?.application as FoodItemApplication).db.foodItemDao().deleteAll()
            }

            totalCalTextView.text = "0"
            avgCalTextView.text = "0"
        }

        // Update the return statement to return the inflated view from above
        return view
    }

    private fun setCaloriesAndAvg(foodList: MutableList<DisplayFoodItem>) {
        var total = 0
        if (foodList.isNotEmpty()) {
            for (item in foodList) {
                total += item.foodCalories!!.toInt()
            }
            totalCalTextView.text = total.toString()
            avgCalTextView.text = (total / foodList.count()).toString()
        } else {
            totalCalTextView.text = "0"
            avgCalTextView.text = "0"
        }
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