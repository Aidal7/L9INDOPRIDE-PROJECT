package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.EnchantEntity
import com.example.data.ItemEntity
import com.example.data.ItemRepository
import com.example.data.MapEntity
import com.example.data.MonsterDropEntity
import com.example.data.MonsterEntity
import com.example.data.QuestEntity
import com.example.data.SkillEntity
import com.example.data.UserEquipEntity
import com.example.data.UserProfileEntity
import com.example.data.UserStatEntry
import com.example.ui.components.LordNineMenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ItemSortOption(val label: String, val shortLabel: String) {
    GRADE_DESC("Rarity: Mitos ke Umum", "Grade ↓"),
    GRADE_ASC("Rarity: Umum ke Mitos", "Grade ↑"),
    ATK_DESC("Serangan: Tertinggi ke Terendah", "ATK ↓"),
    ATK_ASC("Serangan: Terendah ke Tertinggi", "ATK ↑"),
    LEVEL_DESC("Level Syarat: Tertinggi", "Level ↓"),
    LEVEL_ASC("Level Syarat: Terendah", "Level ↑"),
    CRIT_DESC("Tingkat Kritikal: Tertinggi", "Crit ↓"),
    NAME_ASC("Nama Senjata: A ke Z", "Nama A-Z")
}

data class FilterSortParams(
    val query: String,
    val weapon: String,
    val grade: String,
    val sortOption: ItemSortOption,
    val onlyFavorites: Boolean,
    val favoriteIds: Set<String>,
    val mapFilter: String?
)

