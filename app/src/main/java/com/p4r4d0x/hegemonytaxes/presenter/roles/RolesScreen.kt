package com.p4r4d0x.hegemonytaxes.presenter.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getInvolvedTaxPoliciesSummary
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.ui.theme.Blue
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.Grey
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.ui.theme.NunitoFontFamily
import com.p4r4d0x.hegemonytaxes.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.ui.theme.Red
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import com.p4r4d0x.hegemonytaxes.ui.theme.Yellow

@Composable
fun RolesScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
        ) {
            Divider(thickness = 50.dp, color = Color.Transparent)
            RolesDescription(state)
            Divider(thickness = 20.dp, color = Color.Transparent)
            RoleSection(HegemonyRole.WorkingClass, onEventTriggered)
            RoleSection(HegemonyRole.MiddleClass, onEventTriggered)
            RoleSection(HegemonyRole.CapitalistClass, onEventTriggered)
            RoleSection(HegemonyRole.State, onEventTriggered)

        }
    }
}

@Composable
fun RolesDescription(state: UiState) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .height(150.dp)
            .fillMaxWidth()
    ) {
        state.policies.getInvolvedTaxPoliciesSummary()?.let { policiesSummary ->
            MultiStyleText(
                modifier = Modifier.align(Alignment.TopCenter),
                textInit = "Based on the current policies the tax multiplier is ",
                coloredText1 = state.taxMultiplier.toString(),
                textMid = ". The taxation and labor market policies stays at ",
                coloredText2 = policiesSummary,
                endText = ". This values will be used in the next tax calculations.",
                highlightColor = Orange,
                regularColor = White
            )
        }
        Text(
            color = White,
            text = "Pick your role to begin simulating how many taxes you will pay/receive.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun RoleSection(role: HegemonyRole, onEventTriggered: (UiEvent) -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(70.dp)
            .fillMaxWidth()
            .background(getRoleBackgroundColor(role))
    ) {
        Text(
            color = White,
            text = role.value.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun MultiStyleText(
    modifier: Modifier,
    textInit: String,
    coloredText1: String,
    textMid: String,
    coloredText2: String,
    endText: String,
    regularColor: Color,
    highlightColor: Color
) {
    val highlightedStyle = SpanStyle(
        letterSpacing = 0.5.sp,
        fontSize = 18.sp,
        color = highlightColor,
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Bold
    )
    val regularStyle = SpanStyle(
        letterSpacing = 0.5.sp,
        fontSize = 18.sp,
        color = regularColor,
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Bold
    )

    Text(buildAnnotatedString {
        withStyle(style = regularStyle) { append(textInit) }
        withStyle(style = highlightedStyle) { append(coloredText1) }
        withStyle(style = regularStyle) { append(textMid) }
        withStyle(style = highlightedStyle) { append(coloredText2) }
        withStyle(style = regularStyle) { append(endText) }
    }, modifier)
}


fun getRoleBackgroundColor(role: HegemonyRole) = when (role) {
    HegemonyRole.WorkingClass -> Red
    HegemonyRole.MiddleClass -> Yellow
    HegemonyRole.CapitalistClass -> Blue
    HegemonyRole.State -> Grey
}