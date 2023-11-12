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
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.CAPITALIST_CLASS_MAX_COMPANIES
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.MIDDLE_CLASS_MAX_COMPANIES
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.STATE_MAX_COMPANIES
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.HegemonyButton
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.presenter.roles.compose.RoleInputText
import com.p4r4d0x.hegemonytaxes.presenter.roles.compose.RoleTitleSection
import com.p4r4d0x.hegemonytaxes.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.ui.utils.UiConstants.DESCRIPTION_TEXT_SIZE
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.buildRoleUiData
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.verifyIntInputsSelection

@Composable
fun MiddleClassScreen(modifier: Modifier, uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        var companiesWithWorkers by remember { mutableStateOf(uiState.mcSelection.externalCompaniesWithWorkers.toString()) }
        var ownCompanies by remember { mutableStateOf(uiState.mcSelection.ownCompanies.toString()) }

        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val roleUi = buildRoleUiData(HegemonyRole.MiddleClass)
            RoleTitleSection(roleUi)
            Divider(thickness = 20.dp, color = Color.Transparent)
            MiddleClassTaxesDescription()
            Divider(thickness = 10.dp, color = Color.Transparent)
            RoleInputText(
                roleUi = roleUi,
                labelText = "External companies with workers",
                inputText = companiesWithWorkers,
                maxValue = STATE_MAX_COMPANIES + CAPITALIST_CLASS_MAX_COMPANIES,
                imeAction = ImeAction.Next
            ) {
                companiesWithWorkers = it
            }
            RoleInputText(
                roleUi = roleUi,
                labelText = "Own Companies",
                inputText = ownCompanies,
                maxValue = MIDDLE_CLASS_MAX_COMPANIES
            ) {
                ownCompanies = it
            }
            Divider(thickness = 20.dp, color = Color.Transparent)
            CalculateIncomeAndEmploymentTaxesButton(
                companiesWithWorkers,
                ownCompanies,
                onEventTriggered
            )
            IncomeAndEmploymentTaxesResult(uiState)
        }
    }
}

@Composable
fun MiddleClassTaxesDescription() {
    MultiStyleText(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        textStyleList = listOf(
            MultipleText(
                "Add the companies that aren't yours where you have workers (", false
            ),
            MultipleText((CAPITALIST_CLASS_MAX_COMPANIES + STATE_MAX_COMPANIES).toString(), true),
            MultipleText(" max) and your companies (", false),
            MultipleText(MIDDLE_CLASS_MAX_COMPANIES.toString(), true),
            MultipleText(" max).", false)
        ),
        highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
        regularStyle = Utils.getRegularSpanStyle(DESCRIPTION_TEXT_SIZE)
    )
}

@Composable
fun CalculateIncomeAndEmploymentTaxesButton(
    companiesWithWorkers: String,
    ownCompanies: String,
    onEventTriggered: (UiEvent) -> Unit
) {
    val inputs = listOf(
        companiesWithWorkers to (0..(CAPITALIST_CLASS_MAX_COMPANIES + STATE_MAX_COMPANIES)),
        ownCompanies to (0..MIDDLE_CLASS_MAX_COMPANIES)
    )

    HegemonyButton(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = "Calculate total taxes"
    ) {
        if (verifyIntInputsSelection(inputs)) {
            onEventTriggered(
                UiEvent.CalculateTaxes(
                    MiddleClassInputs(
                        externalCompaniesWithWorkers = companiesWithWorkers.toInt(),
                        ownCompanies = ownCompanies.toInt()
                    )
                )
            )
        }
    }
}

@Composable
fun IncomeAndEmploymentTaxesResult(uiState: UiState) {
    (uiState.resultTaxes as? MiddleClassTaxes)?.let { taxes ->
        MultiStyleText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            textStyleList = listOf(
                MultipleText("The Income Tax calculated is ", false),
                MultipleText("${taxes.incomeTaxResult}₳", true),
                MultipleText(", while the Employment Tax is ", false),
                MultipleText("${taxes.employmentTaxResult}₳", true),
                MultipleText(". This is a total of ", false),
                MultipleText("${taxes.totalTaxes}₳", true),
                MultipleText(". Remember that this amount has to be payed to the State.", false)
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(16.sp),
            regularStyle = Utils.getRegularSpanStyle(16.sp)
        )
    }
}