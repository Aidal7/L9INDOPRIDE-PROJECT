package com.example.data

import kotlinx.coroutines.flow.Flow

class ItemRepository(private val dao: LordNineDao) {
    val allItems: Flow<List<ItemEntity>> = dao.getAllItems()
    val allMonsters: Flow<List<MonsterEntity>> = dao.getAllMonsters()
    val allMaps: Flow<List<MapEntity>> = dao.getAllMaps()
    val allSkills: Flow<List<SkillEntity>> = dao.getAllSkills()
    val allQuests: Flow<List<QuestEntity>> = dao.getAllQuests()
    val allEnchants: Flow<List<EnchantEntity>> = dao.getAllEnchants()
    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()
    val userEquipment: Flow<List<UserEquipEntity>> = dao.getUserEquipment()

    fun getItem(itemId: String): Flow<ItemEntity?> = dao.getItemById(itemId)

    fun getDropsForItem(itemId: String): Flow<List<MonsterDropEntity>> = dao.getDropsForItem(itemId)

    fun getMonstersByMap(mapName: String): Flow<List<MonsterEntity>> = dao.getMonstersByMap(mapName)

    fun getSkillsByClass(className: String): Flow<List<SkillEntity>> = dao.getSkillsByClass(className)

    suspend fun toggleQuestCompleted(questId: String, completed: Boolean) {
        dao.setQuestCompleted(questId, completed)
    }

    suspend fun saveProfile(profile: UserProfileEntity) {
        dao.saveUserProfile(profile)
    }

    suspend fun saveEquipment(equipment: List<UserEquipEntity>) {
        dao.saveUserEquipment(equipment)
    }

    suspend fun checkAndSeedDatabase() {
        if (dao.getItemCount() < 10) {
            AppDatabase.populateDatabase(dao)
        }
    }
}
