package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.ItemSortOption
import com.example.ui.ItemViewModel
import com.example.ui.components.FlutterCodeDialog
import com.example.ui.components.InGameGridMenu
import com.example.ui.components.ItemCatalogCard
import com.example.ui.components.ItemDetailModal
import com.example.ui.components.LordNineHUD
import com.example.ui.components.MonsterCodexView
import com.example.ui.components.UIComponentShowcase
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateCard
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.getGradeColor

class MainActivity : ComponentActivity() {
    private val viewModel: ItemViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
                val isFantasyBgActive by viewModel.isFantasyBgActive.collectAsStateWithLifecycle()
                val items by viewModel.filteredItems.collectAsStateWithLifecycle()
                val selectedItem by viewModel.selectedItem.collectAsStateWithLifecycle()
                val drops by viewModel.selectedItemDrops.collectAsStateWithLifecycle()
                val showFlutterCode by viewModel.showFlutterCodeDialog.collectAsStateWithLifecycle()
                val activeMenuItem by viewModel.activeMenuItem.collectAsStateWithLifecycle()
                val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
                val selectedWeapon by viewModel.selectedWeaponFilter.collectAsStateWithLifecycle()
                val selectedGrade by viewModel.selectedGradeFilter.collectAsStateWithLifecycle()
                val selectedSortOption by viewModel.selectedSortOption.collectAsStateWithLifecycle()
                val onlyFavorites by viewModel.onlyFavoritesFilter.collectAsStateWithLifecycle()
                val favoriteIds by viewModel.favoriteItemIds.collectAsStateWithLifecycle()

                var showSortBottomSheet by remember { mutableStateOf(false) }

