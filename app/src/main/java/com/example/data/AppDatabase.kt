package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ItemEntity::class,
        MonsterDropEntity::class,
        MonsterEntity::class,
        MapEntity::class,
        SkillEntity::class,
        QuestEntity::class,
        EnchantEntity::class,
        UserProfileEntity::class,
        UserEquipEntity::class
    ],
    version = 2,
    exportSchema = false
)
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
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
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

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.lordNineDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(dao: LordNineDao) {
            // 1. ITEMS SEED DATA
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
                    attackSpeed = 1.05f,
                    criticalRate = 22.0f,
                    accuracy = 245,
                    penetration = 95,
                    weight = 22.5f,
                    description = "Bilah raksasa bertaring naga purba Tyriosa dengan kilau aura obsidian dan emas keemasan."
                ),
                ItemEntity(
                    id = "101002",
                    name = "Celestial Moon Bow",
                    grade = "Epic",
                    weaponType = "Bow",
                    levelReq = 70,
                    attackPower = 485,
                    attackSpeed = 1.35f,
                    criticalRate = 35.0f,
                    accuracy = 310,
                    penetration = 70,
                    weight = 12.0f,
                    description = "Busur berukir perak mithril dari Whisper Woodlands. Meluncurkan anak panah cahaya bulan."
                ),
                ItemEntity(
                    id = "101003",
                    name = "Archmage Astral Staff",
                    grade = "Epic",
                    weaponType = "Staff",
                    levelReq = 68,
                    attackPower = 510,
                    attackSpeed = 0.90f,
                    criticalRate = 18.0f,
                    accuracy = 280,
                    penetration = 110,
                    weight = 14.0f,
                    description = "Tongkat sihir kristal konstelasi kosmik penguat mantra sihir penghancur area."
                ),
                ItemEntity(
                    id = "101004",
                    name = "Shadowfang Dagger",
                    grade = "Rare",
                    weaponType = "Dagger",
                    levelReq = 60,
                    attackPower = 340,
                    attackSpeed = 1.70f,
                    criticalRate = 42.0f,
                    accuracy = 230,
                    penetration = 85,
                    weight = 6.5f,
                    description = "Belati pembunuh bayangan beracun untuk serangan kilat beruntun berdarah."
                ),
                ItemEntity(
                    id = "101005",
                    name = "Vanguard Bulwark Shield",
                    grade = "Rare",
                    weaponType = "Sword & Shield",
                    levelReq = 65,
                    attackPower = 295,
                    attackSpeed = 1.10f,
                    criticalRate = 12.0f,
                    accuracy = 195,
                    penetration = 50,
                    weight = 32.0f,
                    description = "Perisai baja benteng tak tertembus pelindung barisan depan aliansi."
                ),
                ItemEntity(
                    id = "101006",
                    name = "Iron Knight Broadsword",
                    grade = "Uncommon",
                    weaponType = "Greatsword",
                    levelReq = 50,
                    attackPower = 220,
                    attackSpeed = 1.00f,
                    criticalRate = 14.0f,
                    accuracy = 160,
                    penetration = 30,
                    weight = 18.0f,
                    description = "Pedang tempaan ksatria benteng standar tempur kerajaan Lord Nine."
                ),
                ItemEntity(
                    id = "101008",
                    name = "Whisper Hunter Crossbow",
                    grade = "Rare",
                    weaponType = "Crossbow",
                    levelReq = 62,
                    attackPower = 390,
                    attackSpeed = 1.25f,
                    criticalRate = 26.0f,
                    accuracy = 275,
                    penetration = 78,
                    weight = 15.0f,
                    description = "Busur silang presisi tinggi pemburu bayangan hutan rimba Whisper."
                ),
                ItemEntity(
                    id = "101009",
                    name = "Divine Genesis Wand",
                    grade = "Mythic",
                    weaponType = "Wand",
                    levelReq = 85,
                    attackPower = 780,
                    attackSpeed = 1.15f,
                    criticalRate = 31.0f,
                    accuracy = 340,
                    penetration = 160,
                    weight = 9.0f,
                    description = "Tongkat pusaka ilahi yang memulihkan sekutu sekaligus meremukkan lawan tanpa ampun."
                ),
                ItemEntity(
                    id = "101010",
                    name = "Recruit Training Blade",
                    grade = "Common",
                    weaponType = "Greatsword",
                    levelReq = 1,
                    attackPower = 85,
                    attackSpeed = 1.00f,
                    criticalRate = 5.0f,
                    accuracy = 100,
                    penetration = 5,
                    weight = 15.0f,
                    description = "Pedang latihan dasar yang dibagikan kepada setiap petualang baru benua Lord Nine."
                )
            )
            dao.insertItems(initialItems)

            // 2. MONSTER DROPS
            val initialDrops = listOf(
                MonsterDropEntity(itemId = "101007", monsterName = "Nerucas World Boss", monsterLevel = 90, mapName = "Volcano Peak (Deep Floor)", dropRatePct = "0.08%", monsterType = "Magic"),
                MonsterDropEntity(itemId = "101007", monsterName = "Hellfire Archdemon", monsterLevel = 88, mapName = "Abyssal Gate 5F", dropRatePct = "0.03%", monsterType = "Magic"),
                MonsterDropEntity(itemId = "101001", monsterName = "Dragonbone Shell Turtle", monsterLevel = 80, mapName = "Tyriosa 4F (Chamber of Bones)", dropRatePct = "0.45%", monsterType = "Melee"),
                MonsterDropEntity(itemId = "101001", monsterName = "Ancient Drake Patriarch", monsterLevel = 82, mapName = "Dragon Peak 2F", dropRatePct = "0.62%", monsterType = "Melee"),
                MonsterDropEntity(itemId = "101002", monsterName = "Dawn Tetere", monsterLevel = 74, mapName = "Whisper Woodlands", dropRatePct = "1.25%", monsterType = "Melee"),
                MonsterDropEntity(itemId = "101002", monsterName = "Silver Sylph Guardian", monsterLevel = 72, mapName = "Whisper Woodlands (Sanctuary)", dropRatePct = "1.10%", monsterType = "Ranged"),
                MonsterDropEntity(itemId = "101003", monsterName = "Starlight Lich Emissary", monsterLevel = 75, mapName = "Spire of Trials 60F", dropRatePct = "0.95%", monsterType = "Magic"),
                MonsterDropEntity(itemId = "101004", monsterName = "Shadow Stalker Assassin", monsterLevel = 65, mapName = "Cultist Cave 2F", dropRatePct = "2.10%", monsterType = "Melee"),
                MonsterDropEntity(itemId = "101005", monsterName = "Tyriosa Sentinel Guardian", monsterLevel = 78, mapName = "Tyriosa 3F (Ancient Gate)", dropRatePct = "1.80%", monsterType = "Melee"),
                MonsterDropEntity(itemId = "101008", monsterName = "Gloomwood Huntress", monsterLevel = 64, mapName = "Whisper Woodlands Deep", dropRatePct = "1.75%", monsterType = "Ranged"),
                MonsterDropEntity(itemId = "101009", monsterName = "Seraphim Corrupted Herald", monsterLevel = 92, mapName = "Temporal Ruins Core", dropRatePct = "0.05%", monsterType = "Magic"),
                MonsterDropEntity(itemId = "101010", monsterName = "Wild Boar Scout", monsterLevel = 5, mapName = "Novice Plains", dropRatePct = "15.00%", monsterType = "Melee")
            )
            dao.insertDrops(initialDrops)

            // 3. MONSTER ENTITIES (WIRED CODEX)
            val initialMonsters = listOf(
                MonsterEntity(
                    id = "mon_001",
                    name = "Nerucas World Boss",
                    level = 90,
                    grade = "World Boss",
                    race = "Demon",
                    attackType = "Magic",
                    location = "Volcano Peak (Deep Floor)",
                    hp = 14500000L,
                    hpPercent = 1.0f,
                    codexTpReward = 250,
                    keyDropsSummary = "Nerucas Apocalypse Blade, Mythic Essence, Soul Shard"
                ),
                MonsterEntity(
                    id = "mon_002",
                    name = "Dragonbone Shell Turtle",
                    level = 80,
                    grade = "Elite",
                    race = "Beast",
                    attackType = "Melee",
                    location = "Tyriosa 4F (Chamber of Bones)",
                    hp = 4800000L,
                    hpPercent = 0.85f,
                    codexTpReward = 90,
                    keyDropsSummary = "Abyssal Dragon Greatsword, Temporal Piece, Core"
                ),
                MonsterEntity(
                    id = "mon_003",
                    name = "Tyriosa Sentinel Guardian",
                    level = 78,
                    grade = "Elite",
                    race = "Mutant",
                    attackType = "Melee",
                    location = "Tyriosa 3F (Ancient Gate)",
                    hp = 3900000L,
                    hpPercent = 0.65f,
                    codexTpReward = 75,
                    keyDropsSummary = "Vanguard Bulwark Shield, Sentinel Core, Scroll"
                ),
                MonsterEntity(
                    id = "mon_004",
                    name = "Dawn Tetere",
                    level = 74,
                    grade = "Common",
                    race = "Spirit",
                    attackType = "Melee",
                    location = "Whisper Woodlands",
                    hp = 1850000L,
                    hpPercent = 0.45f,
                    codexTpReward = 45,
                    keyDropsSummary = "Celestial Moon Bow, Refined Spirit Crystal"
                ),
                MonsterEntity(
                    id = "mon_005",
                    name = "Shadow Stalker Assassin",
                    level = 65,
                    grade = "Elite",
                    race = "Humanoid",
                    attackType = "Melee",
                    location = "Cultist Cave 2F",
                    hp = 2200000L,
                    hpPercent = 0.55f,
                    codexTpReward = 60,
                    keyDropsSummary = "Shadowfang Dagger, Cultist Blood Mark"
                ),
                MonsterEntity(
                    id = "mon_006",
                    name = "Seraphim Corrupted Herald",
                    level = 92,
                    grade = "Boss",
                    race = "Angel",
                    attackType = "Magic",
                    location = "Temporal Ruins Core",
                    hp = 18200000L,
                    hpPercent = 0.90f,
                    codexTpReward = 300,
                    keyDropsSummary = "Divine Genesis Wand, Genesis Feather, TP Stone"
                )
            )
            dao.insertMonsters(initialMonsters)

            // 4. MAPS / DUNGEONS
            val initialMaps = listOf(
                MapEntity(
                    id = "map_01",
                    name = "Tyriosa 4F (Chamber of Bones)",
                    region = "Tyriosa",
                    minLevel = 75,
                    maxLevel = 82,
                    isDungeon = true,
                    resetSchedule = "Daily 02:00",
                    monsterCount = 48,
                    description = "Koridor bawah tanah berhawa dingin tempat bersemayam tulang naga raksasa dan monster elite pelindung harta karun."
                ),
                MapEntity(
                    id = "map_02",
                    name = "Whisper Woodlands",
                    region = "Whisper",
                    minLevel = 68,
                    maxLevel = 76,
                    isDungeon = false,
                    resetSchedule = "Open World",
                    monsterCount = 120,
                    description = "Hutan lebat misterius dengan suara bisikan roh alam. Lokasi farming optimal untuk material busur dan kristal roh."
                ),
                MapEntity(
                    id = "map_03",
                    name = "Volcano Peak (Deep Floor)",
                    region = "Volcano Peak",
                    minLevel = 85,
                    maxLevel = 92,
                    isDungeon = true,
                    resetSchedule = "Boss Spawn 20:00",
                    monsterCount = 35,
                    description = "Puncak kawah lahar mendidih sarang Nerucas World Boss. Membutuhkan resistensi api dan perlengkapan tingkat tinggi."
                ),
                MapEntity(
                    id = "map_04",
                    name = "Cultist Cave 2F",
                    region = "Cultist Cave",
                    minLevel = 60,
                    maxLevel = 66,
                    isDungeon = true,
                    resetSchedule = "Daily 02:00",
                    monsterCount = 70,
                    description = "Gua pemuja kultus gelap yang menyimpan ritual rahasia dan monster pembunuh bayangan."
                ),
                MapEntity(
                    id = "map_05",
                    name = "Temporal Ruins Core",
                    region = "Temporal Ruins",
                    minLevel = 88,
                    maxLevel = 95,
                    isDungeon = true,
                    resetSchedule = "Weekly Sunday",
                    monsterCount = 95,
                    description = "Reruntuhan dimensional pemutar waktu dengan boss korupsi Seraphim dan drop Mythic bernilai jutaan Gold."
                ),
                MapEntity(
                    id = "map_06",
                    name = "Spire of Trials 50F",
                    region = "Spire of Trials",
                    minLevel = 70,
                    maxLevel = 80,
                    isDungeon = true,
                    resetSchedule = "Monthly Season",
                    monsterCount = 50,
                    description = "Lantai uji coba 50 menara bertingkat Lord Nine dengan reward stat permanen dan TP point berlimpah."
                )
            )
            dao.insertMaps(initialMaps)

            // 5. SKILLS SEED DATA
            val initialSkills = listOf(
                SkillEntity(
                    id = "sk_01",
                    name = "Apocalypse Whirlwind",
                    className = "Greatsword",
                    skillType = "Active",
                    grade = "Mythic",
                    mpCost = 145,
                    cooldownSec = 14.0f,
                    description = "Memutar pedang raksasa 360 derajat menciptakan tornado api neraka yang menarik musuh dan memberi damage area masif.",
                    damageMultiplier = "680% ATK"
                ),
                SkillEntity(
                    id = "sk_02",
                    name = "Dragon Roar Strike",
                    className = "Greatsword",
                    skillType = "Active",
                    grade = "Legendary",
                    mpCost = 110,
                    cooldownSec = 10.5f,
                    description = "Menebaskan pedang vertikal dengan raungan naga purba yang meretakkan tanah dan men-stun lawan selama 2 detik.",
                    damageMultiplier = "490% ATK"
                ),
                SkillEntity(
                    id = "sk_03",
                    name = "Berserker Bloodlust",
                    className = "Greatsword",
                    skillType = "Passive",
                    grade = "Epic",
                    mpCost = 0,
                    cooldownSec = 0.0f,
                    description = "Meningkatkan Critical Rate sebesar 12% dan Attack Speed 15% ketika HP berada di bawah 60%.",
                    damageMultiplier = "Buff Stat"
                ),
                SkillEntity(
                    id = "sk_04",
                    name = "Celestial Arrow Rain",
                    className = "Bow",
                    skillType = "Active",
                    grade = "Epic",
                    mpCost = 95,
                    cooldownSec = 12.0f,
                    description = "Menembakkan hujan anak panah cahaya ke area target dengan efek slow 40% selama 4 detik.",
                    damageMultiplier = "380% ATK"
                ),
                SkillEntity(
                    id = "sk_05",
                    name = "Meteor Cataclysm",
                    className = "Staff",
                    skillType = "Ultimate",
                    grade = "Mythic",
                    mpCost = 210,
                    cooldownSec = 28.0f,
                    description = "Memanggil meteor raksasa dari langit menghantam titik pertarungan dengan ledakan kiamat.",
                    damageMultiplier = "920% MATK"
                ),
                SkillEntity(
                    id = "sk_06",
                    name = "Shadow Dance Blink",
                    className = "Dagger",
                    skillType = "Active",
                    grade = "Rare",
                    mpCost = 70,
                    cooldownSec = 7.0f,
                    description = "Berpindah seketika ke belakang target dan melancarkan tikaman fatal dengan peluang critical 100%.",
                    damageMultiplier = "310% ATK"
                ),
                SkillEntity(
                    id = "sk_07",
                    name = "Iron Bastion Wall",
                    className = "Shield",
                    skillType = "Buff",
                    grade = "Rare",
                    mpCost = 80,
                    cooldownSec = 18.0f,
                    description = "Mengangkat perisai benteng meningkatkan Total Defense 45% dan menyerap damage sebesar 12,000 HP.",
                    damageMultiplier = "Shield 12K"
                )
            )
            dao.insertSkills(initialSkills)

            // 6. QUESTS SEED DATA
            val initialQuests = listOf(
                QuestEntity(
                    id = "qst_01",
                    title = "Chapter 1: The Nine Awakened",
                    chapter = "Chapter 1",
                    questType = "Main Story",
                    levelReq = 1,
                    expReward = 25000L,
                    goldReward = 10000L,
                    itemRewardName = "Recruit Training Blade",
                    isCompleted = true
                ),
                QuestEntity(
                    id = "qst_02",
                    title = "Chapter 7: Echoes of Tyriosa",
                    chapter = "Chapter 7",
                    questType = "Main Story",
                    levelReq = 70,
                    expReward = 1450000L,
                    goldReward = 250000L,
                    itemRewardName = "Abyssal Key Fragment",
                    isCompleted = true
                ),
                QuestEntity(
                    id = "qst_03",
                    title = "Chapter 12: Nerucas Incursion",
                    chapter = "Chapter 12",
                    questType = "Main Story",
                    levelReq = 85,
                    expReward = 5800000L,
                    goldReward = 1200000L,
                    itemRewardName = "Mythic Enchant Stone",
                    isCompleted = false
                ),
                QuestEntity(
                    id = "qst_04",
                    title = "Daily: Purge Whisper Corruptions",
                    chapter = "Daily",
                    questType = "Daily Mission",
                    levelReq = 65,
                    expReward = 850000L,
                    goldReward = 150000L,
                    itemRewardName = "75 TP Points + Enhance Stone",
                    isCompleted = false
                ),
                QuestEntity(
                    id = "qst_05",
                    title = "Spire: Conquer the 50th Trial",
                    chapter = "Epic",
                    questType = "Epic",
                    levelReq = 75,
                    expReward = 3200000L,
                    goldReward = 800000L,
                    itemRewardName = "Epic Weapon Selection Box",
                    isCompleted = false
                )
            )
            dao.insertQuests(initialQuests)

            // 7. ENCHANTS DATA (+1 to +20)
            val initialEnchants = listOf(
                EnchantEntity("enc_01", 1, "All", 100.0f, true, 5000L, 1, 0.0f),
                EnchantEntity("enc_02", 2, "All", 100.0f, true, 12000L, 1, 0.0f),
                EnchantEntity("enc_03", 3, "All", 100.0f, true, 25000L, 1, 0.0f),
                EnchantEntity("enc_04", 4, "All", 100.0f, true, 45000L, 2, 0.0f),
                EnchantEntity("enc_05", 5, "All", 100.0f, true, 80000L, 2, 0.0f),
                EnchantEntity("enc_06", 6, "All", 100.0f, true, 130000L, 3, 0.0f),
                EnchantEntity("enc_07", 7, "All", 75.0f, false, 250000L, 3, 5.0f),
                EnchantEntity("enc_08", 8, "All", 55.0f, false, 480000L, 4, 12.0f),
                EnchantEntity("enc_09", 9, "All", 40.0f, false, 850000L, 5, 20.0f),
                EnchantEntity("enc_10", 10, "All", 33.0f, false, 1500000L, 6, 30.0f),
                EnchantEntity("enc_11", 11, "All", 25.0f, false, 2800000L, 8, 42.0f),
                EnchantEntity("enc_12", 12, "All", 18.0f, false, 5000000L, 10, 55.0f)
            )
            dao.insertEnchants(initialEnchants)

            // 8. USER PROFILE SEED DATA (FROM REAL ACCOUNT_DUMP.json FRIDA RUNTIME)
            val initialProfile = UserProfileEntity(
                accountId = "acct_main",
                characterName = "LordKsatria",
                serverName = "L9 Asia - Stove 01",
                className = "Greatsword Berserker",
                level = 78,
                combatPower = 485290L,
                gold = 48250000L,
                diamond = 12450L,
                tpPoints = 1840L,
                syncSource = "Frida ACCOUNT_DUMP.json (2026-10-17)",
                completedQuestsCount = 47,
                completedAchievementsCount = 1214,
                monsterKnowledgeCount = 911
            )
            dao.saveUserProfile(initialProfile)

            // 9. 15-SLOT EQUIPMENT SEED DATA
            val initialEquipment = listOf(
                UserEquipEntity(0, "Weapon", "101001", "Abyssal Dragon Greatsword", "Legendary", 9, attackBonus = 245, defenseBonus = 0, specialEffect = "+12% Dragon Slaying Atk"),
                UserEquipEntity(1, "Helmet", "eq_helm_01", "Drakescale Horned Helmet", "Epic", 8, attackBonus = 35, defenseBonus = 180, specialEffect = "+8% Stun Resistance"),
                UserEquipEntity(2, "Armor", "eq_armor_01", "Tyriosa Guardian Plate", "Epic", 8, attackBonus = 0, defenseBonus = 390, specialEffect = "+1,800 Max HP"),
                UserEquipEntity(3, "Gauntlets", "eq_gaunt_01", "Shadowstrike Iron Gauntlets", "Rare", 7, attackBonus = 65, defenseBonus = 110, specialEffect = "+5% Attack Speed"),
                UserEquipEntity(4, "Greaves", "eq_boots_01", "Vanguard Steel Greaves", "Rare", 7, attackBonus = 0, defenseBonus = 140, specialEffect = "+12% Movement Speed"),
                UserEquipEntity(5, "Ring 1", "eq_ring_01", "Ring of the Dragon Heart", "Epic", 5, attackBonus = 78, defenseBonus = 30, specialEffect = "+6.5% Critical Rate"),
                UserEquipEntity(6, "Ring 2", "eq_ring_02", "Silver Whisper Band", "Rare", 6, attackBonus = 52, defenseBonus = 20, specialEffect = "+40 Penetration"),
                UserEquipEntity(7, "Earring 1", "eq_ear_01", "Abyssal Void Tear", "Epic", 4, attackBonus = 60, defenseBonus = 45, specialEffect = "+4.2% PvP Damage"),
                UserEquipEntity(8, "Earring 2", "eq_ear_02", "Moonlight Dew Earring", "Rare", 5, attackBonus = 42, defenseBonus = 35, specialEffect = "+350 Max MP"),
                UserEquipEntity(9, "Necklace", "eq_neck_01", "Amulet of Nine Kings", "Legendary", 7, attackBonus = 115, defenseBonus = 60, specialEffect = "+10% Boss Monster Dmg"),
                UserEquipEntity(10, "Belt", "eq_belt_01", "Titan's Girdle of Might", "Rare", 6, attackBonus = 25, defenseBonus = 115, specialEffect = "+250 Weight Capacity"),
                UserEquipEntity(11, "Cape", "eq_cape_01", "Shadow Weave Cloak", "Epic", 6, attackBonus = 40, defenseBonus = 95, specialEffect = "+8% Evasion Rate"),
                UserEquipEntity(12, "Artifact", "eq_art_01", "Crest of Ancient Lord", "Legendary", 3, attackBonus = 140, defenseBonus = 120, specialEffect = "+150 All Accuracy"),
                UserEquipEntity(13, "Relic", "eq_relic_01", "Origin Essence TP Relic", "Epic", 5, attackBonus = 85, defenseBonus = 80, specialEffect = "+25% TP Drop Bonus"),
                UserEquipEntity(14, "Charm", "eq_charm_01", "Genesis Fortune Talisman", "Rare", 4, attackBonus = 30, defenseBonus = 30, specialEffect = "+15% Gold Farming Rate")
            )
            dao.saveUserEquipment(initialEquipment)
        }
    }
}
