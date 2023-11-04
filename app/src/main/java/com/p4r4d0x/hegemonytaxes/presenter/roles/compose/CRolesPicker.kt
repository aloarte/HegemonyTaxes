package com.p4r4d0x.hegemonytaxes.presenter.roles.compose


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getInvolvedTaxPoliciesSummary
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.HegemonyButton
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.getRoleAvatar
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.getRoleBackground
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.getRoleMainColor

@Composable
fun RolesDescription(state: UiState) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        state.policies.getInvolvedTaxPoliciesSummary()?.let { policiesSummary ->
            MultiStyleText(
                modifier = Modifier,
                textStyleList = listOf(
                    MultipleText("Based on the current policies the tax multiplier is ", false),
                    MultipleText(state.taxMultiplier.toString(), true),
                    MultipleText(". The taxation and labor market policies stays at ", false),
                    MultipleText(policiesSummary, true),
                    MultipleText(". This values will be used in the next tax calculations.", false),
                ),
                highlightedStyle = Utils.getHighlightedSpanStyle(18.sp),
                regularStyle = Utils.getRegularSpanStyle(18.sp)
            )
        }
        Divider(thickness = 40.dp, color = Color.Transparent)
        Text(
            color = White,
            text = "Pick your role to begin simulating how many taxes you will pay/receive.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}

@Composable
fun RoleSection(role: HegemonyRole, onRoleSelected: () -> Unit) {
    HegemonyButton(
        modifier = Modifier,
        text = role.value.uppercase(),
        mainColor = getRoleMainColor(role),
        backgroundColor = getRoleBackground(role),
        drawable = getRoleAvatar(role),
        onClick = onRoleSelected
    )
}