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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuestEntity
import com.example.data.SkillEntity
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateCard
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.GildedGold
import com.example.ui.theme.GildedGoldBright
import com.example.ui.theme.GildedGoldLight
import com.example.ui.theme.MetallicBorder
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.RoyalGoldBorderGradient
import com.example.ui.theme.SlateGlass
import com.example.ui.theme.SlateGlassGradient
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.getGradeColor

@Composable
fun SkillsQuestsScreen(
    skills: List<SkillEntity>,
    quests: List<QuestEntity>,
    selectedClass: String,
    selectedQuestType: String,
    onSelectClass: (String) -> Unit,
    onSelectQuestType: (String) -> Unit,
    onToggleQuest: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Skillbook Library, 1: Quest & Story Log

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("skills_quests_screen")
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // TAB SELECTOR
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
                    val tabs = listOf("Skillbook & Ability (7.437)", "Quests & Misi (12.160)")
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
                            modifier = Modifier.testTag("skill_quest_tab_$index")
                        )
                    }
                }
            }
        }

        // TAB 0: SKILLBOOK LIBRARY
        if (selectedTab == 0) {
            item {
                // Class filter chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val classes = listOf("All", "Greatsword", "Bow", "Staff", "Dagger", "Shield", "Wand")
                    classes.forEach { cls ->
                        val isSelected = selectedClass == cls
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) GildedGold else MetallicBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onSelectClass(cls) },
                            color = if (isSelected) Color(0xFF2E240D) else DarkSlateCard,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = cls,
                                color = if (isSelected) GildedGoldBright else TextSecondary,
                                fontSize = 11.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            items(skills) { skill ->
                SkillCard(skill = skill)
            }
        }

        // TAB 1: QUEST LOG
        if (selectedTab == 1) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val types = listOf("All", "Main Story", "Daily Mission", "Epic")
                    types.forEach { t ->
                        val isSelected = selectedQuestType == t
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) GildedGold else MetallicBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onSelectQuestType(t) },
                            color = if (isSelected) Color(0xFF2E240D) else DarkSlateCard,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = t,
                                color = if (isSelected) GildedGoldBright else TextSecondary,
                                fontSize = 11.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            items(quests) { quest ->
                QuestItemCard(
                    quest = quest,
                    onToggle = { onToggleQuest(quest.id, quest.isCompleted) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun SkillCard(skill: SkillEntity) {
    val gradeColor = getGradeColor(skill.grade)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.2.dp, gradeColor.copy(alpha = 0.7f), RoundedCornerShape(12.dp)),
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
                        imageVector = Icons.Default.FlashOn,
                        contentDescription = null,
                        tint = gradeColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = skill.name,
                        color = TextPrimary,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    color = ObsidianDeep,
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, gradeColor)
                ) {
                    Text(
                        text = "${skill.className} • ${skill.skillType}",
                        color = gradeColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = skill.description,
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
                    text = if (skill.mpCost > 0) "Konsumsi MP: ${skill.mpCost}" else "Pasif (0 MP)",
                    color = Color(0xFF29B6F6),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (skill.cooldownSec > 0f) "Cooldown: ${skill.cooldownSec}s" else "Instan",
                    color = TextMuted,
                    fontSize = 11.sp
                )
                Text(
                    text = skill.damageMultiplier,
                    color = GildedGoldBright,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun QuestItemCard(
    quest: QuestEntity,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, if (quest.isCompleted) GildedGold.copy(alpha = 0.5f) else MetallicBorder, RoundedCornerShape(12.dp)),
        color = DarkSlate,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = quest.isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = GildedGold,
                    checkmarkColor = ObsidianDeep,
                    uncheckedColor = TextMuted
                )
            )

            Spacer(modifier = Modifier.width(6.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = quest.title,
                        color = if (quest.isCompleted) GildedGoldBright else TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = quest.chapter,
                        color = TextMuted,
                        fontSize = 10.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Reward: +${String.format("%,d", quest.goldReward)} Gold",
                        color = Color(0xFFFFD54F),
                        fontSize = 10.5.sp
                    )
                    Text(
                        text = "+${String.format("%,d", quest.expReward)} EXP",
                        color = Color(0xFF64B5F6),
                        fontSize = 10.5.sp
                    )
                }
            }
        }
    }
}
