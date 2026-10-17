package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LordNineDao {
    @Query("SELECT * FROM items ORDER BY levelReq DESC, name ASC")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE id = :itemId LIMIT 1")
    fun getItemById(itemId: String): Flow<ItemEntity?>

    @Query("SELECT * FROM monster_drops WHERE itemId = :itemId ORDER BY monsterLevel DESC")
    fun getDropsForItem(itemId: String): Flow<List<MonsterDropEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrops(drops: List<MonsterDropEntity>)

    @Query("SELECT COUNT(*) FROM items")
    suspend fun getItemCount(): Int
}
