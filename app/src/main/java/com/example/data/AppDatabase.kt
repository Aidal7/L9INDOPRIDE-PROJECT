package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ItemEntity::class, MonsterDropEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lordNineDao(): LordNineDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "l9.db"
                ).addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.lordNineDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(dao: LordNineDao) {
            val initialItems = listOf(
                ItemEntity(
                    id = "101007",
                    name = "Nerucas Apocalypse Blade",
                    grade = "Mythic",
                    weaponType = "Greatsword",
                    levelReq = 85,
                    attackPower = 840,
                    attackSpeed = 0.95f,
                    criticalRate = 28.5f,
                    accuracy = 320,
                    penetration = 145,
                    weight = 24.0f,
                    description = "Pedang kutukan mitos yang ditempa dari inti neraka Nerucas. Membakar esensi target saat benturan."
                ),
                ItemEntity(
                    id = "101001",
                    name = "Abyssal Dragon Greatsword",
                    grade = "Legendary",
                    weaponType = "Greatsword",
                    levelReq = 75,
                    attackPower = 620,
                    attackSpeed = 1.00f,
                    criticalRate = 22.0f,
                    accuracy = 260,
                    penetration = 95,
                    weight = 22.0f,
                    description = "Senjata legendaris berukir sisik naga purba. Memiliki kekuatan tebasan destruktif."
                ),
                ItemEntity(
                    id = "101002",
                    name = "Celestial Moon Bow",
                    grade = "Epic",
                    weaponType = "Bow",
                    levelReq = 65,
                    attackPower = 480,
                    attackSpeed = 1.25f,
                    criticalRate = 34.0f,
                    accuracy = 295,
                    penetration = 70,
                    weight = 14.5f,
                    description = "Busur epik yang memancarkan pendar cahaya bulan perak. Tembakan panah menembus pertahanan musuh."
                ),
                ItemEntity(
                    id = "101003",
                    name = "Origin Relic Staff",
                    grade = "Epic",
                    weaponType = "Staff",
                    levelReq = 60,
                    attackPower = 495,
                    attackSpeed = 0.90f,
                    criticalRate = 18.0f,
                    accuracy = 240,
                    penetration = 110,
                    weight = 16.0f,
                    description = "Tongkat relik kuno yang memadatkan energi sihir temporal. Meningkatkan daya hancur mantra."
                ),
                ItemEntity(
                    id = "101004",
                    name = "Shadow Fang Dagger",
                    grade = "Rare",
                    weaponType = "Dagger",
                    levelReq = 50,
                    attackPower = 310,
                    attackSpeed = 1.45f,
                    criticalRate = 40.0f,
                    accuracy = 210,
                    penetration = 55,
                    weight = 8.0f,
                    description = "Belati pembunuh bayangan yang dilapisi racun fatal. Serangan beruntun berpeluang kritikal tinggi."
                ),
                ItemEntity(
                    id = "101005",
                    name = "Volcano Siege Crossbow",
                    grade = "Rare",
                    weaponType = "Crossbow",
                    levelReq = 48,
                    attackPower = 335,
                    attackSpeed = 1.10f,
                    criticalRate = 25.0f,
                    accuracy = 225,
                    penetration = 60,
                    weight = 18.0f,
                    description = "Crossbow berat dari batuan vulkanik bersuhu tinggi. Mampu menembus baju zirah besi tebal."
                ),
                ItemEntity(
                    id = "101006",
                    name = "Knight Vanguard Shield & Sword",
                    grade = "Uncommon",
                    weaponType = "Sword & Shield",
                    levelReq = 35,
                    attackPower = 210,
                    attackSpeed = 1.15f,
                    criticalRate = 14.0f,
                    accuracy = 165,
                    penetration = 30,
                    weight = 21.0f,
                    description = "Perlengkapan standar kesatria pelindung kerajaan. Seimbang antara pertahanan dan serangan."
                ),
                ItemEntity(
                    id = "101008",
                    name = "Novice Steel Blade",
                    grade = "Common",
                    weaponType = "Sword & Shield",
                    levelReq = 10,
                    attackPower = 85,
                    attackSpeed = 1.00f,
                    criticalRate = 8.0f,
                    accuracy = 90,
                    penetration = 10,
                    weight = 12.0f,
                    description = "Pedang latihan dasar dari besi tempa standar. Cocok untuk petualang pemula."
                ),
                ItemEntity(
                    id = "101009",
                    name = "Archmage Dragon Orb Staff",
                    grade = "Mythic",
                    weaponType = "Staff",
                    levelReq = 85,
                    attackPower = 810,
                    attackSpeed = 0.90f,
                    criticalRate = 24.0f,
                    accuracy = 310,
                    penetration = 160,
                    weight = 15.0f,
                    description = "Tongkat mitos bermahkota bola naga purba. Melepaskan gelombang sihir meteorik dahsyat."
                ),
                ItemEntity(
                    id = "101010",
                    name = "Nerucas Sunfire Crossbow",
                    grade = "Legendary",
                    weaponType = "Crossbow",
                    levelReq = 75,
                    attackPower = 605,
                    attackSpeed = 1.15f,
                    criticalRate = 26.0f,
                    accuracy = 275,
                    penetration = 90,
                    weight = 19.0f,
                    description = "Crossbow legendaris berdaya tembak pijar matahari neraka Nerucas."
                ),
                ItemEntity(
                    id = "101011",
                    name = "Bloodfang Dual Daggers",
                    grade = "Epic",
                    weaponType = "Dagger",
                    levelReq = 62,
                    attackPower = 440,
                    attackSpeed = 1.50f,
                    criticalRate = 38.0f,
                    accuracy = 270,
                    penetration = 65,
                    weight = 9.0f,
                    description = "Sepasang belati taring berlumur darah vampiric. Setiap tebasan memicu luka pendarahan."
                ),
                ItemEntity(
                    id = "101012",
                    name = "Spire Guardian Greatsword",
                    grade = "Rare",
                    weaponType = "Greatsword",
                    levelReq = 52,
                    attackPower = 350,
                    attackSpeed = 1.00f,
                    criticalRate = 16.0f,
                    accuracy = 190,
                    penetration = 50,
                    weight = 23.0f,
                    description = "Pedang besar pelindung gerbang Spire of Trials. Tebasan kokoh memecah formasi musuh."
                ),
                ItemEntity(
                    id = "101013",
                    name = "Whisper Wood Hunting Bow",
                    grade = "Uncommon",
                    weaponType = "Bow",
                    levelReq = 30,
                    attackPower = 195,
                    attackSpeed = 1.20f,
                    criticalRate = 20.0f,
                    accuracy = 180,
                    penetration = 25,
                    weight = 13.0f,
                    description = "Busur berburu dari kayu hutan Whisper. Ringan dan memiliki tarikan tali cepat."
                ),
                ItemEntity(
                    id = "101014",
                    name = "Apprentice Wooden Staff",
                    grade = "Common",
                    weaponType = "Staff",
                    levelReq = 8,
                    attackPower = 78,
                    attackSpeed = 0.95f,
                    criticalRate = 6.0f,
                    accuracy = 85,
                    penetration = 5,
                    weight = 8.0f,
                    description = "Tongkat kayu latihan untuk murid penyihir pemula."
                )
            )

            val initialDrops = listOf(
                // For 101007 (Nerucas)
                MonsterDropEntity(
                    itemId = "101007",
                    monsterName = "Nerucas World Boss",
                    monsterLevel = 90,
                    mapName = "Volcano Peak (Deep Floor)",
                    dropRatePct = "0.05%",
                    monsterType = "Magic"
                ),
                MonsterDropEntity(
                    itemId = "101007",
                    monsterName = "Spire Overlord Goliath",
                    monsterLevel = 88,
                    mapName = "Spire 100F (Conqueror Hall)",
                    dropRatePct = "0.08%",
                    monsterType = "Melee"
                ),
                // For 101001 (Abyssal Greatsword)
                MonsterDropEntity(
                    itemId = "101001",
                    monsterName = "Dragonbone Shell Turtle",
                    monsterLevel = 80,
                    mapName = "Tyriosa 4F (Chamber of Bones)",
                    dropRatePct = "0.25%",
                    monsterType = "Melee"
                ),
                MonsterDropEntity(
                    itemId = "101001",
                    monsterName = "Tyriosa Sentinel Guardian",
                    monsterLevel = 78,
                    mapName = "Tyriosa 3F (Ancient Gate)",
                    dropRatePct = "0.40%",
                    monsterType = "Ranged"
                ),
                MonsterDropEntity(
                    itemId = "101001",
                    monsterName = "Abyssal Behemoth",
                    monsterLevel = 82,
                    mapName = "Desolate Crypt B2",
                    dropRatePct = "0.32%",
                    monsterType = "Melee"
                ),
                // For 101002 (Moon Bow)
                MonsterDropEntity(
                    itemId = "101002",
                    monsterName = "Tyriosa Elite Archer",
                    monsterLevel = 72,
                    mapName = "Tyriosa 2F (Echoing Halls)",
                    dropRatePct = "1.15%",
                    monsterType = "Ranged"
                ),
                MonsterDropEntity(
                    itemId = "101002",
                    monsterName = "Forest Phantom Stalker",
                    monsterLevel = 68,
                    mapName = "Whisper Woodlands",
                    dropRatePct = "1.80%",
                    monsterType = "Magic"
                ),
                // For 101003 (Relic Staff)
                MonsterDropEntity(
                    itemId = "101003",
                    monsterName = "Ancient Crystal Golem",
                    monsterLevel = 65,
                    mapName = "Crystal Cavern 3F",
                    dropRatePct = "0.95%",
                    monsterType = "Magic"
                ),
                MonsterDropEntity(
                    itemId = "101003",
                    monsterName = "Spire High Sorcerer",
                    monsterLevel = 62,
                    mapName = "Spire 60F",
                    dropRatePct = "1.45%",
                    monsterType = "Magic"
                ),
                // For 101004 (Shadow Fang)
                MonsterDropEntity(
                    itemId = "101004",
                    monsterName = "Desert Shade Assassin",
                    monsterLevel = 55,
                    mapName = "Scorched Dune Ruins",
                    dropRatePct = "2.30%",
                    monsterType = "Melee"
                ),
                MonsterDropEntity(
                    itemId = "101004",
                    monsterName = "Dune Scorpion Raider",
                    monsterLevel = 52,
                    mapName = "Scorched Dune Outskirts",
                    dropRatePct = "3.10%",
                    monsterType = "Melee"
                ),
                // For 101005 (Volcano Crossbow)
                MonsterDropEntity(
                    itemId = "101005",
                    monsterName = "Volcano Salamander",
                    monsterLevel = 50,
                    mapName = "Volcano Basin (Lower Ridge)",
                    dropRatePct = "2.80%",
                    monsterType = "Ranged"
                ),
                MonsterDropEntity(
                    itemId = "101005",
                    monsterName = "Flame Hound Alpha",
                    monsterLevel = 47,
                    mapName = "Volcano Basin (Lava Path)",
                    dropRatePct = "3.50%",
                    monsterType = "Melee"
                ),
                // For 101006 (Knight Vanguard)
                MonsterDropEntity(
                    itemId = "101006",
                    monsterName = "Royal Guard Deserter",
                    monsterLevel = 38,
                    mapName = "Outer Bastion Ruins",
                    dropRatePct = "5.20%",
                    monsterType = "Melee"
                ),
                MonsterDropEntity(
                    itemId = "101006",
                    monsterName = "Wild Boar Chieftain",
                    monsterLevel = 35,
                    mapName = "Sunken Valley Marsh",
                    dropRatePct = "6.80%",
                    monsterType = "Melee"
                ),
                // For 101008 (Novice Blade)
                MonsterDropEntity(
                    itemId = "101008",
                    monsterName = "Mine Goblin Worker",
                    monsterLevel = 12,
                    mapName = "Starter Mines 1F",
                    dropRatePct = "12.50%",
                    monsterType = "Melee"
                ),
                MonsterDropEntity(
                    itemId = "101008",
                    monsterName = "Field Wolf Prowler",
                    monsterLevel = 10,
                    mapName = "Green Plains South",
                    dropRatePct = "15.00%",
                    monsterType = "Melee"
                )
            )

            dao.insertItems(initialItems)
            dao.insertDrops(initialDrops)
        }
    }
}
