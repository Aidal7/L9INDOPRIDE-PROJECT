package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// ==========================================
// PALETTE TOKENS: MEWAH & REGAL MMORPG GUIDE
// ==========================================

val ObsidianDark = Color(0xFF090B10)
val ObsidianDeep = Color(0xFF05060A)
val SlateGlass = Color(0xFF141824)
val SlateGlassElevated = Color(0xFF1D2333)
val SlateGlassCard = Color(0xFF10141E)
val MetallicBorder = Color(0xFF2E374A)
val MetallicBorderBright = Color(0xFF485672)

// Royal Gilded Gold Tokens
val GildedGold = Color(0xFFD4AF37)
val GildedGoldBright = Color(0xFFFFE082)
val GildedGoldLight = Color(0xFFF7E7A9)
val GildedGoldMuted = Color(0xFF8A7326)
val GildedGoldDark = Color(0xFF5A4612)

// Accent Jewel Tones
val RubyRed = Color(0xFFE53935)
val EmeraldGreen = Color(0xFF43A047)
val SapphireBlue = Color(0xFF1E88E5)
val AmethystPurple = Color(0xFF8E24AA)
val TopazAmber = Color(0xFFFFB300)
val CyanRune = Color(0xFF00E5FF)

// Compatibility aliases
val ObsidianBackground = ObsidianDark
val DarkSlate = SlateGlass
val DarkSlateElevated = SlateGlassElevated
val DarkSlateCard = SlateGlassCard
val GoldBorder = GildedGold
val GoldAccent = GildedGold
val GoldLight = GildedGoldLight
val SlateBorder = MetallicBorder
val TextPrimary = Color(0xFFF0F3F8)
val TextSecondary = Color(0xFF9DA9BA)
val TextMuted = Color(0xFF6B7687)

// Premium Metallic & Velvet Gradients
val RoyalGoldGradient = Brush.horizontalGradient(
    listOf(
        Color(0xFFFFDF79),
        Color(0xFFD4AF37),
        Color(0xFF9E7822)
    )
)

val RoyalGoldBorderGradient = Brush.linearGradient(
    listOf(
        Color(0xFFFFEEA8),
        Color(0xFFD4AF37),
        Color(0xFF4E3D12),
        Color(0xFFD4AF37)
    )
)

val SlateGlassGradient = Brush.verticalGradient(
    listOf(
        Color(0xFF1C2232),
        Color(0xFF111520)
    )
)

val DeepVignetteGradient = Brush.verticalGradient(
    listOf(
        Color(0x33090B10),
        Color(0x73090B10),
        Color(0xF0090B10)
    )
)

// Rarity Grade Glow Colors (Common s/d Mythic)
val GradeCommon = Color(0xFF9E9E9E)
val GradeCommonBg = Color(0xFF22252C)

val GradeUncommon = Color(0xFF4CAF50)
val GradeUncommonBg = Color(0xFF162E19)

val GradeRare = Color(0xFF2196F3)
val GradeRareBg = Color(0xFF11263F)

val GradeEpic = Color(0xFF9C27B0)
val GradeEpicBg = Color(0xFF2E173E)

val GradeLegendary = Color(0xFFFFC107)
val GradeLegendaryBg = Color(0xFF3F2D0C)

val GradeMythic = Color(0xFFFF5722)
val GradeMythicBg = Color(0xFF42150E)

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
