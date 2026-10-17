package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.ItemEntity
import com.example.data.ItemRepository
import com.example.data.MonsterDropEntity
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
    val favoriteIds: Set<String>
)

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ItemRepository

    val searchQuery = MutableStateFlow("")
    val selectedWeaponFilter = MutableStateFlow("All")
    val selectedGradeFilter = MutableStateFlow("All")
    val selectedSortOption = MutableStateFlow(ItemSortOption.GRADE_DESC)
    val onlyFavoritesFilter = MutableStateFlow(false)

    val currentTab = MutableStateFlow(0) // 0: Katalog, 1: Menu In-Game, 2: Codex Monster, 3: Komponen UI
    val isFantasyBgActive = MutableStateFlow(true)
    val favoriteItemIds = MutableStateFlow<Set<String>>(setOf("101007", "101001"))

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

    private val filterParams = combine(
        combine(searchQuery, selectedWeaponFilter, selectedGradeFilter) { q, w, g -> Triple(q, w, g) },
        combine(selectedSortOption, onlyFavoritesFilter, favoriteItemIds) { s, fav, ids -> Triple(s, fav, ids) }
    ) { (q, w, g), (s, fav, ids) ->
        FilterSortParams(q, w, g, s, fav, ids)
    }

    // Filtered and custom-sorted items flow
    val filteredItems: StateFlow<List<ItemEntity>> = combine(
        repository.allItems,
        filterParams
    ) { items, params ->
        val filtered = items.filter { item ->
            val matchesQuery = params.query.isBlank() ||
                    item.name.contains(params.query, ignoreCase = true) ||
                    item.weaponType.contains(params.query, ignoreCase = true) ||
                    item.grade.contains(params.query, ignoreCase = true)

            val matchesWeapon = params.weapon == "All" ||
                    item.weaponType.equals(params.weapon, ignoreCase = true)

            val matchesGrade = params.grade == "All" ||
                    item.grade.equals(params.grade, ignoreCase = true)

            val matchesFavorites = !params.onlyFavorites || params.favoriteIds.contains(item.id)

            matchesQuery && matchesWeapon && matchesGrade && matchesFavorites
        }

        // Apply Custom Sorting
        when (params.sortOption) {
            ItemSortOption.GRADE_DESC -> filtered.sortedWith(
                compareByDescending<ItemEntity> { gradeRank(it.grade) }
                    .thenByDescending { it.levelReq }
                    .thenByDescending { it.attackPower }
            )
            ItemSortOption.GRADE_ASC -> filtered.sortedWith(
                compareBy<ItemEntity> { gradeRank(it.grade) }
                    .thenBy { it.levelReq }
            )
            ItemSortOption.ATK_DESC -> filtered.sortedByDescending { it.attackPower }
            ItemSortOption.ATK_ASC -> filtered.sortedBy { it.attackPower }
            ItemSortOption.LEVEL_DESC -> filtered.sortedByDescending { it.levelReq }
            ItemSortOption.LEVEL_ASC -> filtered.sortedBy { it.levelReq }
            ItemSortOption.CRIT_DESC -> filtered.sortedByDescending { it.criticalRate }
            ItemSortOption.NAME_ASC -> filtered.sortedBy { it.name }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Drops for the selected item
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val selectedItemDrops: StateFlow<List<MonsterDropEntity>> = _selectedItem.flatMapLatest { item ->
        if (item != null) {
            repository.getDropsForItem(item.id)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectItem(item: ItemEntity?) {
        _selectedItem.value = item
    }

    fun setSortOption(option: ItemSortOption) {
        selectedSortOption.value = option
    }

    fun toggleFavoritesFilter() {
        onlyFavoritesFilter.value = !onlyFavoritesFilter.value
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

    fun setFlutterCodeDialogVisible(visible: Boolean) {
        _showFlutterCodeDialog.value = visible
    }

    fun setActiveMenuItem(menuItem: LordNineMenuItem?) {
        _activeMenuItem.value = menuItem
    }

    fun setTab(index: Int) {
        currentTab.value = index
    }

    fun toggleFantasyBackground(active: Boolean) {
        isFantasyBgActive.value = active
    }

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun onWeaponFilterSelected(filter: String) {
        selectedWeaponFilter.value = filter
    }

    fun onGradeFilterSelected(filter: String) {
        selectedGradeFilter.value = filter
    }
}
