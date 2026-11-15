package com.p4r4d0x.hegemonytaxes.presenter.policies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.HegemonyButton
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.presenter.policies.compose.PolicySliderComponent
import com.p4r4d0x.hegemonytaxes.presenter.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils

@Composable
fun PoliciesScreenScrollable(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onEventTriggered: (UiEvent) -> Unit
) {
    HegemonyTaxesCalculatorTheme {
        LazyColumn(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(horizontal = 5.dp, vertical = 10.dp)
        ) {
            if (uiState.policies.size > 5) {
                item { Divider(thickness = 20.dp, color = Color.Transparent) }
                item {
                    PoliciesRows(uiState.policies) { data ->
                        onEventTriggered(UiEvent.UpdatePolicy(data))
                    }
                }
                item { TaxRow(uiState) }
                item { PoliciesActionsRow(onEventTriggered) }
            }
        }
    }
}

@Composable
private fun PoliciesRows(policies: List<PolicyData>, sliderCallback: (PolicyData) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

    ) {
        PolicySliderComponent(policies[0], sliderCallback)
        Divider(thickness = 2.dp, color = Color.Transparent)
        PolicySliderComponent(policies[1], sliderCallback)
        Divider(thickness = 2.dp, color = Color.Transparent)
        PolicySliderComponent(policies[2], sliderCallback)
        Divider(thickness = 2.dp, color = Color.Transparent)
        PolicySliderComponent(policies[3], sliderCallback)
        Divider(thickness = 2.dp, color = Color.Transparent)
        PolicySliderComponent(policies[4], sliderCallback)
    }
}

@Composable
private fun TaxRow(uiState: UiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MultiStyleText(
            textStyleList = listOf(
                MultipleText(
                    stringResource(R.string.tax_row_tax_multiplier)+": ",
                    false
                ),
                MultipleText("${uiState.taxMultiplier}", true),
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(16.sp),
            regularStyle = Utils.getBoldSpanStyle(16.sp)
        )
        MultiStyleText(
            textStyleList = listOf(
                MultipleText(
                    stringResource(R.string.tax_row_income_tax)+": ",
                    false
                ),
                MultipleText("${uiState.incomeTax}", true)
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(16.sp),
            regularStyle = Utils.getBoldSpanStyle(16.sp)
        )
    }
}

@Composable
private fun PoliciesActionsRow(onEventTriggered: (UiEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HegemonyButton(
            modifier = Modifier,
            text = stringResource(R.string.pick_roles_row_pick_roles)
        ) {
            onEventTriggered(UiEvent.GoPickRole)
        }
        Divider(thickness = 15.dp, color = Color.Transparent)
        HegemonyButton(
            modifier = Modifier,
            text = stringResource(R.string.reset_policies)
        ) {
            onEventTriggered(UiEvent.ResetPolicies)
        }
    }
}
