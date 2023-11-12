package com.p4r4d0x.hegemonytaxes.presenter.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.roles.compose.RoleSection
import com.p4r4d0x.hegemonytaxes.presenter.roles.compose.RolesDescription
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme

@Composable
fun RolesScreen(modifier:Modifier, uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp)
        ) {
            Divider(thickness = 50.dp, color = Color.Transparent)
            RolesDescription(uiState)
            Divider(thickness = 20.dp, color = Color.Transparent)

            Column(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    RoleSection(HegemonyRole.WorkingClass) {
                        onEventTriggered.invoke(UiEvent.GoRole(HegemonyRole.WorkingClass))
                    }

                    Divider(color = Color.Transparent, modifier = Modifier.width(20.dp))
                    RoleSection(HegemonyRole.MiddleClass) {
                        onEventTriggered.invoke(UiEvent.GoRole(HegemonyRole.MiddleClass))
                    }
                }
                Divider(thickness = 20.dp, color = Color.Transparent)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    RoleSection(HegemonyRole.CapitalistClass) {
                        onEventTriggered.invoke(UiEvent.GoRole(HegemonyRole.CapitalistClass))
                    }
                    Divider(color = Color.Transparent, modifier = Modifier.width(20.dp))

                    RoleSection(HegemonyRole.State) {
                        onEventTriggered.invoke(UiEvent.GoRole(HegemonyRole.State))
                    }
                }
            }

            Divider(thickness = 5.dp, color = Color.Transparent)


        }
    }
}