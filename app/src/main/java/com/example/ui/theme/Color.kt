package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Core Theme Tokens
val ObsidianBackground = Color(0xFF0B0D12)
val DarkSlate = Color(0xFF1A1E29)
val DarkSlateElevated = Color(0xFF222736)
val DarkSlateCard = Color(0xFF141720)
val GoldBorder = Color(0xFFD4AF37)
val GoldAccent = Color(0xFFE5C358)
val GoldLight = Color(0xFFF7E7A9)
val SlateBorder = Color(0xFF2D3345)
val TextPrimary = Color(0xFFF0F3F8)
val TextSecondary = Color(0xFF9DA9BA)
val TextMuted = Color(0xFF6B7687)

// Rarity Grade Colors
val GradeCommon = Color(0xFF9E9E9E)
val GradeCommonBg = Color(0xFF22252C)

val GradeUncommon = Color(0xFF4CAF50)
val GradeUncommonBg = Color(0xFF162E19)

val GradeRare = Color(0xFF2196F3)
val GradeRareBg = Color(0xFF11263F)

val GradeEpic = Color(0xFFAB47BC)
val GradeEpicBg = Color(0xFF2E173E)

val GradeLegendary = Color(0xFFFFA726)
val GradeLegendaryBg = Color(0xFF3F260C)

val GradeMythic = Color(0xFFFF1744)
val GradeMythicBg = Color(0xFF420E19)

fun getGradeColor(grade: String): Color {
    return when (grade.lowercase()) {
        "mythic", "mitos" -> GradeMythic
        "legendary", "legendaris" -> GradeLegendary
        "epic", "epik" -> GradeEpic
        "rare", "langka" -> GradeRare
        "uncommon" -> GradeUncommon
        else -> GradeCommon
    }
}

fun getGradeBgColor(grade: String): Color {
    return when (grade.lowercase()) {
        "mythic", "mitos" -> GradeMythicBg
        "legendary", "legendaris" -> GradeLegendaryBg
        "epic", "epik" -> GradeEpicBg
        "rare", "langka" -> GradeRareBg
        "uncommon" -> GradeUncommonBg
        else -> GradeCommonBg
    }
}
