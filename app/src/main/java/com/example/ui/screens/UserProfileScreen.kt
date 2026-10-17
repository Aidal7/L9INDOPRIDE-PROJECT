package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserEquipEntity
import com.example.data.UserProfileEntity
import com.example.data.UserStatEntry
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateCard
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.GildedGold
import com.example.ui.theme.GildedGoldBright
import com.example.ui.theme.GildedGoldDark
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
import com.example.ui.theme.getGradeBgColor
import com.example.ui.theme.getGradeColor

@Composable
fun UserProfileScreen(
    profile: UserProfileEntity?,
    equipment: List<UserEquipEntity>,
    stats: List<UserStatEntry>,
    onReloadSampleAccount: () -> Unit,
    onInspectEquip: (UserEquipEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedProfileTab by remember { mutableStateOf(0) } // 0: Equipment (15 Slots), 1: Stats & Radar, 2: DB Compare, 3: Account Sync
    var showImportDialog by remember { mutableStateOf(false) }
    var showSuccessToast by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("user_profile_screen")
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. ROYAL CHARACTER HERO BANNER
        item {
            RoyalCharacterHeroCard(
                profile = profile,
                onImportClick = { showImportDialog = true }
            )
        }

        // 2. LUXURY CURRENCY & RUNTIME STAT BAR
        item {
            CurrencyRuntimeBar(profile = profile)
        }

        // 3. REGAL TAB SELECTOR (15 Equipment | Status Rinci | Compare vs Master | Cloud Sync)
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.2.dp, RoyalGoldBorderGradient, RoundedCornerShape(12.dp)),
                color = SlateGlass,
                shape = RoundedCornerShape(12.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedProfileTab,
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedProfileTab]),
                            color = GildedGold,
                            height = 3.dp
                        )
                    },
                    divider = {}
                ) {
                    val tabs = listOf(
                        "15 Equipment",
                        "Stat Tempur",
                        "DB Compare",
                        "Dump Sync"
                    )
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedProfileTab == index
                        Tab(
                            selected = isSelected,
                            onClick = { selectedProfileTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.5.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = if (isSelected) GildedGoldBright else TextMuted
                                )
                            },
                            modifier = Modifier.testTag("profile_tab_$index")
                        )
                    }
                }
            }
        }

        // 4. TAB CONTENTS
        when (selectedProfileTab) {
            // TAB 0: 15-SLOT EQUIPMENT GRID
            0 -> {
                item {
                    Text(
                        text = "15 SLOT PERLENGKAPAN KARAKTER (Lord Nine Account Dump)",
                        color = GildedGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
                item {
                    Equipment15SlotGrid(
                        equipment = equipment,
                        onEquipClick = onInspectEquip
                    )
                }
            }

            // TAB 1: STATUS RINCI DARI RUNTIME DUMP
            1 -> {
                item {
                    Text(
                        text = "TABEL STATISTIK KARAKTER (_statusInfoData & _statusViewData)",
                        color = GildedGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(stats.size) { index ->
                    val stat = stats[index]
                    StatRowCard(stat = stat)
                }
            }

            // TAB 2: COMPARE VS MASTER DATABASE (l9.db)
            2 -> {
                item {
                    CompareVsMasterCard(profile = profile, equipmentCount = equipment.size)
                }
            }

            // TAB 3: DUMP SYNC & FRIDA ACCOUNT LOADER
            3 -> {
                item {
                    AccountDumpSyncCard(
                        profile = profile,
                        onReloadSample = {
                            onReloadSampleAccount()
                            showSuccessToast = true
                        },
                        onOpenImportDialog = { showImportDialog = true }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    // MODAL DIALOG: IMPORT ACCOUNT DUMP JSON
    if (showImportDialog) {
        ImportAccountDumpDialog(
            onDismiss = { showImportDialog = false },
            onConfirmImport = {
                onReloadSampleAccount()
                showImportDialog = false
                showSuccessToast = true
            }
        )
    }

    // TOAST BANNER SUCCESS
    AnimatedVisibility(visible = showSuccessToast) {
        AlertDialog(
            onDismissRequest = { showSuccessToast = false },
            containerColor = DarkSlate,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.border(1.5.dp, GildedGold, RoundedCornerShape(14.dp)),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = GildedGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sinkronisasi Berhasil", color = TextPrimary, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Text(
                    text = "Data profil akun Lord Nine (440 field PlayerManager, 15 Slot Equipment, & 251 status data) berhasil dimuat ke database lokal!",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { showSuccessToast = false },
                    colors = ButtonDefaults.buttonColors(containerColor = GildedGold, contentColor = ObsidianDark)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun RoyalCharacterHeroCard(
    profile: UserProfileEntity?,
    onImportClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, RoyalGoldBorderGradient, RoundedCornerShape(16.dp)),
        color = SlateGlass,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SlateGlassGradient)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar Frame with Gold Rim
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .background(ObsidianDeep, CircleShape)
                            .border(2.dp, RoyalGoldBorderGradient, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = GildedGoldBright,
                            modifier = Modifier.size(40.dp)
                        )
                        // Level Badge
                        Surface(
                            color = GildedGold,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .border(1.dp, ObsidianDark, RoundedCornerShape(4.dp))
                        ) {
                            Text(
                                text = "Lv. ${profile?.level ?: 78}",
                                color = ObsidianDark,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    // Character Details
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile?.characterName ?: "LordKsatria",
                                color = TextPrimary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = Color(0xFF261D0C),
                                shape = RoundedCornerShape(4.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, GildedGold.copy(alpha = 0.6f))
                            ) {
                                Text(
                                    text = profile?.className ?: "Greatsword Berserker",
                                    color = GildedGoldBright,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = profile?.serverName ?: "L9 Asia - Stove 01",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Combat Power Pill with Gold Fill
                        Surface(
                            color = ObsidianDeep,
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GildedGold.copy(alpha = 0.7f))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MilitaryTech,
                                    contentDescription = null,
                                    tint = GildedGoldBright,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "COMBAT POWER: ${String.format("%,d", profile?.combatPower ?: 485290L)}",
                                    color = GildedGoldBright,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MetallicBorder, thickness = 1.dp)
                Spacer(modifier = Modifier.height(10.dp))

                // Action Buttons: Import Dump / Frida Cloud
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Tombol Mewah Gold Gradient (Import Dump)
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.2.dp, RoyalGoldBorderGradient, RoundedCornerShape(8.dp))
                            .clickable { onImportClick() },
                        color = Color.Transparent
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(RoyalGoldGradient),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CloudDownload,
                                    contentDescription = null,
                                    tint = ObsidianDark,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Import ACCOUNT_DUMP",
                                    color = ObsidianDark,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }

                    // Source Info Pill
                    Surface(
                        modifier = Modifier
                            .height(38.dp)
                            .border(1.dp, MetallicBorder, RoundedCornerShape(8.dp)),
                        color = ObsidianDeep,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Fingerprint,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Frida Sync: 2026-10-17",
                                color = TextSecondary,
                                fontSize = 10.5.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyRuntimeBar(profile: UserProfileEntity?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Gold
        Surface(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, MetallicBorder, RoundedCornerShape(8.dp)),
            color = DarkSlateCard,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.MonetizationOn,
                    contentDescription = null,
                    tint = GildedGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(text = "Gold", color = TextMuted, fontSize = 9.sp)
                    Text(
                        text = "${((profile?.gold ?: 48250000L) / 1000000)}M",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Diamond
        Surface(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, MetallicBorder, RoundedCornerShape(8.dp)),
            color = DarkSlateCard,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color(0xFF29B6F6),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(text = "Diamond", color = TextMuted, fontSize = 9.sp)
                    Text(
                        text = "${String.format("%,d", profile?.diamond ?: 12450L)}",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // TP Points
        Surface(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, MetallicBorder, RoundedCornerShape(8.dp)),
            color = DarkSlateCard,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Token,
                    contentDescription = null,
                    tint = Color(0xFFAB47BC),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(text = "TP Points", color = TextMuted, fontSize = 9.sp)
                    Text(
                        text = "${profile?.tpPoints ?: 1840L}",
                        color = Color(0xFFCE93D8),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun Equipment15SlotGrid(
    equipment: List<UserEquipEntity>,
    onEquipClick: (UserEquipEntity) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        equipment.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { equip ->
                    val gradeColor = getGradeColor(equip.grade)
                    val gradeBg = getGradeBgColor(equip.grade)

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.2.dp, gradeColor.copy(alpha = 0.8f), RoundedCornerShape(10.dp))
                            .clickable { onEquipClick(equip) }
                            .testTag("equip_slot_${equip.slotIndex}"),
                        color = DarkSlateCard,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            // Slot Title & Enhance Level (+9)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = equip.slotName,
                                    color = GildedGold,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Surface(
                                    color = Color(0xFF261D0C),
                                    shape = RoundedCornerShape(3.dp),
                                    border = androidx.compose.foundation.BorderStroke(0.8.dp, GildedGold)
                                ) {
                                    Text(
                                        text = "+${equip.enhanceLevel}",
                                        color = GildedGoldBright,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Item Name
                            Text(
                                text = equip.itemName,
                                color = TextPrimary,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Stat Bonus tag
                            Text(
                                text = if (equip.attackBonus > 0) "+${equip.attackBonus} ATK" else "+${equip.defenseBonus} DEF",
                                color = if (equip.attackBonus > 0) RubyRed else Color(0xFF42A5F5),
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Medium
                            )

                            // Grade Label
                            Text(
                                text = equip.grade,
                                color = gradeColor,
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                // Fill remaining empty weight slots if chunk < 3
                repeat(3 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun StatRowCard(stat: UserStatEntry) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                val iconColor = when (stat.category) {
                    "Attack" -> RubyRed
                    "Defense" -> Color(0xFF42A5F5)
                    "PvP" -> GildedGold
                    else -> Color(0xFF66BB6A)
                }
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(iconColor, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stat.statName,
                    color = TextPrimary,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = stat.statValue,
                color = GildedGoldBright,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CompareVsMasterCard(
    profile: UserProfileEntity?,
    equipmentCount: Int
) {
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
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.CompareArrows, contentDescription = null, tint = GildedGold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "KOMPARASI AKUN VS MASTER DATABASE (l9.db)",
                    color = GildedGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Perbandingan progres runtime akun milikmu dibandingkan 816.355 baris data induk game Lord Nine:",
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Milestone 1: Master Items
            MasterComparisonRow(
                title = "Item Terpasang vs Master Katalog",
                userCount = "$equipmentCount slot aktif",
                masterTotal = "8.007 Total Items",
                percentage = (equipmentCount.toFloat() / 8007f) * 100f
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Milestone 2: Monster Knowledge
            MasterComparisonRow(
                title = "Monster Knowledge Codex",
                userCount = "${profile?.monsterKnowledgeCount ?: 911} Terbuka",
                masterTotal = "6.123 Monster Master",
                percentage = ((profile?.monsterKnowledgeCount ?: 911).toFloat() / 6123f) * 100f
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Milestone 3: Quest Completed
            MasterComparisonRow(
                title = "Alur Cerita Misi Selesai",
                userCount = "${profile?.completedQuestsCount ?: 47} Misi",
                masterTotal = "12.160 Quest",
                percentage = ((profile?.completedQuestsCount ?: 47).toFloat() / 12160f) * 100f
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Milestone 4: Achievements
            MasterComparisonRow(
                title = "Pencapaian Petualang Selesai",
                userCount = "${profile?.completedAchievementsCount ?: 1214} Achievement",
                masterTotal = "2.850 Total",
                percentage = ((profile?.completedAchievementsCount ?: 1214).toFloat() / 2850f) * 100f
            )
        }
    }
}

@Composable
fun MasterComparisonRow(
    title: String,
    userCount: String,
    masterTotal: String,
    percentage: Float
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, color = TextPrimary, fontSize = 11.5.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "$userCount / $masterTotal", color = GildedGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(ObsidianDeep, RoundedCornerShape(3.dp))
                .border(0.5.dp, MetallicBorder, RoundedCornerShape(3.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth((percentage / 100f).coerceIn(0.04f, 1f))
                    .height(6.dp)
                    .background(RoyalGoldGradient, RoundedCornerShape(3.dp))
            )
        }
    }
}

@Composable
fun AccountDumpSyncCard(
    profile: UserProfileEntity?,
    onReloadSample: () -> Unit,
    onOpenImportDialog: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.2.dp, MetallicBorder, RoundedCornerShape(14.dp)),
        color = SlateGlass,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.CloudSync, contentDescription = null, tint = GildedGold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SUMBER DATA AKUN CLOUD (DUMP FRIDA)",
                    color = GildedGold,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Lokasi file dump: L9_RECAP/oc_dump_account/ACCOUNT_DUMP.json (2,2 MB). Berisi data 4 Manager inti: PlayerManager (440 field), KnowledgeManager (4), QuestManager (37), HomunManager (10).",
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onReloadSample,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GildedGold,
                        contentColor = ObsidianDark
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Reload Sample Dump", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onOpenImportDialog,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GildedGold),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GildedGold),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Input File JSON", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ImportAccountDumpDialog(
    onDismiss: () -> Unit,
    onConfirmImport: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkSlate,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.border(1.5.dp, GildedGold, RoundedCornerShape(16.dp)),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.CloudDownload, contentDescription = null, tint = GildedGold)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Import ACCOUNT_DUMP.json", color = TextPrimary, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Aplikasi akan mem-parsing file dump runtime Frida akun Lord Nine Anda secara read-only.",
                    color = TextSecondary,
                    fontSize = 12.5.sp
                )
                Surface(
                    color = ObsidianDeep,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MetallicBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = "Format yang didukung:", color = GildedGold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(text = "• ACCOUNT_DUMP.json (PlayerManager, QuestManager)", color = TextMuted, fontSize = 10.sp)
                        Text(text = "• acct_parsed.json (435 fields)", color = TextMuted, fontSize = 10.sp)
                        Text(text = "• 15 Equipment slots & _statusInfoData", color = TextMuted, fontSize = 10.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmImport,
                colors = ButtonDefaults.buttonColors(containerColor = GildedGold, contentColor = ObsidianDark)
            ) {
                Text("Muat & Sinkron", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary)
            ) {
                Text("Batal")
            }
        }
    )
}
