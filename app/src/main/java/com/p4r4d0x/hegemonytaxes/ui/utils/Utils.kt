package com.p4r4d0x.hegemonytaxes.ui.utils

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.ui.theme.NunitoFontFamily
import com.p4r4d0x.hegemonytaxes.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.ui.theme.White

object Utils {

    fun getHighlightedSpanStyle(textSize: TextUnit) = SpanStyle(
    letterSpacing = 0.5.sp,
    fontSize = textSize,
    color = Orange,
    fontFamily = NunitoFontFamily,
    fontWeight = FontWeight.Bold
    )

    fun getRegularSpanStyle(textSize: TextUnit) = SpanStyle(
        letterSpacing = 0.5.sp,
        fontSize = textSize,
        color = White,
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Bold
    )
}