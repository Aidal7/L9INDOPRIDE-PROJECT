package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val assetIcon: String = "assets/items/$id.png"
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
