package com.p4r4d0x.hegemonytaxes.presenter.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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



            RoleSection(HegemonyRole.WorkingClass) {
                onEventTriggered.invoke(UiEvent.GoRole(HegemonyRole.WorkingClass))
            }
            RoleSection(HegemonyRole.MiddleClass) {
                onEventTriggered.invoke(UiEvent.GoRole(HegemonyRole.MiddleClass))
            }
            RoleSection(HegemonyRole.CapitalistClass) {
                onEventTriggered.invoke(UiEvent.GoRole(HegemonyRole.CapitalistClass))
            }
            RoleSection(HegemonyRole.State) {
                onEventTriggered.invoke(UiEvent.GoRole(HegemonyRole.State))
            }
        }
    }
}