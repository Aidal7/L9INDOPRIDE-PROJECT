package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LordNineDao {
    // ==========================================
    // ITEMS & DROPS
    // ==========================================
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

    // ==========================================
    // MONSTERS (CODEX)
    // ==========================================
    @Query("SELECT * FROM monsters ORDER BY level DESC, name ASC")
    fun getAllMonsters(): Flow<List<MonsterEntity>>

    @Query("SELECT * FROM monsters WHERE location LIKE '%' || :mapName || '%' ORDER BY level DESC")
    fun getMonstersByMap(mapName: String): Flow<List<MonsterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonsters(monsters: List<MonsterEntity>)

    // ==========================================
    // MAPS / DUNGEONS
    // ==========================================
    @Query("SELECT * FROM maps ORDER BY minLevel ASC, name ASC")
    fun getAllMaps(): Flow<List<MapEntity>>

    @Query("SELECT * FROM maps WHERE region = :region ORDER BY minLevel ASC")
    fun getMapsByRegion(region: String): Flow<List<MapEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaps(maps: List<MapEntity>)

    // ==========================================
    // SKILLS
    // ==========================================
    @Query("SELECT * FROM skills ORDER BY className ASC, name ASC")
    fun getAllSkills(): Flow<List<SkillEntity>>

    @Query("SELECT * FROM skills WHERE className = :className ORDER BY grade ASC")
    fun getSkillsByClass(className: String): Flow<List<SkillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkills(skills: List<SkillEntity>)

    // ==========================================
    // QUESTS
    // ==========================================
    @Query("SELECT * FROM quests ORDER BY levelReq ASC, title ASC")
    fun getAllQuests(): Flow<List<QuestEntity>>

    @Query("UPDATE quests SET isCompleted = :completed WHERE id = :questId")
    suspend fun setQuestCompleted(questId: String, completed: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuests(quests: List<QuestEntity>)

    // ==========================================
    // ENCHANTS
    // ==========================================
    @Query("SELECT * FROM enchants ORDER BY enhanceLevel ASC")
    fun getAllEnchants(): Flow<List<EnchantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnchants(enchants: List<EnchantEntity>)

    // ==========================================
    // ACCOUNT DB (USER PROFILE & 15-SLOT EQUIP)
    // ==========================================
    @Query("SELECT * FROM user_profiles WHERE accountId = :accountId LIMIT 1")
    fun getUserProfile(accountId: String = "acct_main"): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)

    @Query("SELECT * FROM user_equipment ORDER BY slotIndex ASC")
    fun getUserEquipment(): Flow<List<UserEquipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserEquipment(equipment: List<UserEquipEntity>)
}
