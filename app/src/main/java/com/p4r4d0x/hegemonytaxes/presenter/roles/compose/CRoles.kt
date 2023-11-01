package com.p4r4d0x.hegemonytaxes.presenter.roles.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getInvolvedTaxPoliciesSummary
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.ui.theme.Blue
import com.p4r4d0x.hegemonytaxes.ui.theme.Grey
import com.p4r4d0x.hegemonytaxes.ui.theme.Red
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import com.p4r4d0x.hegemonytaxes.ui.theme.Yellow
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.getHighlightedSpanStyle
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.getRegularSpanStyle

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
                textStyleList = listOf(MultipleText("Based on the current policies the tax multiplier is ",false),
                    MultipleText(state.taxMultiplier.toString(),true),
                    MultipleText(". The taxation and labor market policies stays at ",false),
                    MultipleText(policiesSummary,true),
                    MultipleText(". This values will be used in the next tax calculations.",false),
                    ),
                highlightedStyle = getHighlightedSpanStyle(18.sp),
                regularStyle = getRegularSpanStyle(18.sp)
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
fun RoleSection(role: HegemonyRole, onRoleSelected: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(70.dp)
            .fillMaxWidth()
            .clickable { onRoleSelected() }
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


fun getRoleBackgroundColor(role: HegemonyRole) = when (role) {
    HegemonyRole.WorkingClass -> Red
    HegemonyRole.MiddleClass -> Yellow
    HegemonyRole.CapitalistClass -> Blue
    HegemonyRole.State -> Grey
}