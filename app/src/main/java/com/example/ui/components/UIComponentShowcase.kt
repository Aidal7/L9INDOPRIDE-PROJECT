package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.DarkSlate
import com.example.ui.theme.DarkSlateCard
import com.example.ui.theme.DarkSlateElevated
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GradeCommon
import com.example.ui.theme.GradeEpic
import com.example.ui.theme.GradeLegendary
import com.example.ui.theme.GradeMythic
import com.example.ui.theme.GradeRare
import com.example.ui.theme.GradeUncommon
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.SlateBorder
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun UIComponentShowcase(
    isFantasyBgActive: Boolean,
    onToggleBackground: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("ui_component_showcase"),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Section 1: Background Art Preview & Switcher
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSlate,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.6f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "GAMBAR BACKGROUND MMORPG",
                            color = GoldAccent,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = "Kastil Fantasi Gelap + Partikel Emas Lord Nine",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isFantasyBgActive) "Wallpaper" else "Obsidian",
                            color = if (isFantasyBgActive) GoldLight else TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Switch(
                            checked = isFantasyBgActive,
                            onCheckedChange = onToggleBackground,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = GoldAccent,
                                checkedTrackColor = DarkSlateElevated,
                                uncheckedThumbColor = TextMuted,
                                uncheckedTrackColor = ObsidianBackground
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Wallpaper Thumbnail Preview Card with Medallion
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_lordnine_fantasy),
                        contentDescription = "Lord Nine Fantasy Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Dark gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color(0xCC0B0D12), Color(0xF00B0D12))
                                )
                            )
                    )

                    // Emblems on top
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_gold_seal),
                            contentDescription = "Lord Nine Dragon Gold Seal",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, GoldBorder, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "The Shadowed Sanctuary",
                                color = GoldLight,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Asset: assets/items/ & res/drawable/bg_lordnine_fantasy.jpg",
                                color = Color(0xFFD4AF37),
                                fontSize = 11.sp
                            )
                            Text(
                                text = "Palet Resmi: Obsidian #0B0D12, Dark Slate #1A1E29, Emas #D4AF37",
                                color = TextSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }

        // Section 2: Contoh Tombol UI (Buttons Showcase)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSlate,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.6f))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "CONTOH TOMBOL MMORPG (BUTTONS)",
                    color = GoldAccent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                Text(
                    text = "Didesain dengan nuansa gold metallic, debossed slate, dan crimson mitos.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                // 1. Tombol Emas Utama (Primary Button)
                Button(
                    onClick = { Toast.makeText(context, "Tombol Emas ditekan!", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_gold_primary"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldBorder,
                        contentColor = ObsidianBackground
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TOMBOL EMAS UTAMA (CONFIRM / ENHANCE)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.8.sp
                    )
                }

                // 2. Tombol Dark Slate (Secondary Outlined Button)
                OutlinedButton(
                    onClick = { Toast.makeText(context, "Tombol Slate ditekan!", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_slate_secondary"),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, GoldBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = DarkSlateElevated,
                        contentColor = GoldAccent
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = GoldAccent
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TOMBOL SLATE SEKUNDER (VIEW CODEX)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                }

                // 3. Tombol Mythic Raid (Danger / Boss Attack Button)
                Button(
                    onClick = { Toast.makeText(context, "Tombol Boss Raid ditekan!", Toast.LENGTH_SHORT).show() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_mythic_raid"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC62828),
                        contentColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFFFF5252)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFFFFD54F)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TANTANG WORLD BOSS (NERUCAS RAID)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.8.sp
                    )
                }
            }
        }

        // Section 3: Frame Slot Item UI (Rarity Border Showcase)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSlate,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.6f))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "FRAME SLOT ITEM DENGAN BORDER GRADE DINAMIS",
                    color = GoldAccent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                Text(
                    text = "6 Varian Rarity: Common, Uncommon, Rare, Epic, Legendary, Mythic",
                    color = TextSecondary,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Row of 6 Grade Item Slots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val grades = listOf(
                        "Common" to GradeCommon,
                        "Uncommon" to GradeUncommon,
                        "Rare" to GradeRare,
                        "Epic" to GradeEpic,
                        "Legendary" to GradeLegendary,
                        "Mythic" to GradeMythic
                    )

                    grades.forEach { (name, color) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.testTag("slot_grade_$name")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(ObsidianBackground, RoundedCornerShape(8.dp))
                                    .border(2.dp, color, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = name,
                                    tint = color,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = name.substring(0, kotlin.math.min(3, name.length)).uppercase(),
                                color = color,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }
        }

        // Section 4: Tombol Salin Kode UI Flutter
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = DarkSlateCard,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Kode Widget UI Flutter Siap Pakai",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tombol emas, target monster bar, dan slot item",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                Button(
                    onClick = {
                        val sampleFlutterButtons = """
// Tombol Emas Utama Lord Nine (Flutter)
ElevatedButton.icon(
  onPressed: () {},
  icon: const Icon(Icons.shield, color: Color(0xFF0B0D12)),
  label: const Text('ENHANCE GEAR', style: TextStyle(fontWeight: FontWeight.bold, color: Color(0xFF0B0D12))),
  style: ElevatedButton.styleFrom(
    backgroundColor: const Color(0xFFD4AF37),
    padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 14),
    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
  ),
);

// Tombol Dark Slate Sekunder (Flutter)
OutlinedButton.icon(
  onPressed: () {},
  icon: const Icon(Icons.auto_awesome, color: Color(0xFFE5C358)),
  label: const Text('VIEW CODEX', style: TextStyle(color: Color(0xFFE5C358), fontWeight: FontWeight.bold)),
  style: OutlinedButton.styleFrom(
    backgroundColor: const Color(0xFF222736),
    side: const BorderSide(color: Color(0xFFD4AF37), width: 1.2),
    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
  ),
);
                        """.trimIndent()
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Lord Nine Flutter UI Code", sampleFlutterButtons)
                        clipboard.setPrimaryClip(clip)
                        Toast.makeText(context, "Snippet Flutter UI disalin ke clipboard!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkSlateElevated,
                        contentColor = GoldAccent
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Salin Kode", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
