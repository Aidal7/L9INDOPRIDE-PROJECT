package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WorkspacePremium
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

data class LordNineMenuItem(
    val title: String,
    val icon: ImageVector,
    val category: String,
    val isHighlighted: Boolean = false,
    val description: String = ""
)

@Composable
fun InGameGridMenu(
    onMenuItemClick: (LordNineMenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val menuItems = listOf(
        // Row 1: Core System & Inventory
        LordNineMenuItem("Battle Pass", Icons.Default.WorkspacePremium, "System", true, "Hadiah musim & tantangan VIP"),
        LordNineMenuItem("Event", Icons.Default.CardGiftcard, "System", false, "Daftar event aktif & login bonus"),
        LordNineMenuItem("Shop", Icons.Default.ShoppingCart, "Market", false, "Toko perlengkapan & item mall"),
        LordNineMenuItem("Summon Shop", Icons.Default.AutoAwesome, "Gacha", false, "Summon Avatar & Familiar"),
        LordNineMenuItem("Skillbook", Icons.Default.Book, "Character", false, "Pustaka skill aktif & pasif"),
        LordNineMenuItem("Inventory", Icons.Default.Inventory2, "Gear", false, "Tas penyimpanan item & consumable"),

        // Row 2: Character Mastery & Avatars
        LordNineMenuItem("Mastery", Icons.Default.FitnessCenter, "Growth", true, "Peningkatan weapon & armor mastery"),
        LordNineMenuItem("Weapon Traits", Icons.Default.Bolt, "Growth", false, "Kustomisasi efek trait senjata"),
        LordNineMenuItem("Ability", Icons.Default.FlashOn, "Growth", false, "Poin stat & atribut karakter"),
        LordNineMenuItem("Avatars", Icons.Default.Person, "Cosmetic", false, "Koleksi avatar & bonus status"),
        LordNineMenuItem("Artifact", Icons.Default.Shield, "Gear", false, "Artefak purba penguat tempur"),
        LordNineMenuItem("Mounts", Icons.Default.Pets, "Mobility", false, "Tunggangan darat & akselerasi"),
        LordNineMenuItem("Homun", Icons.Default.Extension, "Homunculus", true, "Kalkulator sel kultur #1-16 & genome"),

        // Row 3: Items, Trade & Quests
        LordNineMenuItem("Item Log", Icons.Default.Bookmark, "Guide", true, "1123 slot koleksi gear & bonus stat"),
        LordNineMenuItem("World Trading", Icons.Default.Storefront, "Market", false, "Pasar bursa perdagangan antar pemain"),
        LordNineMenuItem("Gear Forge", Icons.Default.Handyman, "Crafting", false, "Tempa & enhance persenjataan"),
        LordNineMenuItem("Craft", Icons.Default.Build, "Crafting", true, "Resep crafting senjata & armor 820"),
        LordNineMenuItem("Quests", Icons.Default.Task, "Story", false, "Misi alur cerita Lord Nine utama"),
        LordNineMenuItem("Missions", Icons.Default.MilitaryTech, "Story", false, "Misi harian & mingguan"),
        LordNineMenuItem("Achievements", Icons.Default.EmojiEvents, "Achievement", false, "Gelar & pencapaian pemain"),

        // Row 4: Exploration, Dungeon & Relic
        LordNineMenuItem("Journal", Icons.Default.TravelExplore, "Codex", false, "Catatan eksplorasi benua"),
        LordNineMenuItem("Dungeon", Icons.Default.Castle, "Dungeon", true, "Dungeon harian reset jam 02:00 malam"),
        LordNineMenuItem("Spire of Trials", Icons.Default.Navigation, "Challenge", false, "Ujian lantai menara Spire 100F"),
        LordNineMenuItem("Relic", Icons.Default.LocalFireDepartment, "Relic", true, "Essence Upgrade TP (Origin/Barrier/Crystal)"),
        LordNineMenuItem("Imprint", Icons.Default.Fingerprint, "Growth", false, "Pahatan rune kekuatan mistik"),
        LordNineMenuItem("Monster Codex", Icons.Default.Explore, "Codex", true, "Daftar drop rate & data monster l9.db"),
        LordNineMenuItem("Friendship", Icons.Default.Groups, "Social", false, "Tingkat persahabatan antar pemain"),

        // Row 5: Raid & Boss Status
        LordNineMenuItem("Boss Status", Icons.Default.Public, "Boss", true, "Jadwal spawn World Boss & Nerucas"),
        LordNineMenuItem("Guild", Icons.Default.Security, "Social", false, "Markas serikat & perang guild"),
        LordNineMenuItem("Rankings", Icons.Default.Leaderboard, "PVP", false, "Peringkat combat power & arena"),
        LordNineMenuItem("Faction", Icons.Default.Castle, "PVP", false, "Fraksi aliansi benua"),
        LordNineMenuItem("PvP Records", Icons.Default.MilitaryTech, "PVP", false, "Riwayat duel & kemenangan tempur"),
        LordNineMenuItem("Relationships", Icons.Default.Groups, "Social", false, "Jaringan aliansi & rival"),
        LordNineMenuItem("Server Transfer", Icons.Default.SwapHoriz, "System", false, "Pindah server antar benua")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("in_game_grid_menu_container")
    ) {
        // Menu Header Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(GoldBorder, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "MENU CEPAT IN-GAME (LORD NINE)",
                    color = GoldAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Text(
                text = "${menuItems.size} Fitur",
                color = TextMuted,
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Grid of 6 Columns matching the in-game UI!
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .testTag("lord_nine_grid_view")
        ) {
            items(menuItems) { item ->
                GoldMenuItemButton(
                    item = item,
                    onClick = { onMenuItemClick(item) }
                )
            }
        }
    }
}

@Composable
fun GoldMenuItemButton(
    item: LordNineMenuItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp)
            .testTag("menu_btn_${item.title.replace(" ", "_")}"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Ornate Gold Emblem Icon Box
        Box(
            modifier = Modifier
                .size(46.dp)
                .background(
                    if (item.isHighlighted)
                        Brush.radialGradient(
                            listOf(Color(0xFF382F18), DarkSlateCard)
                        )
                    else
                        Brush.radialGradient(
                            listOf(DarkSlateElevated, DarkSlateCard)
                        ),
                    RoundedCornerShape(10.dp)
                )
                .border(
                    width = if (item.isHighlighted) 1.5.dp else 1.dp,
                    color = if (item.isHighlighted) GoldBorder else SlateBorder,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (item.isHighlighted) GoldAccent else GoldLight.copy(alpha = 0.85f),
                modifier = Modifier.size(24.dp)
            )

            if (item.isHighlighted) {
                // Gold Corner Indicator
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(3.dp)
                        .size(5.dp)
                        .background(GoldBorder, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Title underneath
        Text(
            text = item.title,
            color = if (item.isHighlighted) GoldLight else TextSecondary,
            fontSize = 9.sp,
            fontWeight = if (item.isHighlighted) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 11.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}
