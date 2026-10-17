package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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

data class MapPreviewItem(
    val name: String,
    val floor: String,
    val monsterLevelRange: String,
    val drawableResId: Int,
    val recommendedCombatPower: Int
)

@Composable
fun SidebarNavigationOverlay(
    selectedMap: String?,
    onSelectMap: (String) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategoryIndex by remember { mutableStateOf(1) } // 1: Monster Codex default

    val mapPreviews = remember {
        listOf(
            MapPreviewItem(
                name = "Cultist Cave",
                floor = "2F",
                monsterLevelRange = "Lv. 68-74",
                drawableResId = R.drawable.map_cultist_cave,
                recommendedCombatPower = 48500
            ),
            MapPreviewItem(
                name = "Temporal Ruins",
                floor = "1F",
                monsterLevelRange = "Lv. 75-80",
                drawableResId = R.drawable.map_temporal_ruins,
                recommendedCombatPower = 52000
            ),
            MapPreviewItem(
                name = "Cultist Cave",
                floor = "3F (Sanctuary)",
                monsterLevelRange = "Lv. 76-82",
                drawableResId = R.drawable.map_cultist_cave,
                recommendedCombatPower = 56000
            ),
            MapPreviewItem(
                name = "Temporal Ruins",
                floor = "2F (Chamber)",
                monsterLevelRange = "Lv. 82-88",
                drawableResId = R.drawable.map_temporal_ruins,
                recommendedCombatPower = 62000
            ),
            MapPreviewItem(
                name = "Tyriosa",
                floor = "4F (Bone Crypt)",
                monsterLevelRange = "Lv. 80-86",
                drawableResId = R.drawable.map_cultist_cave,
                recommendedCombatPower = 60000
            ),
            MapPreviewItem(
                name = "Volcano Peak",
                floor = "Deep Basin",
                monsterLevelRange = "Lv. 88-95",
                drawableResId = R.drawable.map_temporal_ruins,
                recommendedCombatPower = 71000
            )
        )
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .testTag("sidebar_navigation_overlay"),
        color = ObsidianDark.copy(alpha = 0.96f)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Overlay Top Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkSlate)
                    .border(1.dp, GildedGold.copy(alpha = 0.6f), RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        tint = GildedGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ALL CATEGORIES SLIDING NAVIGATION",
                        color = GildedGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Split View: Left Navigation Tree (40%) + Right Compact RPG Grid (60%)
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // LEFT TREE PANE
                Column(
                    modifier = Modifier
                        .weight(0.42f)
                        .fillMaxHeight()
                        .background(DarkSlate.copy(alpha = 0.85f))
                        .border(1.dp, MetallicBorder)
                        .padding(vertical = 8.dp)
                ) {
                    val navTree = listOf(
                        "Dashboard",
                        "Monster Codex",
                        "Nested Tabs",
                        "Map Tabs",
                        "Item Menus",
                        "Slub Menu",
                        "Quick Jump"
                    )

                    navTree.forEachIndexed { index, title ->
                        val isSelected = selectedCategoryIndex == index
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedCategoryIndex = index }
                                .background(
                                    if (isSelected) Color(0xFF282316) else Color.Transparent
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) GildedGold.copy(alpha = 0.8f) else Color.Transparent
                                )
                                .padding(horizontal = 10.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = title,
                                color = if (isSelected) GildedGoldLight else TextSecondary,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Icon(
                                imageVector = if (isSelected) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = if (isSelected) GildedGold else TextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                // RIGHT COMPACT RPG GRID FLYOUT (Screen 2: Monster Codex Map Selector)
                Column(
                    modifier = Modifier
                        .weight(0.58f)
                        .fillMaxHeight()
                        .background(ObsidianDark)
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Monster Codex Map Grid",
                            color = GildedGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${mapPreviews.size} Peta",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(mapPreviews) { mapItem ->
                            val isCurrentMap = selectedMap == "${mapItem.name} ${mapItem.floor}"
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(0.85f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onSelectMap("${mapItem.name} ${mapItem.floor}") }
                                    .border(
                                        1.2.dp,
                                        if (isCurrentMap) GildedGold else MetallicBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .testTag("map_card_${mapItem.name.replace(" ", "_")}"),
                                color = DarkSlateCard,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.fillMaxSize()) {
                                    // Map Thumbnail Image
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                    ) {
                                        Image(
                                            painter = painterResource(id = mapItem.drawableResId),
                                            contentDescription = mapItem.name,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )

                                        // Dark vignette over map
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        listOf(Color.Transparent, Color(0x990B0D12), Color(0xEE0B0D12))
                                                    )
                                                )
                                        )

                                        // Recommended CP Badge
                                        Surface(
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .align(Alignment.TopEnd),
                                            color = ObsidianDark.copy(alpha = 0.85f),
                                            shape = RoundedCornerShape(4.dp),
                                            border = androidx.compose.foundation.BorderStroke(0.6.dp, GildedGold)
                                        ) {
                                            Text(
                                                text = "CP ${mapItem.recommendedCombatPower / 1000}k",
                                                color = GildedGoldLight,
                                                fontSize = 8.5.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }

                                    // Map Title & Floor
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(6.dp)
                                    ) {
                                        Text(
                                            text = mapItem.name,
                                            color = TextPrimary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = mapItem.floor,
                                                color = GildedGold,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Text(
                                                text = mapItem.monsterLevelRange,
                                                color = TextSecondary,
                                                fontSize = 8.5.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
