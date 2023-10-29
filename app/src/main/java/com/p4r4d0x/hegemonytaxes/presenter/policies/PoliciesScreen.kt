package com.p4r4d0x.hegemonytaxes.presenter.policies

import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import com.p4r4d0x.hegemonytaxes.presenter.policies.compose.PolicySliderComponent
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.Grey
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import java.util.Locale

@Composable
fun PoliciesScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(horizontal = 1.dp, vertical = 10.dp)
        ) {
            val callbackcomun: (PolicyData) -> Unit = { data ->
                Log.d("ALRALR", "Selected: ${data.state} , ${data.type}")
                onEventTriggered(UiEvent.UpdatePolicy(data))
            }

            if (state.policies.size > 5) {
                PoliciesRows(state.policies, callbackcomun)
                TaxRow(state.taxMultiplier)
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
            .height(70.dp)
            .padding(1.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            color = White,
            text = "TAX MULTIPLIER: ",
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
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Orange),
            modifier = Modifier.width(250.dp),
            onClick = { onEventTriggered(UiEvent.GoPickRole) }
        ) {
            Text(
                color = DarkGrey,
                text = "Pick Role".uppercase(Locale.ROOT),
                fontSize = 16.sp,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}