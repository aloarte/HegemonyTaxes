package com.p4r4d0x.hegemonytaxes.presenter.policies

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.policies.compose.PolicySliderComponent
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import java.util.Locale

@Composable
fun PoliciesScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        Row(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
        ) {
            val callbackcomun: (PolicyData) -> Unit = { data ->
                Log.d("ALRALR", "Selected: ${data.state} , ${data.type}")
                onEventTriggered(UiEvent.UpdatePolicy(data))
            }

            if(state.policies.size >5){
                Column{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        PolicySliderComponent(state.policies[0], callbackcomun)
                        PolicySliderComponent(state.policies[1], callbackcomun)
                        PolicySliderComponent(state.policies[2], callbackcomun)
                        PolicySliderComponent(state.policies[3], callbackcomun)
                        PolicySliderComponent(state.policies[4], callbackcomun)
                    }

                    Row(){
                        Text(
                            color = White,
                            text = state.taxMultiplier.toString().uppercase(Locale.ROOT),
                            fontSize = 50.sp,
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(50.dp)
                        )
                    }
                }


            }


        }
    }
}
