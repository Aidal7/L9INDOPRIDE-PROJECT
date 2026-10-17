package com.example.ui.components

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ItemEntity
import com.example.data.MonsterDropEntity
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
import com.example.ui.theme.getGradeBgColor
import com.example.ui.theme.getGradeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailModal(
    item: ItemEntity?,
    drops: List<MonsterDropEntity>,
    onDismiss: () -> Unit,
    onOpenFlutterCode: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    if (item == null) return

    val context = LocalContext.current
    val gradeColor = getGradeColor(item.grade)
    val gradeBgColor = getGradeBgColor(item.grade)

    // Membaca Icon dari assets/items/{item_id}.png
    val assetBitmap = remember(item.id) {
        try {
            context.assets.open("items/${item.id}.png").use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
            }
        } catch (_: Exception) {
            null
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = DarkSlate,
        scrimColor = Color.Black.copy(alpha = 0.75f),
        dragHandle = {
            // Drag Handle Emas
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(46.dp)
                    .height(4.dp)
                    .background(GoldBorder.copy(alpha = 0.7f), RoundedCornerShape(2.dp))
                    .testTag("modal_drag_handle")
            )
        },
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier
            .border(1.5.dp, GoldBorder, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .testTag("item_detail_bottom_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            // HEADER ITEM (Icon di Kiri + Nama & Grade di Kanan)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tampilan Icon Item di sebelah kiri (membaca dari assets/items/{item_id}.png)
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(12.dp),
                            ambientColor = gradeColor,
                            spotColor = gradeColor
                        )
                        .background(ObsidianBackground, RoundedCornerShape(12.dp))
                        .border(2.5.dp, gradeColor, RoundedCornerShape(12.dp))
                        .padding(6.dp)
                        .testTag("item_icon_container"),
                    contentAlignment = Alignment.Center
                ) {
                    if (assetBitmap != null) {
                        Image(
                            bitmap = assetBitmap,
                            contentDescription = item.name,
                            modifier = Modifier
                                .size(66.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .testTag("item_asset_image")
                        )
                    } else {
                        // Fallback vector icon
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = item.name,
                            tint = gradeColor,
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Detail Nama, Grade, Tipe Senjata
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.name,
                        color = TextPrimary,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.3.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.testTag("item_detail_name")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Grade Badge dengan Warna Border & Background Dinamis
                        Surface(
                            color = gradeBgColor,
                            shape = RoundedCornerShape(4.dp),
                            border = androidx.compose.foundation.BorderStroke(1.2.dp, gradeColor),
                            modifier = Modifier.testTag("item_grade_badge")
                        ) {
                            Text(
                                text = item.grade.uppercase(),
                                color = gradeColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        // Tipe Senjata Chip
                        Surface(
                            color = DarkSlateElevated,
                            shape = RoundedCornerShape(4.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                            modifier = Modifier.testTag("item_type_badge")
                        ) {
                            Text(
                                text = item.weaponType,
                                color = TextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Syarat Level
                        Text(
                            text = "Lv. ${item.levelReq}",
                            color = GoldAccent,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.testTag("item_level_req")
                        )
                    }
                }
            }

            // Garis Pembatas Gradasi Emas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, GoldBorder.copy(alpha = 0.6f), Color.Transparent)
                        )
                    )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Deskripsi Lore Item
            Surface(
                color = ObsidianBackground,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.description,
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 17.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // TABEL STATISTIK DASAR ITEM (Dengan label Estimasi Decode sesuai CATATAN)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionHeader(
                    title = "STATISTIK DASAR",
                    icon = Icons.Default.FlashOn,
                    accentColor = GoldBorder
                )
                Surface(
                    color = Color(0xFF261D0C),
                    shape = RoundedCornerShape(4.dp),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, GoldBorder.copy(alpha = 0.8f))
                ) {
                    Text(
                        text = "Data: Estimasi Decode / l9.db",
                        color = GoldAccent,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                color = ObsidianBackground,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("base_stats_table")
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        StatCell(
                            label = "Daya Serang (ATK)",
                            value = "+${item.attackPower}",
                            valueColor = GoldAccent,
                            modifier = Modifier.weight(1f)
                        )
                        StatCell(
                            label = "Tingkat Kritikal",
                            value = "${item.criticalRate}%",
                            valueColor = Color(0xFFFF8A80),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    HorizontalDivider(color = DarkSlateElevated, thickness = 1.dp)

                    Row(modifier = Modifier.fillMaxWidth()) {
                        StatCell(
                            label = "Kecepatan Serang",
                            value = "${item.attackSpeed}x",
                            valueColor = Color(0xFF80D8FF),
                            modifier = Modifier.weight(1f)
                        )
                        StatCell(
                            label = "Akurasi (Hit)",
                            value = "+${item.accuracy}",
                            valueColor = Color(0xFFA7FFEB),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    HorizontalDivider(color = DarkSlateElevated, thickness = 1.dp)

                    Row(modifier = Modifier.fillMaxWidth()) {
                        StatCell(
                            label = "Penetrasi Zirah",
                            value = "+${item.penetration}",
                            valueColor = Color(0xFFFFD180),
                            modifier = Modifier.weight(1f)
                        )
                        StatCell(
                            label = "Berat Senjata",
                            value = "${item.weight}",
                            valueColor = TextMuted,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TABEL LOKASI DROP MONSTER (SQLite l9.db)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SectionHeader(
                    title = "LOKASI DROP MONSTER (l9.db)",
                    icon = Icons.Default.Place,
                    accentColor = GoldBorder
                )

                Surface(
                    color = DarkSlateElevated,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder)
                ) {
                    Text(
                        text = "${drops.size} Monster",
                        color = GoldAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                color = ObsidianBackground,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("monster_drops_table")
            ) {
                if (drops.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Belum ada catatan monster drop untuk item ini di l9.db",
                            color = TextMuted,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        drops.forEachIndexed { index, drop ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                                    .testTag("drop_row_$index"),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Monster Type Icon
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .background(DarkSlateElevated, CircleShape)
                                        .border(1.dp, SlateBorder, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val icon = when (drop.monsterType.lowercase()) {
                                        "magic" -> Icons.Default.AutoAwesome
                                        "ranged" -> Icons.Default.GpsFixed
                                        else -> Icons.Default.Security
                                    }
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = drop.monsterType,
                                        tint = GoldAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                // Monster & Map Details
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = drop.monsterName,
                                            color = TextPrimary,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Lv. ${drop.monsterLevel}",
                                            color = GoldLight,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = drop.mapName,
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                }

                                // Drop Rate Badge
                                Surface(
                                    color = Color(0xFF142B1A),
                                    shape = RoundedCornerShape(6.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E7D32))
                                ) {
                                    Text(
                                        text = drop.dropRatePct,
                                        color = Color(0xFF81C784),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            if (index < drops.lastIndex) {
                                HorizontalDivider(
                                    color = DarkSlateElevated.copy(alpha = 0.6f),
                                    thickness = 0.8.dp,
                                    modifier = Modifier.padding(horizontal = 14.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ACTION BAR FOOTER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Button Lihat Kode Flutter
                Button(
                    onClick = onOpenFlutterCode,
                    modifier = Modifier
                        .weight(1.3f)
                        .testTag("view_flutter_code_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkSlateElevated,
                        contentColor = GoldAccent
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, GoldBorder),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = "Code",
                        modifier = Modifier.size(16.dp),
                        tint = GoldAccent
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Kode Flutter / Dart",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Button Tutup
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("close_modal_button"),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SlateBorder),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Tutup",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            color = accentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp
        )
    }
}

@Composable
private fun StatCell(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = TextSecondary,
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            color = valueColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