                Box(modifier = Modifier.fillMaxSize()) {
                    // Atmospheric Fantasy Wallpaper Background
                    if (isFantasyBgActive) {
                        Image(
                            painter = painterResource(id = R.drawable.bg_lordnine_fantasy),
                            contentDescription = "Fantasy Background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        // Dark obsidian gradient veil for high contrast and readability
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color(0xD90B0D12),
                                            Color(0xF00B0D12),
                                            Color(0xFA0B0D12)
                                        )
                                    )
                                )
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(ObsidianBackground)
                        )
                    }

                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("main_screen_scaffold"),
                        containerColor = Color.Transparent,
                        topBar = {
                            Column {
                                TopAppBar(
                                    title = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_gold_seal),
                                                contentDescription = "Lord Nine Dragon Emblem",
                                                modifier = Modifier
                                                    .size(36.dp)
                                                    .clip(CircleShape)
                                                    .border(1.2.dp, GoldBorder, CircleShape)
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(
                                                    text = "LORD NINE",
                                                    color = GoldAccent,
                                                    fontSize = 17.sp,
                                                    fontWeight = FontWeight.Black,
                                                    letterSpacing = 1.2.sp
                                                )
                                                Text(
                                                    text = when (currentTab) {
                                                        0 -> "Katalog Senjata & Detail Modal"
                                                        1 -> "HUD Status & Menu In-Game"
                                                        2 -> "Target Monster & Drop Codex"
                                                        else -> "Galeri Tombol & Komponen UI"
                                                    },
                                                    color = TextSecondary,
                                                    fontSize = 11.sp,
                                                    letterSpacing = 0.4.sp
                                                )
                                            }
                                        }
                                    },
                                    actions = {
                                        IconButton(
                                            onClick = { viewModel.setFlutterCodeDialogVisible(true) },
                                            modifier = Modifier.testTag("top_bar_flutter_code_button")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Code,
                                                contentDescription = "Kode Flutter / Dart",
                                                tint = GoldAccent
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = DarkSlate.copy(alpha = 0.95f),
                                        titleContentColor = GoldAccent
                                    )
                                )
                                // Ornate Gold Trim Under TopBar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(2.dp)
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(
                                                    Color.Transparent,
                                                    GoldBorder,
                                                    GoldAccent,
                                                    GoldBorder,
                                                    Color.Transparent
                                                )
                                            )
                                        )
                                )
                            }
                        },
                        bottomBar = {
                            NavigationBar(
                                containerColor = DarkSlate.copy(alpha = 0.95f),
                                modifier = Modifier
                                    .border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                    .testTag("main_bottom_nav_bar")
                            ) {
                                val navItems = listOf(
                                    Triple(0, "Katalog", Icons.Default.Shield),
                                    Triple(1, "Menu Game", Icons.Default.Menu),
                                    Triple(2, "Codex", Icons.Default.Explore),
                                    Triple(3, "UI & Tombol", Icons.Default.Widgets)
                                )

                                navItems.forEach { (index, label, icon) ->
                                    val isSelected = currentTab == index
                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = { viewModel.setTab(index) },
                                        icon = {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = label,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = label,
                                                fontSize = 11.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = ObsidianBackground,
                                            selectedTextColor = GoldAccent,
                                            indicatorColor = GoldBorder,
                                            unselectedIconColor = TextSecondary,
                                            unselectedTextColor = TextMuted
                                        ),
                                        modifier = Modifier.testTag("nav_tab_$index")
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            when (currentTab) {
                                // TAB 0: KATALOG ITEM & MODAL TRIGGER
                                0 -> {
                                    Column(modifier = Modifier.fillMaxSize()) {
                                        // Modern Search Bar
                                        OutlinedTextField(
                                            value = searchQuery,
                                            onValueChange = { viewModel.onSearchQueryChanged(it) },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                                .testTag("search_input"),
                                            placeholder = {
                                                Text(
                                                    "Cari senjata, tipe, grade, atau monster...",
                                                    color = TextMuted,
                                                    fontSize = 13.sp
                                                )
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Default.Search,
                                                    contentDescription = "Search Icon",
                                                    tint = GoldAccent,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            },
                                            trailingIcon = {
                                                if (searchQuery.isNotEmpty()) {
                                                    IconButton(
                                                        onClick = { viewModel.onSearchQueryChanged("") },
                                                        modifier = Modifier.testTag("clear_search_button")
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.Clear,
                                                            contentDescription = "Clear Search",
                                                            tint = TextSecondary,
                                                            modifier = Modifier.size(18.dp)
                                                        )
                                                    }
                                                }
                                            },
                                            singleLine = true,
                                            shape = RoundedCornerShape(10.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedContainerColor = DarkSlate,
                                                unfocusedContainerColor = DarkSlateCard,
                                                focusedBorderColor = GoldBorder,
                                                unfocusedBorderColor = SlateBorder,
                                                focusedTextColor = TextPrimary,
                                                unfocusedTextColor = TextPrimary
                                            )
                                        )

                                        // SORT & FILTER CONTROL BAR
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 2.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            // Sort Button Trigger
                                            Surface(
                                                color = DarkSlateElevated,
                                                shape = RoundedCornerShape(8.dp),
                                                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.8f)),
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable { showSortBottomSheet = true }
                                                    .testTag("btn_open_sort_modal")
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Sort,
                                                        contentDescription = "Sort",
                                                        tint = GoldAccent,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text(
                                                        text = "Urutkan: ${selectedSortOption.shortLabel}",
                                                        color = GoldLight,
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }

                                            // Quick Favorite Filter Toggle
                                            Surface(
                                                color = if (onlyFavorites) Color(0xFF382F18) else DarkSlateCard,
                                                shape = RoundedCornerShape(8.dp),
                                                border = androidx.compose.foundation.BorderStroke(
                                                    1.dp,
                                                    if (onlyFavorites) GoldBorder else SlateBorder
                                                ),
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable { viewModel.toggleFavoritesFilter() }
                                                    .testTag("btn_filter_favorites")
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = if (onlyFavorites) Icons.Default.Star else Icons.Outlined.StarBorder,
                                                        contentDescription = "Favorit",
                                                        tint = if (onlyFavorites) GoldAccent else TextMuted,
                                                        modifier = Modifier.size(15.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(4.dp))
                                                    Text(
                                                        text = if (onlyFavorites) "Favorit (${favoriteIds.size})" else "Favorit",
                                                        color = if (onlyFavorites) GoldLight else TextSecondary,
                                                        fontSize = 11.sp,
                                                        fontWeight = if (onlyFavorites) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))

                                        // Weapon Type Filter Chips (Horizontal)
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .horizontalScroll(rememberScrollState())
                                                .padding(horizontal = 16.dp, vertical = 2.dp),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            val weaponFilters = listOf(
                                                "All" to "Semua Senjata",
                                                "Greatsword" to "Greatsword",
                                                "Bow" to "Bow",
                                                "Staff" to "Staff",
                                                "Dagger" to "Dagger",
                                                "Crossbow" to "Crossbow",
                                                "Sword & Shield" to "Sword & Shield"
                                            )
                                            weaponFilters.forEach { (key, label) ->
                                                val isSelected = selectedWeapon.equals(key, ignoreCase = true)
                                                FilterChip(
                                                    selected = isSelected,
                                                    onClick = { viewModel.onWeaponFilterSelected(key) },
                                                    label = {
                                                        Text(
                                                            text = label,
                                                            fontSize = 11.sp,
                                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                                        )
                                                    },
                                                    colors = FilterChipDefaults.filterChipColors(
                                                        containerColor = DarkSlateCard,
                                                        labelColor = TextSecondary,
                                                        selectedContainerColor = DarkSlateElevated,
                                                        selectedLabelColor = GoldAccent
                                                    ),
                                                    border = FilterChipDefaults.filterChipBorder(
                                                        enabled = true,
                                                        selected = isSelected,
                                                        borderColor = SlateBorder,
                                                        selectedBorderColor = GoldBorder
                                                    ),
                                                    modifier = Modifier.testTag("filter_weapon_$key")
                                                )
                                            }
                                        }

                                        // Grade Filter Chips (Horizontal)
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .horizontalScroll(rememberScrollState())
                                                .padding(horizontal = 16.dp, vertical = 2.dp),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            val gradeFilters = listOf("All", "Mythic", "Legendary", "Epic", "Rare", "Uncommon", "Common")
                                            gradeFilters.forEach { grade ->
                                                val isSelected = selectedGrade.equals(grade, ignoreCase = true)
                                                val gradeColor = if (grade == "All") GoldBorder else getGradeColor(grade)
                                                FilterChip(
                                                    selected = isSelected,
                                                    onClick = { viewModel.onGradeFilterSelected(grade) },
                                                    label = {
                                                        Text(
                                                            text = if (grade == "All") "Semua Grade" else grade.uppercase(),
                                                            fontSize = 11.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    },
                                                    colors = FilterChipDefaults.filterChipColors(
                                                        containerColor = DarkSlateCard,
                                                        labelColor = if (isSelected) gradeColor else TextMuted,
                                                        selectedContainerColor = DarkSlateElevated,
                                                        selectedLabelColor = gradeColor
                                                    ),
                                                    border = FilterChipDefaults.filterChipBorder(
                                                        enabled = true,
                                                        selected = isSelected,
                                                        borderColor = if (isSelected) gradeColor else SlateBorder,
                                                        selectedBorderColor = gradeColor
                                                    ),
                                                    modifier = Modifier.testTag("filter_grade_$grade")
                                                )
                                            }
                                        }

                                        // Section Banner: Catalog Count & Notice
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "SENJATA (${items.size})",
                                                color = TextMuted,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 0.8.sp
                                            )
                                            Text(
                                                text = "Ketuk untuk Detail Modal",
                                                color = GoldAccent.copy(alpha = 0.9f),
                                                fontSize = 11.sp
                                            )
                                        }

                                        // Items List (LazyColumn)
                                        if (items.isEmpty()) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(32.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Icon(
                                                        imageVector = Icons.Default.Search,
                                                        contentDescription = null,
                                                        tint = TextMuted,
                                                        modifier = Modifier.size(48.dp)
                                                    )
                                                    Spacer(modifier = Modifier.height(12.dp))
                                                    Text(
                                                        text = "Tidak ada senjata yang cocok dengan filter",
                                                        color = TextSecondary,
                                                        fontSize = 14.sp
                                                    )
                                                }
                                            }
                                        } else {
                                            LazyColumn(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .testTag("item_catalog_list"),
                                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                                                verticalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                items(items = items, key = { it.id }) { item ->
                                                    ItemCatalogCard(
                                                        item = item,
                                                        isFavorite = favoriteIds.contains(item.id),
                                                        onToggleFavorite = { viewModel.toggleFavorite(item.id) },
                                                        onClick = { viewModel.selectItem(item) }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                // TAB 1: MENU IN-GAME & HUD
                                1 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .verticalScroll(rememberScrollState())
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        LordNineHUD()
                                        InGameGridMenu(
                                            onMenuItemClick = { menuItem ->
                                                when (menuItem.title) {
                                                    "Item Log" -> viewModel.setTab(0)
                                                    "Monster Codex" -> viewModel.setTab(2)
                                                    else -> viewModel.setActiveMenuItem(menuItem)
                                                }
                                            }
                                        )
                                    }
                                }

                                // TAB 2: CODEX MONSTER
                                2 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .verticalScroll(rememberScrollState())
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        MonsterCodexView(
                                            onMonsterDropClick = { dropName ->
                                                viewModel.onSearchQueryChanged(dropName)
                                                viewModel.setTab(0)
                                            }
                                        )
                                    }
                                }

                                // TAB 3: KOMPONEN UI & TOMBOL
                                3 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .verticalScroll(rememberScrollState())
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(14.dp)
                                    ) {
                                        UIComponentShowcase(
                                            isFantasyBgActive = isFantasyBgActive,
                                            onToggleBackground = { viewModel.toggleFantasyBackground(it) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // BOTTOM SHEET: DETAIL ITEM MODAL
                if (selectedItem != null) {
                    ItemDetailModal(
                        item = selectedItem,
                        drops = drops,
                        onDismiss = { viewModel.selectItem(null) },
                        onOpenFlutterCode = { viewModel.setFlutterCodeDialogVisible(true) }
                    )
                }

                // DIALOG: KODE FLUTTER / DART
                if (showFlutterCode) {
                    FlutterCodeDialog(
                        onDismiss = { viewModel.setFlutterCodeDialogVisible(false) }
                    )
                }

                // BOTTOM SHEET: PILIHAN SORTING (BY SHORT SAYA / PILIHAN USER)
                if (showSortBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showSortBottomSheet = false },
                        containerColor = DarkSlate,
                        dragHandle = {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                                    .width(44.dp)
                                    .height(4.dp)
                                    .background(GoldBorder, RoundedCornerShape(2.dp))
                            )
                        },
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        modifier = Modifier
                            .border(1.5.dp, GoldBorder, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .testTag("sort_bottom_sheet")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .navigationBarsPadding()
                                .padding(bottom = 24.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 14.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Sort,
                                    contentDescription = null,
                                    tint = GoldAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "PILIHAN PENGURUTAN (SORT BY)",
                                    color = GoldAccent,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )
                            }

                            ItemSortOption.values().forEach { option ->
                                val isSelected = selectedSortOption == option
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            viewModel.setSortOption(option)
                                            showSortBottomSheet = false
                                        }
                                        .background(
                                            if (isSelected) DarkSlateElevated else Color.Transparent
                                        )
                                        .padding(horizontal = 8.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = {
                                            viewModel.setSortOption(option)
                                            showSortBottomSheet = false
                                        },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = GoldAccent,
                                            unselectedColor = TextMuted
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = option.label,
                                        color = if (isSelected) GoldLight else TextPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { showSortBottomSheet = false },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GoldBorder,
                                    contentColor = ObsidianBackground
                                )
                            ) {
                                Text("SELESAI", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // DIALOG: DETAIL MENU CEPAT IN-GAME
                if (activeMenuItem != null) {
                    val item = activeMenuItem!!
                    AlertDialog(
                        onDismissRequest = { viewModel.setActiveMenuItem(null) },
                        containerColor = DarkSlate,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.border(1.5.dp, GoldBorder, RoundedCornerShape(16.dp)),
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(ObsidianBackground, RoundedCornerShape(8.dp))
                                        .border(1.2.dp, GoldBorder, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint = GoldAccent,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = item.title,
                                        color = TextPrimary,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Kategori: ${item.category}",
                                        color = GoldAccent,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        },
                        text = {
                            Text(
                                text = item.description,
                                color = TextSecondary,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = { viewModel.setActiveMenuItem(null) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GoldBorder,
                                    contentColor = ObsidianBackground
                                )
                            ) {
                                Text("Tutup", fontWeight = FontWeight.Bold)
                            }
                        }
                    )
                }
            }
        }
    }
}