private data class Tuple4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ItemRepository

    // Search and Filters
    val searchQuery = MutableStateFlow("")
    val selectedWeaponFilter = MutableStateFlow("All")
    val selectedGradeFilter = MutableStateFlow("All")
    val selectedSortOption = MutableStateFlow(ItemSortOption.GRADE_DESC)
    val onlyFavoritesFilter = MutableStateFlow(false)
    val selectedMapFilter = MutableStateFlow<String?>(null)

    // Navigation and Panels (0: Hub, 1: Item Log, 2: Combat/Maps, 3: Skills/Quests, 4: Profil Akun)
    val currentTab = MutableStateFlow(0)
    val isFantasyBgActive = MutableStateFlow(true)
    val isSidebarNavOpen = MutableStateFlow(false)
    val favoriteItemIds = MutableStateFlow<Set<String>>(setOf("101007", "101001", "101009"))

    // Sub-filters for new tabs
    val selectedSkillClassFilter = MutableStateFlow("All")
    val selectedQuestTypeFilter = MutableStateFlow("All")
    val selectedMapRegionFilter = MutableStateFlow("All")

    private val _selectedItem = MutableStateFlow<ItemEntity?>(null)
    val selectedItem: StateFlow<ItemEntity?> = _selectedItem.asStateFlow()

    private val _showFlutterCodeDialog = MutableStateFlow(false)
    val showFlutterCodeDialog: StateFlow<Boolean> = _showFlutterCodeDialog.asStateFlow()

    private val _activeMenuItem = MutableStateFlow<LordNineMenuItem?>(null)
    val activeMenuItem: StateFlow<LordNineMenuItem?> = _activeMenuItem.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = ItemRepository(database.lordNineDao())
        viewModelScope.launch {
            repository.checkAndSeedDatabase()
        }
    }

    private fun gradeRank(grade: String): Int {
        return when (grade.lowercase()) {
            "mythic", "mitos" -> 6
            "legendary", "legendaris" -> 5
            "epic", "epik" -> 4
            "rare", "langka" -> 3
            "uncommon" -> 2
            else -> 1
        }
    }

    // 1. ITEMS & FILTERING
    val allItems: StateFlow<List<ItemEntity>> = repository.allItems
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val filterParams = combine(
        combine(searchQuery, selectedWeaponFilter, selectedGradeFilter) { q, w, g -> Triple(q, w, g) },
        combine(selectedSortOption, onlyFavoritesFilter, favoriteItemIds, selectedMapFilter) { s, fav, ids, map ->
            Tuple4(s, fav, ids, map)
        }
    ) { (q, w, g), (s, fav, ids, map) ->
        FilterSortParams(q, w, g, s, fav, ids, map)
    }

    val filteredItems: StateFlow<List<ItemEntity>> = combine(
        repository.allItems,
        filterParams
    ) { items, params ->
        val filtered = items.filter { item ->
            val matchesQuery = params.query.isBlank() ||
                item.name.contains(params.query, ignoreCase = true) ||
                item.weaponType.contains(params.query, ignoreCase = true) ||
                item.grade.contains(params.query, ignoreCase = true)

            val matchesWeapon = params.weapon == "All" || item.weaponType.equals(params.weapon, ignoreCase = true)
            val matchesGrade = params.grade == "All" || item.grade.equals(params.grade, ignoreCase = true)
            val matchesFav = !params.onlyFavorites || params.favoriteIds.contains(item.id)

            matchesQuery && matchesWeapon && matchesGrade && matchesFav
        }

        when (params.sortOption) {
            ItemSortOption.GRADE_DESC -> filtered.sortedWith(
                compareByDescending<ItemEntity> { gradeRank(it.grade) }
                    .thenByDescending { it.attackPower }
                    .thenBy { it.name }
            )
            ItemSortOption.GRADE_ASC -> filtered.sortedWith(
                compareBy<ItemEntity> { gradeRank(it.grade) }
                    .thenBy { it.attackPower }
                    .thenBy { it.name }
            )
            ItemSortOption.ATK_DESC -> filtered.sortedByDescending { it.attackPower }
            ItemSortOption.ATK_ASC -> filtered.sortedBy { it.attackPower }
            ItemSortOption.LEVEL_DESC -> filtered.sortedByDescending { it.levelReq }
            ItemSortOption.LEVEL_ASC -> filtered.sortedBy { it.levelReq }
            ItemSortOption.CRIT_DESC -> filtered.sortedByDescending { it.criticalRate }
            ItemSortOption.NAME_ASC -> filtered.sortedBy { it.name }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedItemDrops: StateFlow<List<MonsterDropEntity>> = _selectedItem.flatMapLatest { item ->
        if (item == null) flowOf(emptyList())
        else repository.getDropsForItem(item.id)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. MONSTERS (WIRED CODEX)
    val allMonsters: StateFlow<List<MonsterEntity>> = repository.allMonsters
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // 3. MAPS & REGIONS
    val allMaps: StateFlow<List<MapEntity>> = repository.allMaps
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filteredMaps: StateFlow<List<MapEntity>> = combine(
        allMaps,
        selectedMapRegionFilter,
        searchQuery
    ) { maps, region, query ->
        maps.filter { map ->
            val matchesRegion = region == "All" || map.region.contains(region, ignoreCase = true)
            val matchesQuery = query.isBlank() ||
                map.name.contains(query, ignoreCase = true) ||
                map.region.contains(query, ignoreCase = true)
            matchesRegion && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // 4. SKILLS & CLASS
    val allSkills: StateFlow<List<SkillEntity>> = repository.allSkills
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filteredSkills: StateFlow<List<SkillEntity>> = combine(
        allSkills,
        selectedSkillClassFilter,
        searchQuery
    ) { skills, className, query ->
        skills.filter { skill ->
            val matchesClass = className == "All" || skill.className.equals(className, ignoreCase = true)
            val matchesQuery = query.isBlank() ||
                skill.name.contains(query, ignoreCase = true) ||
                skill.description.contains(query, ignoreCase = true)
            matchesClass && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // 5. QUESTS & MISSIONS
    val allQuests: StateFlow<List<QuestEntity>> = repository.allQuests
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filteredQuests: StateFlow<List<QuestEntity>> = combine(
        allQuests,
        selectedQuestTypeFilter,
        searchQuery
    ) { quests, questType, query ->
        quests.filter { quest ->
            val matchesType = questType == "All" || quest.questType.equals(questType, ignoreCase = true)
            val matchesQuery = query.isBlank() ||
                quest.title.contains(query, ignoreCase = true) ||
                quest.chapter.contains(query, ignoreCase = true)
            matchesType && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // 6. ENCHANTS
    val allEnchants: StateFlow<List<EnchantEntity>> = repository.allEnchants
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // 7. USER PROFILE & 15-SLOT EQUIPMENT (ACCOUNT DB)
    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val userEquipment: StateFlow<List<UserEquipEntity>> = repository.userEquipment
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Calculated User Stats from Profile & Equipment
    val userStats: StateFlow<List<UserStatEntry>> = combine(
        userProfile,
        userEquipment
    ) { profile, equips ->
        val bonusAtk = equips.sumOf { it.attackBonus }
        val bonusDef = equips.sumOf { it.defenseBonus }
        listOf(
            UserStatEntry("Physical Attack (ATK)", "${4200 + bonusAtk} (+${bonusAtk})", "Attack"),
            UserStatEntry("Accuracy (ACC)", "582", "Attack"),
            UserStatEntry("Critical Rate", "42.5%", "Attack"),
            UserStatEntry("Penetration", "210", "Attack"),
            UserStatEntry("Physical Defense (DEF)", "${3450 + bonusDef} (+${bonusDef})", "Defense"),
            UserStatEntry("Max HP", "38,400", "Defense"),
            UserStatEntry("Max MP", "4,200", "Defense"),
            UserStatEntry("PvP Damage Reduction", "+18.2%", "PvP"),
            UserStatEntry("Movement Speed", "+24.0%", "Utility"),
            UserStatEntry("Attack Speed", "+32.5%", "Utility")
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // User Actions
    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun onWeaponFilterChanged(weapon: String) {
        selectedWeaponFilter.value = weapon
    }

    fun onGradeFilterChanged(grade: String) {
        selectedGradeFilter.value = grade
    }

    fun onSortOptionChanged(option: ItemSortOption) {
        selectedSortOption.value = option
    }

    fun toggleFavorite(itemId: String) {
        val current = favoriteItemIds.value.toMutableSet()
        if (current.contains(itemId)) {
            current.remove(itemId)
        } else {
            current.add(itemId)
        }
        favoriteItemIds.value = current
    }

    fun toggleOnlyFavorites() {
        onlyFavoritesFilter.value = !onlyFavoritesFilter.value
    }

    fun selectMap(mapName: String?) {
        selectedMapFilter.value = mapName
    }

    fun setTab(tabIndex: Int) {
        currentTab.value = tabIndex
    }

    fun toggleFantasyBackground() {
        isFantasyBgActive.value = !isFantasyBgActive.value
    }

    fun setSidebarNavOpen(open: Boolean) {
        isSidebarNavOpen.value = open
    }

    fun selectItem(item: ItemEntity?) {
        _selectedItem.value = item
    }

    fun selectItemById(itemId: String) {
        val item = allItems.value.find { it.id == itemId }
        if (item != null) {
            _selectedItem.value = item
        }
    }

    fun setFlutterCodeDialogVisible(visible: Boolean) {
        _showFlutterCodeDialog.value = visible
    }

    fun setActiveMenuItem(item: LordNineMenuItem?) {
        _activeMenuItem.value = item
    }

    fun toggleQuest(questId: String, currentStatus: Boolean) {
        viewModelScope.launch {
            repository.toggleQuestCompleted(questId, !currentStatus)
        }
    }

    fun reloadSampleAccount() {
        viewModelScope.launch {
            repository.saveProfile(
                UserProfileEntity(
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
            )
        }
    }
}
