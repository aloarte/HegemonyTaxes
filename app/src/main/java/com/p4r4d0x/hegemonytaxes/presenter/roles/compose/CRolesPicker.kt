package com.p4r4d0x.hegemonytaxes.presenter.roles.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getInvolvedTaxPoliciesSummary
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.ui.utils.UiConstants.DESCRIPTION_TEXT_SIZE
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
                    MultipleText(". This values will be used in the next tax calculations.\n\n", false),
                    MultipleText("Pick your role to begin simulating how many taxes you will pay / receive:", false)

                ),
                highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
                regularStyle = Utils.getRegularSpanStyle(DESCRIPTION_TEXT_SIZE)
            )
        }

    }
}

@Composable
fun RoleSection(role: HegemonyRole, onRoleSelected: () -> Unit) {
    OutlinedButton(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .height(120.dp)
            .width(120.dp),
        border = BorderStroke(1.dp, getRoleMainColor(role)),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = getRoleBackground(role)),
        onClick = onRoleSelected
    ) {

        Image(
            painterResource(id = getRoleAvatar(role)),
            contentDescription = "button image",
            modifier = Modifier.size(80.dp)
        )

    }
}