package com.p4r4d0x.hegemonytaxes.presenter.policies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.HegemonyButton
import com.p4r4d0x.hegemonytaxes.presenter.policies.compose.PolicySliderComponent
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import java.util.Locale

@Composable
fun PoliciesScreen(modifier:Modifier, uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        Column(
            modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(horizontal = 1.dp, vertical = 10.dp)
        ) {
            val callbackcomun: (PolicyData) -> Unit = { data ->
                onEventTriggered(UiEvent.UpdatePolicy(data))
            }

            if (uiState.policies.size > 5) {
                Divider(thickness = 20.dp, color = Color.Transparent)

                PoliciesRows(uiState.policies, callbackcomun)
                TaxRow(uiState.taxMultiplier)
                PickRolesRow(onEventTriggered)
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
fun TaxRow(taxMultiplier: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            color = White,
            text = "Tax multiplier: ",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Text(
            color = White,
            text = taxMultiplier.toString().uppercase(Locale.ROOT),
            fontSize = 50.sp,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(50.dp)
        )
    }
}

@Composable
fun PickRolesRow(onEventTriggered: (UiEvent) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HegemonyButton(modifier = Modifier, text = "Pick roles") {
            onEventTriggered(UiEvent.GoPickRole)
        }
    }
}