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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.CAPITALIST_CLASS_MAX_COMPANIES
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

@Composable
fun CapitalistClassScreen(modifier:Modifier,uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        var companies by remember { mutableStateOf(uiState.ccSelection.companies.toString()) }
        var profit by remember { mutableStateOf("0") }

        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val roleUi = buildRoleUiData(HegemonyRole.CapitalistClass)
            RoleTitleSection(roleUi)
            Divider(thickness = 20.dp, color = Color.Transparent)
            CapitalistClassTaxesDescription()
            Divider(thickness = 10.dp, color = Color.Transparent)
            RoleInputText(
                roleUi = roleUi,
                labelText = "Companies",
                inputText = companies,
                maxValue = CAPITALIST_CLASS_MAX_COMPANIES,
                imeAction = ImeAction.Next
            ) {
                companies = it
            }
            RoleInputText(
                roleUi = roleUi,
                labelText = "Profit",
                inputText = profit,
                maxValue = Integer.MAX_VALUE
            ) {
                profit = it
            }
            Divider(thickness = 20.dp, color = Color.Transparent)
            CalculateEmploymentAndCorporateTaxesButton(
                companies,
                profit,
                onEventTriggered
            )
            EmploymentAndCorporateTaxesResult(uiState)

        }
    }
}

@Composable
fun CapitalistClassTaxesDescription() {
    MultiStyleText(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        textStyleList = listOf(
            MultipleText(
                "Add your companies (", false
            ),
            MultipleText(CAPITALIST_CLASS_MAX_COMPANIES.toString(), true),
            MultipleText(" max) and your profit.", false)
        ),
        highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
        regularStyle = Utils.getRegularSpanStyle(DESCRIPTION_TEXT_SIZE)
    )
}

@Composable
fun CalculateEmploymentAndCorporateTaxesButton(
    companies: String,
    profit: String,
    onEventTriggered: (UiEvent) -> Unit
) {
    val inputs = listOf(
        companies to (0..CAPITALIST_CLASS_MAX_COMPANIES),
        profit to (0..Int.MAX_VALUE)
    )

    HegemonyButton(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = "Calculate total taxes"
    ) {
        if (Utils.verifyIntInputsSelection(inputs)) {
            onEventTriggered(
                UiEvent.CalculateTaxes(
                    CapitalistClassInputs(
                        companies = companies.toInt(),
                        profit = profit.toInt()
                    )
                )
            )
        }
    }
}

@Composable
fun EmploymentAndCorporateTaxesResult(uiState: UiState) {
    (uiState.resultTaxes as? CapitalistClassTaxes)?.let { taxes ->
        MultiStyleText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            textStyleList = listOf(
                MultipleText("The Employment Tax calculated is ", false),
                MultipleText("${taxes.employmentTaxResult}₳", true),
                MultipleText(", while the Corporate Tax is ", false),
                MultipleText("${taxes.corporateTaxResult}₳", true),
                MultipleText(". This is a total of ", false),
                MultipleText("${taxes.totalTaxes}₳", true),
                MultipleText(". Remember that this amount has to be payed to the State.", false)
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(16.sp),
            regularStyle = Utils.getRegularSpanStyle(16.sp)
        )
    }

}