package com.example.data

import kotlinx.coroutines.flow.Flow

class ItemRepository(private val dao: LordNineDao) {
    val allItems: Flow<List<ItemEntity>> = dao.getAllItems()

    fun getItem(itemId: String): Flow<ItemEntity?> = dao.getItemById(itemId)

    fun getDropsForItem(itemId: String): Flow<List<MonsterDropEntity>> = dao.getDropsForItem(itemId)

    suspend fun checkAndSeedDatabase() {
        if (dao.getItemCount() < 14) {
            AppDatabase.populateDatabase(dao)
        }
    }
}
