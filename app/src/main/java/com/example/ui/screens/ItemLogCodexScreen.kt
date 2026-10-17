package com.example.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ItemEntity
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateCard
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.GildedGold
import com.example.ui.theme.GildedGoldLight
import com.example.ui.theme.GradeCommon
import com.example.ui.theme.GradeEpic
import com.example.ui.theme.GradeLegendary
import com.example.ui.theme.GradeMythic
import com.example.ui.theme.GradeRare
import com.example.ui.theme.GradeUncommon
import com.example.ui.theme.MetallicBorder
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.getGradeColor

data class StatThresholdNode(
    val nodeNumber: Int,
    val progressCurrent: Int,
    val progressTarget: Int,
    val statBonus: String,
    val isUnlocked: Boolean
)

data class ItemLogGroup(
    val title: String,
    val category: String, // Gear Log, Special, Event
    val currentComplete: Int,
    val totalSlots: Int,
    val itemSlots: List<ItemEntity>,
    val statThresholds: List<StatThresholdNode>
)

@Composable
fun ItemLogCodexScreen(
    items: List<ItemEntity>,
    onBack: () -> Unit,
    onItemSlotClick: (ItemEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Gear Log, 1: Special, 2: Event
    val tabs = listOf("Gear Log", "Special", "Event")

    // Mock Groups constructed from real loaded SQLite items
    val gearItems = items.filter { it.weaponType != "Staff" }.take(6)
    val specialItems = items.filter { it.grade in listOf("Epic", "Legendary", "Mythic") }.take(5)
    val eventItems = items.take(4)

    val currentItems = when (selectedTab) {
        0 -> gearItems
        1 -> specialItems
        else -> eventItems
    }

    val currentTotal = when (selectedTab) {
        0 -> 1047
        1 -> 71
        else -> 5
    }

    val currentCompleted = when (selectedTab) {
        0 -> 23
        1 -> 14
        else -> 2
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("item_log_codex_screen")
    ) {
        // Top Bar matching Screen 3 in reference image
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSlate)
                .border(1.dp, GildedGold.copy(alpha = 0.5f), RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("btn_back_from_codex")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = GildedGold
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Column {
                Text(
                    text = "Item Log / Codex",
                    color = GildedGold,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Multilevel item-to-Stat completion panel (1123 slots)",
                    color = TextSecondary,
                    fontSize = 10.5.sp
                )
            }
        }

        // Top Tab Row: [Gear Log] | [Special] | [Event]
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = DarkSlateCard,
            contentColor = GildedGold,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = GildedGold,
                    height = 2.5.dp
                )
            },
            divider = {
                HorizontalDivider(color = MetallicBorder, thickness = 1.dp)
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 13.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == index) GildedGold else TextSecondary
                        )
                    },
                    modifier = Modifier.testTag("tab_log_$index")
                )
            }
        }

        // Main Completion Progress Header
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            color = DarkSlateElevated,
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GildedGold.copy(alpha = 0.6f))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(GildedGold, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PROGRESS TOTAL ${tabs[selectedTab].uppercase()}",
                            color = GildedGoldLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                    }

                    Text(
                        text = "$currentCompleted / $currentTotal Complete",
                        color = GildedGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Gold Progress Bar
                LinearProgressIndicator(
                    progress = { currentCompleted.toFloat() / currentTotal },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = GildedGold,
                    trackColor = ObsidianDark,
                )
            }
        }

        // Completion Cards (Gear Log, Special, Event) as shown on Screen 3!
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                ItemLogCard(
                    title = "Gear Log",
                    completed = 23,
                    total = 1047,
                    iconRes = Icons.Default.Shield,
                    rarityColor = GradeLegendary,
                    slotItems = gearItems,
                    thresholdNodes = listOf(
                        StatThresholdNode(123, 80, 20, "Max HP +120", true),
                        StatThresholdNode(575, 95, 100, "Critical Hit +5", false)
                    ),
                    onSlotClick = onItemSlotClick
                )
            }

            item {
                ItemLogCard(
                    title = "Special",
                    completed = 14,
                    total = 71,
                    iconRes = Icons.Default.AutoAwesome,
                    rarityColor = GradeMythic,
                    slotItems = specialItems,
                    thresholdNodes = listOf(
                        StatThresholdNode(109, 80, 30, "Max MP +180", true),
                        StatThresholdNode(525, 80, 100, "Daya Serang +35", false)
                    ),
                    onSlotClick = onItemSlotClick
                )
            }

            item {
                ItemLogCard(
                    title = "Event",
                    completed = 2,
                    total = 5,
                    iconRes = Icons.Default.CardGiftcard,
                    rarityColor = GradeEpic,
                    slotItems = eventItems,
                    thresholdNodes = listOf(
                        StatThresholdNode(210, 25, 20, "Drop Rate +3%", true),
                        StatThresholdNode(363, 50, 100, "HP Regen +6", false)
                    ),
                    onSlotClick = onItemSlotClick
                )
            }
        }
    }
}

