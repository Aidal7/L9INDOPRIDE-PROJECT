package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EnchantEntity
import com.example.data.MapEntity
import com.example.data.MonsterEntity
import com.example.ui.components.LordNineHUD
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateCard
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.GildedGold
import com.example.ui.theme.GildedGoldBright
import com.example.ui.theme.GildedGoldLight
import com.example.ui.theme.MetallicBorder
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.RoyalGoldBorderGradient
import com.example.ui.theme.RoyalGoldGradient
import com.example.ui.theme.RubyRed
import com.example.ui.theme.SlateGlass
import com.example.ui.theme.SlateGlassGradient
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CombatMapsScreen(
    monsters: List<MonsterEntity>,
    maps: List<MapEntity>,
    enchants: List<EnchantEntity>,
    selectedRegion: String,
    onSelectRegion: (String) -> Unit,
    onMonsterDropClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Monster Codex (DB), 1: Maps & Dungeon, 2: Gear Forge Enchant

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("combat_maps_screen")
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. LORD NINE HUD STATUS
        item {
            LordNineHUD()
        }

        // 2. TAB SELECTOR: [Monster Codex (DB)] | [Peta & Dungeon (436)] | [Tempa & Enchant (+20)]
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.2.dp, RoyalGoldBorderGradient, RoundedCornerShape(12.dp)),
                color = SlateGlass,
                shape = RoundedCornerShape(12.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = GildedGold,
                            height = 3.dp
                        )
                    },
                    divider = {}
                ) {
                    val tabs = listOf("Monster Codex", "Maps & Dungeon", "Gear Forge (+20)")
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTab == index
                        Tab(
                            selected = isSelected,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.5.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = if (isSelected) GildedGoldBright else TextMuted
                                )
                            },
                            modifier = Modifier.testTag("combat_tab_$index")
                        )
                    }
                }
            }
        }

        // TAB 0: MONSTER CODEX WIRED TO ROOM DB
        if (selectedTab == 0) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Explore, contentDescription = null, tint = GildedGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "MONSTER CODEX (Wired to l9.db)",
                            color = GildedGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "${monsters.size} Monster Terdaftar",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                }
            }

            items(monsters) { monster ->
                MonsterCodexDbCard(
                    monster = monster,
                    onDropClick = onMonsterDropClick
                )
            }
        }

        // TAB 1: MAPS & DUNGEON (436 MAPS)
        if (selectedTab == 1) {
            item {
                // Region Filter Pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val regions = listOf("All", "Whisper", "Tyriosa", "Volcano Peak", "Cultist Cave", "Temporal Ruins", "Spire of Trials")
                    regions.forEach { reg ->
                        val isSelected = selectedRegion == reg
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) GildedGold else MetallicBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onSelectRegion(reg) },
                            color = if (isSelected) Color(0xFF2E240D) else DarkSlateCard,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = reg,
                                color = if (isSelected) GildedGoldBright else TextSecondary,
                                fontSize = 11.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            items(maps) { mapItem ->
                MapItemCard(map = mapItem)
            }
        }

        // TAB 2: GEAR FORGE ENCHANT CALCULATOR
        if (selectedTab == 2) {
            item {
                GearForgeHeaderCard()
            }
            items(enchants) { enchant ->
                EnchantRowCard(enchant = enchant)
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun MonsterCodexDbCard(
    monster: MonsterEntity,
    onDropClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.2.dp, MetallicBorder, RoundedCornerShape(12.dp)),
        color = DarkSlate,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Level, Name, Grade & TP Points
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color(0xFF261D0C),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GildedGold)
                ) {
                    Text(
                        text = "Lv. ${monster.level}",
                        color = GildedGoldBright,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = monster.name,
                    color = TextPrimary,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = Color(0xFF2A1538),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFFAB47BC))
                ) {
                    Text(
                        text = "+${monster.codexTpReward} TP",
                        color = Color(0xFFCE93D8),
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Sub info: Grade, Race, Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = ObsidianDeep,
                    shape = RoundedCornerShape(3.dp),
                    border = androidx.compose.foundation.BorderStroke(0.7.dp, MetallicBorder)
                ) {
                    Text(
                        text = monster.grade,
                        color = GildedGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                    )
                }

                Text(
                    text = "${monster.race} • ${monster.attackType}",
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = GildedGold, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = monster.location,
                        color = GildedGoldLight,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // HP Bar Live (MMORPG Style)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color(0xFF21090C), RoundedCornerShape(4.dp))
                    .border(0.8.dp, Color(0xFF5E131C), RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(monster.hpPercent)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFFB71C1C), Color(0xFFE53935), Color(0xFFFF5252))
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Drops from l9.db
            Text(
                text = "Drop Loot (l9.db):",
                color = TextMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                monster.keyDropsSummary.split(",").forEach { dropName ->
                    val clean = dropName.trim()
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .border(0.8.dp, DarkSlateElevated, RoundedCornerShape(4.dp))
                            .clickable { onDropClick(clean) },
                        color = ObsidianDeep
                    ) {
                        Text(
                            text = clean,
                            color = GildedGoldLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MapItemCard(map: MapEntity) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MetallicBorder, RoundedCornerShape(12.dp)),
        color = DarkSlate,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (map.isDungeon) Icons.Default.Castle else Icons.Default.Public,
                        contentDescription = null,
                        tint = GildedGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = map.name,
                        color = TextPrimary,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    color = Color(0xFF261D0C),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GildedGold.copy(alpha = 0.7f))
                ) {
                    Text(
                        text = "Lv. ${map.minLevel} - ${map.maxLevel}",
                        color = GildedGoldBright,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = map.description,
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Reset: ${map.resetSchedule}",
                    color = Color(0xFF29B6F6),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${map.monsterCount} Jenis Monster",
                    color = TextMuted,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun GearForgeHeaderCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.2.dp, RoyalGoldBorderGradient, RoundedCornerShape(14.dp)),
        color = SlateGlass,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SlateGlassGradient)
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Build, contentDescription = null, tint = GildedGold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "GEAR FORGE & ENCHANT TABLE (+1 s/d +20)",
                    color = GildedGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Tabel probabilitas sukses, batas aman (Safe Zone +6), risiko kehancuran (Destroy Risk), dan biaya Gold per tempaan:",
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun EnchantRowCard(enchant: EnchantEntity) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MetallicBorder, RoundedCornerShape(8.dp)),
        color = DarkSlateCard,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Level
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = if (enchant.safeLevel) Color(0xFF132A15) else Color(0xFF331612),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (enchant.safeLevel) Color(0xFF4CAF50) else RubyRed
                    )
                ) {
                    Text(
                        text = "+${enchant.enhanceLevel}",
                        color = if (enchant.safeLevel) Color(0xFF81C784) else Color(0xFFE57373),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = if (enchant.safeLevel) "Safe Zone (100%)" else "Risiko Hancur: ${enchant.destroyRiskPct}%",
                        color = if (enchant.safeLevel) Color(0xFF81C784) else RubyRed,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${enchant.enhanceStoneCost} Batu Tempa",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
            }

            // Success Rate & Gold
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Peluang: ${enchant.successRatePct}%",
                    color = GildedGoldBright,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${String.format("%,d", enchant.goldCost)} Gold",
                    color = TextSecondary,
                    fontSize = 10.5.sp
                )
            }
        }
    }
}
