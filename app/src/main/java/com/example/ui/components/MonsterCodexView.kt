package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateCard
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

data class LordNineMonster(
    val name: String,
    val level: Int,
    val grade: String, // Common, Elite, Boss, World Boss
    val race: String, // Spirit, Beast, Undead, Demon, Dragon
    val attackType: String, // Melee, Ranged, Magic
    val location: String,
    val hpPercent: Float,
    val codexTpReward: Int,
    val keyDrops: List<String>
)

@Composable
fun MonsterCodexView(
    onMonsterDropClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val monsters = listOf(
        LordNineMonster(
            name = "Dawn Tetere",
            level = 74,
            grade = "Common",
            race = "Spirit",
            attackType = "Melee",
            location = "Whisper Woodlands",
            hpPercent = 0.68f,
            codexTpReward = 45,
            keyDrops = listOf("Celestial Moon Bow", "Refined Spirit Crystal", "Enhance Stone")
        ),
        LordNineMonster(
            name = "Dragonbone Shell Turtle",
            level = 80,
            grade = "Elite",
            race = "Beast",
            attackType = "Melee",
            location = "Tyriosa 4F (Chamber of Bones)",
            hpPercent = 0.85f,
            codexTpReward = 90,
            keyDrops = listOf("Abyssal Dragon Greatsword", "Temporal Piece (90 TP)", "Dragon Bone Core")
        ),
        LordNineMonster(
            name = "Nerucas World Boss",
            level = 90,
            grade = "World Boss",
            race = "Demon",
            attackType = "Magic",
            location = "Volcano Peak (Deep Floor)",
            hpPercent = 1.0f,
            codexTpReward = 250,
            keyDrops = listOf("Nerucas Apocalypse Blade", "Mythic Essence", "Nerucas Soul Shard")
        ),
        LordNineMonster(
            name = "Tyriosa Sentinel Guardian",
            level = 78,
            grade = "Elite",
            race = "Mutant",
            attackType = "Ranged",
            location = "Tyriosa 3F (Ancient Gate)",
            hpPercent = 0.42f,
            codexTpReward = 75,
            keyDrops = listOf("Abyssal Dragon Greatsword", "Sentinel Core", "Rare Scroll")
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("monster_codex_view"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = GoldAccent,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "TARGET MONSTER & CODEX (IN-GAME HUD)",
                    color = GoldAccent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }
            Text(
                text = "Live Target Bar",
                color = TextMuted,
                fontSize = 11.sp
            )
        }

        // List of In-Game Monster Target Bars
        monsters.forEach { monster ->
            MonsterTargetBarCard(
                monster = monster,
                onDropClick = onMonsterDropClick
            )
        }
    }
}

@Composable
fun MonsterTargetBarCard(
    monster: LordNineMonster,
    onDropClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("monster_bar_${monster.name.replace(" ", "_")}"),
        color = DarkSlate,
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, GoldBorder.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Header Bar: Monster Name, Level, Grade, Race & Attack Type (Identik Screenshot)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Level Badge
                Surface(
                    color = Color(0xFF261D0C),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
                ) {
                    Text(
                        text = "Lv. ${monster.level}",
                        color = GoldAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Monster Name
                Text(
                    text = monster.name,
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                // TP Reward Badge
                Surface(
                    color = Color(0xFF2E173E),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, Color(0xFFAB47BC))
                ) {
                    Text(
                        text = "+${monster.codexTpReward} TP",
                        color = Color(0xFFCE93D8),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Sub-info: Grade, Type, Race & Map
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Grade Badge
                Surface(
                    color = ObsidianBackground,
                    shape = RoundedCornerShape(3.dp),
                    border = androidx.compose.foundation.BorderStroke(0.7.dp, SlateBorder)
                ) {
                    Text(
                        text = monster.grade,
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                    )
                }

                // Attack Type (Melee / Ranged / Magic)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val icon = when (monster.attackType.lowercase()) {
                        "magic" -> Icons.Default.AutoAwesome
                        "ranged" -> Icons.Default.GpsFixed
                        else -> Icons.Default.Security
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = monster.attackType,
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Text(text = "•", color = SlateBorder, fontSize = 10.sp)

                // Race
                Text(
                    text = monster.race,
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                // Map
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = GoldBorder,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = monster.location,
                        color = GoldLight,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Target HP Bar (Red Gradient with In-game Style)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(9.dp)
                    .background(Color(0xFF21090C), RoundedCornerShape(4.dp))
                    .border(0.8.dp, Color(0xFF5E131C), RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(monster.hpPercent)
                        .height(9.dp)
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
                monster.keyDrops.forEach { dropItem ->
                    Surface(
                        color = ObsidianBackground,
                        shape = RoundedCornerShape(4.dp),
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, DarkSlateElevated)
                    ) {
                        Text(
                            text = dropItem,
                            color = GoldLight,
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
