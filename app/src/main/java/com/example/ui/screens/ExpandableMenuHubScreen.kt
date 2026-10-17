package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.LordNineMenuItem
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateCard
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.GildedGold
import com.example.ui.theme.GildedGoldLight
import com.example.ui.theme.MetallicBorder
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

data class MenuCategory(
    val title: String,
    val icon: ImageVector,
    val items: List<LordNineMenuItem>,
    val defaultExpanded: Boolean = false
)

@Composable
fun ExpandableMenuHubScreen(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onMenuItemClick: (LordNineMenuItem) -> Unit,
    onOpenSidebarNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 5 Categories matching Screen 1 in the 2.5D visual specification
    val categories = remember {
        listOf(
            MenuCategory(
                title = "KARAKTER & SKILL",
                icon = Icons.Default.Shield,
                defaultExpanded = true,
                items = listOf(
                    LordNineMenuItem("Ability", Icons.Default.FlashOn, "Karakter", true, "Stat alokasi & atribut kekuatan tempur"),
                    LordNineMenuItem("Weapon Mastery", Icons.Default.Bolt, "Skill", true, "Tingkat kemahiran tiap kategori senjata"),
                    LordNineMenuItem("Mastery", Icons.Default.FitnessCenter, "Armor", false, "Armor Mastery (Cloth/Leather/Plate Lv 100)"),
                    LordNineMenuItem("Skillbook", Icons.Default.Book, "Skill", false, "Pustaka buku skill & konsumsi MP"),
                    LordNineMenuItem("Avatars", Icons.Default.Person, "Avatar", false, "Koleksi avatar visual & bonus status"),
                    LordNineMenuItem("Artifact", Icons.Default.Security, "Artifact", false, "Artefak purba penguat defensif"),
                    LordNineMenuItem("Mounts", Icons.Default.Pets, "Mobility", false, "Tunggangan darat & kecepatan tempur"),
                    LordNineMenuItem("Homun", Icons.Default.Extension, "Homunculus", true, "Kalkulator kultur sel #1-16 & genome"),
                    LordNineMenuItem("Relic", Icons.Default.LocalFireDepartment, "Relic", true, "Essence Upgrade TP (Origin, Barrier, Crystal)"),
                    LordNineMenuItem("Imprint", Icons.Default.Fingerprint, "Growth", false, "Pahatan rune kekuatan mistik"),
                    LordNineMenuItem("Tabula", Icons.Default.AutoAwesome, "Growth", false, "Sistem konstelasi kekuatan Tabula"),
                    LordNineMenuItem("Combat Memories", Icons.Default.Bookmark, "Lore", false, "Rekaman memori pertarungan legendaris")
                )
            ),
            MenuCategory(
                title = "ITEM & EKONOMI",
                icon = Icons.Default.Paid,
                items = listOf(
                    LordNineMenuItem("Inventory", Icons.Default.Inventory2, "Item", false, "Tas penyimpanan item & perlengkapan"),
                    LordNineMenuItem("Gear Forge", Icons.Default.Handyman, "Ekonomi", false, "Tempa & enhance senjata hingga +20"),
                    LordNineMenuItem("Craft", Icons.Default.Build, "Ekonomi", true, "Resep 820 item crafting & bahan drop"),
                    LordNineMenuItem("Item Log", Icons.Default.Bookmark, "Ekonomi", true, "1123 slot koleksi gear & bonus stat"),
                    LordNineMenuItem("NEXT Market", Icons.Default.Storefront, "Ekonomi", false, "Bursa transaksi C2C live pasar game"),
                    LordNineMenuItem("World Trading", Icons.Default.ShoppingCart, "Ekonomi", false, "Pasar dagang antar server benua"),
                    LordNineMenuItem("Consumables", Icons.Default.CardGiftcard, "Item", false, "Ramuan HP/MP & buff pertarungan"),
                    LordNineMenuItem("Dismantle TP", Icons.Default.LocalFireDepartment, "Ekonomi", true, "Kalkulator bongkar item menghasilkan TP")
                )
            ),
            MenuCategory(
                title = "COMBAT & BOSS",
                icon = Icons.Default.LocalFireDepartment,
                items = listOf(
                    LordNineMenuItem("Boss Status", Icons.Default.Public, "Combat", true, "Jadwal spawn World Boss & Nerucas"),
                    LordNineMenuItem("Monster Codex", Icons.Default.Explore, "Combat", true, "Daftar drop rate & monster l9.db"),
                    LordNineMenuItem("Dungeon", Icons.Default.Castle, "Combat", true, "Dungeon harian reset jam 02:00 malam"),
                    LordNineMenuItem("Spire of Trials", Icons.Default.Navigation, "Combat", false, "Tantangan lantai menara Spire 100F"),
                    LordNineMenuItem("Lord's Arena", Icons.Default.MilitaryTech, "PVP", false, "Arena duel gladiator kehormatan"),
                    LordNineMenuItem("PvP Records", Icons.Default.Leaderboard, "PVP", false, "Riwayat perang aliansi & rekor duel")
                )
            ),
            MenuCategory(
                title = "QUEST & MISI",
                icon = Icons.Default.Task,
                items = listOf(
                    LordNineMenuItem("Quests", Icons.Default.Task, "Quest", false, "12,160 misi alur cerita Lord Nine"),
                    LordNineMenuItem("Missions", Icons.Default.MilitaryTech, "Quest", false, "Misi harian & mingguan berhadiah"),
                    LordNineMenuItem("Achievements", Icons.Default.EmojiEvents, "Quest", false, "Pencapaian gelar & reputasi petualang"),
                    LordNineMenuItem("Battle Pass", Icons.Default.WorkspacePremium, "Quest", true, "Musim tantangan pass VIP Lord Nine"),
                    LordNineMenuItem("Event", Icons.Default.CardGiftcard, "Quest", false, "Event musiman & reward spesial")
                )
            ),
            MenuCategory(
                title = "SOSIAL & GUILD",
                icon = Icons.Default.Groups,
                items = listOf(
                    LordNineMenuItem("Guild", Icons.Default.Security, "Sosial", false, "Markas serikat & guild boss raid"),
                    LordNineMenuItem("Faction", Icons.Default.Castle, "Sosial", false, "Perebutan wilayah fraksi benua"),
                    LordNineMenuItem("Rankings", Icons.Default.Leaderboard, "Sosial", false, "Peringkat combat power server"),
                    LordNineMenuItem("Server Transfer", Icons.Default.SwapHoriz, "Sosial", false, "Migrasi karakter lintas server")
                )
            )
        )
    }

    // State for tracking expanded categories
    var expandedMap by remember {
        mutableStateOf(categories.associate { it.title to it.defaultExpanded })
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("expandable_menu_hub_screen"),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top Global Search Field
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .testTag("global_search_input"),
                placeholder = {
                    Text(
                        "Cari Menu, Item, atau Boss...",
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = GildedGold,
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear",
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
                    focusedBorderColor = GildedGold,
                    unfocusedBorderColor = MetallicBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )
        }

        // Title Header with Gilded Filigree Ornament Wings (Screen 1 Title)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Left Filigree Wing
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color.Transparent, GildedGold.copy(alpha = 0.8f), GildedGold)
                                )
                            )
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    // Gothic / Serif Styling Title
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "LORD NINE",
                            color = GildedGold,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Serif,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "DATABASE",
                            color = GildedGoldLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 4.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                    // Right Filigree Wing
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(GildedGold, GildedGold.copy(alpha = 0.8f), Color.Transparent)
                                )
                            )
                    )
                }

                // Subtitle Quick Jump
                Row(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .clickable(onClick = onOpenSidebarNavigation)
                        .background(DarkSlateElevated)
                        .border(0.8.dp, GildedGold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        tint = GildedGold,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Buka Sidebar Navigasi Peta (Monster Codex) →",
                        color = GildedGoldLight,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Expandable Accordion Rows (Categories 1-5)
        categories.forEach { category ->
            val isExpanded = expandedMap[category.title] ?: false
            val filteredCategoryItems = if (searchQuery.isBlank()) {
                category.items
            } else {
                category.items.filter {
                    it.title.contains(searchQuery, ignoreCase = true) ||
                    it.description.contains(searchQuery, ignoreCase = true) ||
                    it.category.contains(searchQuery, ignoreCase = true)
                }
            }

            if (searchQuery.isBlank() || filteredCategoryItems.isNotEmpty()) {
                item(key = category.title) {
                    AccordionCategoryCard(
                        category = category,
                        displayedItems = filteredCategoryItems,
                        isExpanded = isExpanded || searchQuery.isNotBlank(),
                        onToggleExpand = {
                            expandedMap = expandedMap.toMutableMap().apply {
                                put(category.title, !isExpanded)
                            }
                        },
                        onMenuItemClick = onMenuItemClick
                    )
                }
            }
        }
    }
}

