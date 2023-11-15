package com.p4r4d0x.hegemonytaxes.presenter.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassTaxes
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.HegemonyButton
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.presenter.roles.compose.RoleInputText
import com.p4r4d0x.hegemonytaxes.presenter.roles.compose.RoleTitleSection
import com.p4r4d0x.hegemonytaxes.presenter.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.UiConstants.DESCRIPTION_TEXT_SIZE
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils.buildRoleUiData
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils.verifyIntInputsSelection

@Composable
fun WorkingClassScreen(modifier:Modifier,uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        var population by remember { mutableStateOf(uiState.wcSelection.population.toString()) }
        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val roleUi = buildRoleUiData(HegemonyRole.WorkingClass)
            RoleTitleSection(roleUi)
            Divider(thickness = 20.dp, color = Color.Transparent)
            PopulationInputDescription()
            Divider(thickness = 10.dp, color = Color.Transparent)
            RoleInputText(
                roleUi = roleUi,
                labelText = "Population",
                inputText = population,
                maxValue = 10
            ) {
                population = it
            }
            Divider(thickness = 20.dp, color = Color.Transparent)
            CalculateIncomeTaxButton(population, onEventTriggered)
            IncomeTaxResult(uiState)

        }
    }
}

@Composable
fun PopulationInputDescription() {
    MultiStyleText(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        textStyleList = listOf(
            MultipleText(
                "Add your current population number. Remember that the values may be in the range of ",
                false
            ),
            MultipleText(3.toString(), true),
            MultipleText(" to ", false),
            MultipleText(10.toString(), true)
        ),
        highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
        regularStyle = Utils.getBoldSpanStyle(DESCRIPTION_TEXT_SIZE)
    )
}

@Composable
fun CalculateIncomeTaxButton(
    population: String,
    onEventTriggered: (UiEvent) -> Unit
) {
    val inputs = listOf(population to (3..10))
    HegemonyButton(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = "Calculate total taxes"
    ) {
        if (verifyIntInputsSelection(inputs)) {
            onEventTriggered(UiEvent.CalculateTaxes(WorkingClassInputs(population.toInt())))
        }

    }
}

@Composable
fun IncomeTaxResult(uiState: UiState) {
    (uiState.resultTaxes as? WorkingClassTaxes)?.let {
        MultiStyleText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            textStyleList = listOf(
                MultipleText("The Income Tax calculated is ", false),
                MultipleText("${uiState.resultTaxes.incomeTaxResult}₳", true),
                MultipleText(". Remember that this amount has to be payed to the State.", false),
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
            regularStyle = Utils.getBoldSpanStyle(DESCRIPTION_TEXT_SIZE)
        )
    }

}