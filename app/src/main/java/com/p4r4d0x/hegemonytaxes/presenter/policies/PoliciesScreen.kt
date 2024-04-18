package com.p4r4d0x.hegemonytaxes.presenter.policies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun PoliciesScreen(modifier: Modifier, uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        Column(
            modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(horizontal = 2.dp, vertical = 10.dp)
        ) {

            if (uiState.policies.size > 5) {
                Divider(thickness = 20.dp, color = Color.Transparent)
                PoliciesRows(uiState.policies) { data ->
                    onEventTriggered(UiEvent.UpdatePolicy(data))
                }
                TaxRow(uiState)
                PickRolesRow(onEventTriggered)
            }
        }
    }
}

@Composable
fun PoliciesScreenScrollable(
    modifier: Modifier,
    uiState: UiState,
    onEventTriggered: (UiEvent) -> Unit
) {
    HegemonyTaxesCalculatorTheme {
        LazyColumn(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(horizontal = 2.dp, vertical = 10.dp)
        ) {
            if (uiState.policies.size > 5) {
                item { Divider(thickness = 20.dp, color = Color.Transparent) }
                item {
                    PoliciesRows(uiState.policies) { data ->
                        onEventTriggered(UiEvent.UpdatePolicy(data))
                    }
                }
                item { TaxRow(uiState) }
                item { PickRolesRow(onEventTriggered) }
            }
        }
    }
}

@Composable
fun PoliciesRows(policies: List<PolicyData>, sliderCallback: (PolicyData) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

    ) {
        PolicySliderComponent(policies[0], sliderCallback)
        PolicySliderComponent(policies[1], sliderCallback)
        PolicySliderComponent(policies[2], sliderCallback)
        PolicySliderComponent(policies[3], sliderCallback)
        PolicySliderComponent(policies[4], sliderCallback)
    }
}

@Composable
fun TaxRow(uiState: UiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MultiStyleText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            textStyleList = listOf(
                MultipleText("Tax Multiplier: ", false),
                MultipleText("${uiState.taxMultiplier}", true),
                MultipleText("      Income Tax: ", false),
                MultipleText("${uiState.incomeTax}", true)

            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(16.sp),
            regularStyle = Utils.getBoldSpanStyle(16.sp)
        )
    }
}

@Composable
fun PickRolesRow(onEventTriggered: (UiEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HegemonyButton(modifier = Modifier, text = "Pick roles") {
            onEventTriggered(UiEvent.GoPickRole)
        }
    }
}