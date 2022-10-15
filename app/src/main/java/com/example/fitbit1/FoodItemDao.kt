package com.example.fitbit1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodItemDao {
    @Query("SELECT * FROM foodItem_table")
    fun getAll(): Flow<List<FoodItemEntity>>

    @Insert
    fun insertAll(foodItems: List<FoodItemEntity>)

    @Insert
    fun insert(foodItem: FoodItemEntity)

    @Query("DELETE FROM foodItem_table")
    fun deleteAll()
}