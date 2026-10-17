package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.ItemEntity
import com.example.ui.ItemViewModel
import com.example.ui.components.FlutterCodeDialog
import com.example.ui.components.ItemDetailModal
import com.example.ui.screens.CombatMapsScreen
import com.example.ui.screens.ExpandableMenuHubScreen
import com.example.ui.screens.ItemLogCodexScreen
import com.example.ui.screens.SidebarNavigationOverlay
import com.example.ui.screens.SkillsQuestsScreen
import com.example.ui.screens.UserProfileScreen
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.DeepVignetteGradient
import com.example.ui.theme.GildedGold
import com.example.ui.theme.GildedGoldBright
import com.example.ui.theme.GildedGoldLight
import com.example.ui.theme.MetallicBorder
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.RoyalGoldBorderGradient
import com.example.ui.theme.RoyalGoldGradient
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

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
                val isSidebarNavOpen by viewModel.isSidebarNavOpen.collectAsStateWithLifecycle()
                val selectedMapFilter by viewModel.selectedMapFilter.collectAsStateWithLifecycle()
                val items by viewModel.filteredItems.collectAsStateWithLifecycle()
                val selectedItem by viewModel.selectedItem.collectAsStateWithLifecycle()
                val drops by viewModel.selectedItemDrops.collectAsStateWithLifecycle()
                val showFlutterCode by viewModel.showFlutterCodeDialog.collectAsStateWithLifecycle()
                val activeMenuItem by viewModel.activeMenuItem.collectAsStateWithLifecycle()
                val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

                // Master Entities & Account Flows (3-Layer Architecture)
                val monsters by viewModel.allMonsters.collectAsStateWithLifecycle()
                val maps by viewModel.filteredMaps.collectAsStateWithLifecycle()
                val skills by viewModel.filteredSkills.collectAsStateWithLifecycle()
                val quests by viewModel.filteredQuests.collectAsStateWithLifecycle()
                val enchants by viewModel.allEnchants.collectAsStateWithLifecycle()
                val profile by viewModel.userProfile.collectAsStateWithLifecycle()
                val equipment by viewModel.userEquipment.collectAsStateWithLifecycle()
                val stats by viewModel.userStats.collectAsStateWithLifecycle()

                val selectedClass by viewModel.selectedSkillClassFilter.collectAsStateWithLifecycle()
                val selectedQuestType by viewModel.selectedQuestTypeFilter.collectAsStateWithLifecycle()
                val selectedRegion by viewModel.selectedMapRegionFilter.collectAsStateWithLifecycle()

                Box(modifier = Modifier.fillMaxSize()) {
                    // Atmospheric MMORPG Wallpaper Background with Regal Vignette
                    if (isFantasyBgActive) {
                        Image(
                            painter = painterResource(id = R.drawable.bg_lordnine_fantasy),
                            contentDescription = "Fantasy MMORPG Castle Background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        // Deep Obsidian Vignette Veil for high contrast and readability
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DeepVignetteGradient)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(ObsidianDark)
                        )
                    }

                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("main_screen_scaffold"),
                        containerColor = Color.Transparent,
                        bottomBar = {
                            // Luxurious 5-Tab Navigation Bar with Gold Frame
                            NavigationBar(
                                containerColor = DarkSlate.copy(alpha = 0.95f),
                                modifier = Modifier
                                    .border(
                                        1.2.dp,
                                        RoyalGoldBorderGradient,
                                        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                    )
                                    .testTag("main_bottom_nav_bar")
                            ) {
                                val navTabs = listOf(
                                    Triple(0, "Menu Hub", Icons.Default.Home),
                                    Triple(1, "Item Log", Icons.Default.Inventory2),
                                    Triple(2, "Combat & Map", Icons.Default.LocalFireDepartment),
                                    Triple(3, "Skill & Misi", Icons.Default.Book),
                                    Triple(4, "Profil Akun", Icons.Default.Person)
                                )

                                navTabs.forEach { (index, label, icon) ->
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
                                                fontSize = 10.5.sp,
                                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = ObsidianDark,
                                            selectedTextColor = GildedGoldBright,
                                            indicatorColor = GildedGold,
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
                                // SCREEN 1: EXPANDABLE MENU HUB (DASHBOARD)
                                0 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .statusBarsPadding()
                                    ) {
                                        ExpandableMenuHubScreen(
                                            searchQuery = searchQuery,
                                            onSearchChange = { viewModel.onSearchQueryChanged(it) },
                                            onMenuItemClick = { menuItem ->
                                                when (menuItem.title) {
                                                    "Item Log", "Inventory", "Craft", "Gear Forge" -> viewModel.setTab(1)
                                                    "Monster Codex", "Boss Status", "Dungeon", "Spire of Trials" -> viewModel.setTab(2)
                                                    "Skillbook", "Ability", "Weapon Mastery", "Quests", "Missions", "Achievements" -> viewModel.setTab(3)
                                                    "Avatars", "Homun", "Relic" -> viewModel.setTab(4)
                                                    else -> viewModel.setActiveMenuItem(menuItem)
                                                }
                                            },
                                            onOpenSidebarNavigation = { viewModel.setSidebarNavOpen(true) }
                                        )
                                    }
                                }

                                // SCREEN 2: ITEM LOG / CODEX SCREEN (DATABASE TAB)
                                1 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .statusBarsPadding()
                                    ) {
                                        ItemLogCodexScreen(
                                            items = items,
                                            onBack = { viewModel.setTab(0) },
                                            onItemSlotClick = { item -> viewModel.selectItem(item) }
                                        )
                                    }
                                }

                                // SCREEN 3: COMBAT & MAPS EXPLORER (WIRED TO ROOM DB)
                                2 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .statusBarsPadding()
                                    ) {
                                        CombatMapsScreen(
                                            monsters = monsters,
                                            maps = maps,
                                            enchants = enchants,
                                            selectedRegion = selectedRegion,
                                            onSelectRegion = { viewModel.selectedMapRegionFilter.value = it },
                                            onMonsterDropClick = { dropName ->
                                                viewModel.onSearchQueryChanged(dropName)
                                                viewModel.setTab(1)
                                            }
                                        )
                                    }
                                }

                                // SCREEN 4: SKILLBOOK & QUEST LOG
                                3 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .statusBarsPadding()
                                    ) {
                                        SkillsQuestsScreen(
                                            skills = skills,
                                            quests = quests,
                                            selectedClass = selectedClass,
                                            selectedQuestType = selectedQuestType,
                                            onSelectClass = { viewModel.selectedSkillClassFilter.value = it },
                                            onSelectQuestType = { viewModel.selectedQuestTypeFilter.value = it },
                                            onToggleQuest = { id, current -> viewModel.toggleQuest(id, current) }
                                        )
                                    }
                                }

                                // SCREEN 5: PROFIL AKUN USER (15 EQUIPMENT SLOTS & ACCOUNT DUMP)
                                4 -> {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .statusBarsPadding()
                                    ) {
                                        UserProfileScreen(
                                            profile = profile,
                                            equipment = equipment,
                                            stats = stats,
                                            onReloadSampleAccount = { viewModel.reloadSampleAccount() },
                                            onInspectEquip = { equip ->
                                                // Find matching item or create preview entity
                                                val existing = items.find { it.id == equip.itemId }
                                                if (existing != null) {
                                                    viewModel.selectItem(existing)
                                                } else {
                                                    viewModel.selectItem(
                                                        ItemEntity(
                                                            id = equip.itemId,
                                                            name = equip.itemName,
                                                            grade = equip.grade,
                                                            weaponType = equip.slotName,
                                                            levelReq = 75,
                                                            attackPower = equip.attackBonus,
                                                            attackSpeed = 1.0f,
                                                            criticalRate = 15.0f,
                                                            accuracy = 200,
                                                            penetration = 80,
                                                            weight = 12.0f,
                                                            description = "Perlengkapan Lord Nine: ${equip.slotName} (+${equip.enhanceLevel}). Efek khusus: ${equip.specialEffect}",
                                                            isEstimatedStat = true
                                                        )
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            // SLIDING SIDEBAR OVERLAY
                            AnimatedVisibility(
                                visible = isSidebarNavOpen,
                                enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                                exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                            ) {
                                SidebarNavigationOverlay(
                                    selectedMap = selectedMapFilter,
                                    onSelectMap = { mapName ->
                                        viewModel.selectMap(mapName)
                                        viewModel.setSidebarNavOpen(false)
                                        viewModel.setTab(2)
                                    },
                                    onClose = { viewModel.setSidebarNavOpen(false) }
                                )
                            }
                        }
                    }
                }

                // BOTTOM SHEET: DETAIL ITEM MODAL (with decode estimate label & stats)
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

                // DIALOG: DETAIL MENU CEPAT IN-GAME
                if (activeMenuItem != null) {
                    val item = activeMenuItem!!
                    AlertDialog(
                        onDismissRequest = { viewModel.setActiveMenuItem(null) },
                        containerColor = DarkSlate,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.border(1.5.dp, GildedGold, RoundedCornerShape(16.dp)),
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(ObsidianDark, RoundedCornerShape(8.dp))
                                        .border(1.2.dp, GildedGold, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint = GildedGold,
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
                                        color = GildedGold,
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
                                    containerColor = GildedGold,
                                    contentColor = ObsidianDark
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
