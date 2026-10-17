package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// ==========================================
// LAPIS 1: MASTER / STATIC DATA (l9.db)
// ==========================================

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val grade: String, // Common, Uncommon, Rare, Epic, Legendary, Mythic
    val weaponType: String, // Greatsword, Bow, Staff, Dagger, Crossbow, Sword & Shield, Wand
    val levelReq: Int,
    val attackPower: Int,
    val attackSpeed: Float,
    val criticalRate: Float,
    val accuracy: Int,
    val penetration: Int,
    val weight: Float,
    val description: String,
    val assetIcon: String = "assets/items/$id.png",
    val isEstimatedStat: Boolean = true // Per CATATAN: stat senjata hasil decode bertanda estimasi
)

@Entity(tableName = "monster_drops")
data class MonsterDropEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val itemId: String,
    val monsterName: String,
    val monsterLevel: Int,
    val mapName: String,
    val dropRatePct: String,
    val monsterType: String // Melee, Ranged, Magic
)

@Entity(tableName = "monsters")
data class MonsterEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val level: Int,
    val grade: String, // Common, Elite, Boss, World Boss
    val race: String, // Beast, Spirit, Undead, Demon, Dragon, Human
    val attackType: String, // Melee, Ranged, Magic
    val location: String,
    val hp: Long,
    val hpPercent: Float = 1.0f,
    val codexTpReward: Int,
    val keyDropsSummary: String
)

@Entity(tableName = "maps")
data class MapEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val region: String, // Whisper Woodlands, Tyriosa, Volcano Peak, Cultist Cave, Temporal Ruins, Spire of Trials
    val minLevel: Int,
    val maxLevel: Int,
    val isDungeon: Boolean,
    val resetSchedule: String = "02:00 Daily",
    val monsterCount: Int,
    val description: String
)

@Entity(tableName = "skills")
data class SkillEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val className: String, // Greatsword, Bow, Staff, Dagger, Shield, Common
    val skillType: String, // Active, Passive, Buff, Ultimate
    val grade: String, // Common, Uncommon, Rare, Epic, Legendary, Mythic
    val mpCost: Int,
    val cooldownSec: Float,
    val description: String,
    val damageMultiplier: String
)

@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val chapter: String, // Chapter 1 to 14, Daily, Epic
    val questType: String, // Main Story, Sub Quest, Daily Mission, Epic
    val levelReq: Int,
    val expReward: Long,
    val goldReward: Long,
    val itemRewardName: String,
    val isCompleted: Boolean = false
)

@Entity(tableName = "enchants")
data class EnchantEntity(
    @PrimaryKey
    val id: String,
    val enhanceLevel: Int, // 1 to 20
    val targetGrade: String, // Normal, Rare, Epic, Legendary, Mythic
    val successRatePct: Float,
    val safeLevel: Boolean,
    val goldCost: Long,
    val enhanceStoneCost: Int,
    val destroyRiskPct: Float
)

// ==========================================
// LAPIS 2: ACCOUNT DB (User Profile & Dump)
// ==========================================

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey
    val accountId: String = "acct_main",
    val characterName: String,
    val serverName: String,
    val className: String,
    val level: Int,
    val combatPower: Long,
    val gold: Long,
    val diamond: Long,
    val tpPoints: Long,
    val syncSource: String,
    val completedQuestsCount: Int,
    val completedAchievementsCount: Int,
    val monsterKnowledgeCount: Int
)

@Entity(tableName = "user_equipment")
data class UserEquipEntity(
    @PrimaryKey
    val slotIndex: Int, // 0 to 14 (15 Slots)
    val slotName: String, // Weapon, Helmet, Armor, Gauntlets, Greaves, Ring 1, Ring 2, Earring 1, Earring 2, Necklace, Belt, Cape, Artifact, Relic, Charm
    val itemId: String,
    val itemName: String,
    val grade: String,
    val enhanceLevel: Int,
    val attackBonus: Int = 0,
    val defenseBonus: Int = 0,
    val specialEffect: String = ""
)

data class UserStatEntry(
    val statName: String,
    val statValue: String,
    val category: String // Attack, Defense, Utility, PvP
)