@Composable
fun ItemLogCard(
    title: String,
    completed: Int,
    total: Int,
    iconRes: androidx.compose.ui.graphics.vector.ImageVector,
    rarityColor: Color,
    slotItems: List<ItemEntity>,
    thresholdNodes: List<StatThresholdNode>,
    onSlotClick: (ItemEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.2.dp, MetallicBorder, RoundedCornerShape(12.dp))
            .testTag("item_log_card_$title"),
        color = DarkSlate,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header: Category Icon, Title, Progress & Glowing Status Dot
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Icon with Rarity Frame
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(ObsidianDark, RoundedCornerShape(8.dp))
                        .border(1.8.dp, rarityColor, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = iconRes,
                        contentDescription = title,
                        tint = rarityColor,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            color = TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$completed / $total Complete",
                            color = GildedGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    // Glowing Stat Unlock Threshold Subtitle
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .shadow(4.dp, CircleShape, ambientColor = GildedGold, spotColor = GildedGold)
                                .background(GildedGold, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Glowing Stat Unlock Threshold",
                            color = GildedGoldLight,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Row containing Item Slots (Left) and Milestone Stat Node Thresholds (Right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left: Item Slot Boxes with Rarity Borders
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    slotItems.take(3).forEachIndexed { idx, item ->
                        ItemSlotBox(
                            item = item,
                            onClick = { onSlotClick(item) }
                        )
                    }
                }

                // Right: Milestone Stat Node Thresholds (Circular Gilded Badges as on Screen 3)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    thresholdNodes.forEach { node ->
                        StatThresholdNodeWidget(node = node)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemSlotBox(
    item: ItemEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val gradeColor = getGradeColor(item.grade)

    val assetBitmap = remember(item.id) {
        try {
            context.assets.open("items/${item.id}.png").use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
            }
        } catch (_: Exception) {
            null
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .testTag("slot_box_${item.id}")
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .background(ObsidianDark, RoundedCornerShape(8.dp))
                .border(1.8.dp, gradeColor, RoundedCornerShape(8.dp))
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            if (assetBitmap != null) {
                Image(
                    bitmap = assetBitmap,
                    contentDescription = item.name,
                    modifier = Modifier.size(36.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = item.name,
                    tint = gradeColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Small Level / Upgrade indicator
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                color = ObsidianDark.copy(alpha = 0.85f),
                shape = RoundedCornerShape(3.dp)
            ) {
                Text(
                    text = "+0",
                    color = GildedGold,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Slot",
            color = TextMuted,
            fontSize = 9.sp
        )
    }
}

@Composable
fun StatThresholdNodeWidget(
    node: StatThresholdNode,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Circular Gold Ring Node
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    if (node.isUnlocked) Color(0xFF332711) else DarkSlateCard,
                    CircleShape
                )
                .border(
                    1.8.dp,
                    if (node.isUnlocked) GildedGold else MetallicBorder,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${node.nodeNumber}",
                color = if (node.isUnlocked) GildedGoldLight else TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Ratio e.g. 80 / 20
        Text(
            text = "${node.progressCurrent}/${node.progressTarget}",
            color = if (node.isUnlocked) GildedGold else TextMuted,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