@Composable
fun AccordionCategoryCard(
    category: MenuCategory,
    displayedItems: List<LordNineMenuItem>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onMenuItemClick: (LordNineMenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val arrowRotation by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "arrowRotation")

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                if (isExpanded) GildedGold.copy(alpha = 0.8f) else MetallicBorder,
                RoundedCornerShape(10.dp)
            )
            .testTag("accordion_${category.title.replace(" ", "_")}"),
        color = DarkSlate.copy(alpha = 0.95f),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row (Clickable to toggle)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onToggleExpand)
                    .padding(horizontal = 14.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .background(DarkSlateElevated, RoundedCornerShape(6.dp))
                            .border(0.8.dp, GildedGold.copy(alpha = 0.7f), RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = category.icon,
                            contentDescription = null,
                            tint = GildedGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "${category.title} (${category.items.size})",
                        color = if (isExpanded) GildedGold else TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Tutup" else "Buka",
                    tint = if (isExpanded) GildedGold else TextSecondary,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(arrowRotation)
                )
            }

            // Expandable Content: 4-Column Grid as shown in the screenshot!
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(ObsidianDark.copy(alpha = 0.7f))
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    // 4-Column Grid
                    val columns = 4
                    val chunked = displayedItems.chunked(columns)

                    chunked.forEach { rowItems ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            rowItems.forEach { menuItem ->
                                CompactMenuButton(
                                    item = menuItem,
                                    onClick = { onMenuItemClick(menuItem) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            // Fill remaining spaces if row has fewer than 4 items
                            for (i in 0 until (columns - rowItems.size)) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CompactMenuButton(
    item: LordNineMenuItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(58.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .border(
                1.dp,
                if (item.isHighlighted) GildedGold.copy(alpha = 0.8f) else MetallicBorder,
                RoundedCornerShape(8.dp)
            )
            .testTag("compact_btn_${item.title.replace(" ", "_")}"),
        color = if (item.isHighlighted) Color(0xFF242018) else DarkSlateCard,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (item.isHighlighted) GildedGold else TextSecondary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.title,
                color = if (item.isHighlighted) GildedGoldLight else TextPrimary,
                fontSize = 9.sp,
                fontWeight = if (item.isHighlighted) FontWeight.Bold else FontWeight.Medium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 10.5.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
