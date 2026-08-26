package com.p4r4d0x.hegemonytaxes.presenter.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.p4r4d0x.hegemonytaxes.R
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
import com.p4r4d0x.hegemonytaxes.presenter.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.UiConstants.DESCRIPTION_TEXT_SIZE
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils.buildRoleUiData
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils.verifyIntInputsSelection

@Composable
fun MiddleClassScreenScrollable(
    modifier: Modifier,
    uiState: UiState,
    onEventTriggered: (UiEvent) -> Unit
) {
    HegemonyTaxesCalculatorTheme {
        var companiesWithWorkers by remember { mutableStateOf(uiState.mcSelection.externalCompaniesWithWorkers.toString()) }
        var ownCompanies by remember { mutableStateOf(uiState.mcSelection.ownCompanies.toString()) }

        LazyColumn(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val roleUi = buildRoleUiData(HegemonyRole.MiddleClass)
            item { RoleTitleSection(roleUi) }
            item { Divider(thickness = 20.dp, color = Color.Transparent) }
            item { MiddleClassTaxesDescription() }
            item { Divider(thickness = 10.dp, color = Color.Transparent) }
            item {
                RoleInputText(
                    roleUi = roleUi,
                    labelText = stringResource(
                        R.string.middle_class_screen_external_companies_with_workers
                    ),
                    inputText = companiesWithWorkers,
                    maxValue = STATE_MAX_COMPANIES + CAPITALIST_CLASS_MAX_COMPANIES,
                    imeAction = ImeAction.Next
                ) {
                    companiesWithWorkers = it
                }
            }
            item {
                RoleInputText(
                    roleUi = roleUi,
                    labelText = stringResource(
                        R.string.middle_class_screen_own_companies
                    ),
                    inputText = ownCompanies,
                    maxValue = MIDDLE_CLASS_MAX_COMPANIES
                ) {
                    ownCompanies = it
                }
            }

            item { Divider(thickness = 20.dp, color = Color.Transparent) }
            item {
                CalculateIncomeAndEmploymentTaxesButton(
                    companiesWithWorkers,
                    ownCompanies,
                    onEventTriggered
                )
            }
            item { IncomeAndEmploymentTaxesResult(uiState) }
        }
    }
}

@Composable
private fun MiddleClassTaxesDescription() {
    MultiStyleText(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        textStyleList = listOf(
            MultipleText(
                stringResource(R.string.middle_class_screen_description_start),
                false
            ),
            MultipleText(
                (CAPITALIST_CLASS_MAX_COMPANIES + STATE_MAX_COMPANIES).toString(),
                true
            ),
            MultipleText(
                stringResource(R.string.middle_class_screen_max),
                false
            ),
            MultipleText(
                stringResource(R.string.middle_class_screen_and_your_companies),
                false
            ),
            MultipleText(
                MIDDLE_CLASS_MAX_COMPANIES.toString(),
                true
            ),
            MultipleText(
                stringResource(
                    R.string.middle_class_screen_max
                ),
                false
            )
        ),
        highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
        regularStyle = Utils.getBoldSpanStyle(DESCRIPTION_TEXT_SIZE)
    )
}

@Composable
private fun CalculateIncomeAndEmploymentTaxesButton(
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
        text = stringResource(R.string.middle_class_screen_calculate_total_taxes)
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
private fun IncomeAndEmploymentTaxesResult(uiState: UiState) {
    (uiState.resultTaxes as? MiddleClassTaxes)?.let { taxes ->
        MultiStyleText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            textStyleList = listOf(
                MultipleText(
                    stringResource(R.string.middle_class_screen_income_tax_calculated),
                    false
                ),
                MultipleText("${taxes.incomeTaxResult}₳", true),
                MultipleText(
                    stringResource(R.string.middle_class_screen_while_employment_tax),
                    false
                ),
                MultipleText("${taxes.employmentTaxResult}₳", true),
                MultipleText(
                    stringResource(R.string.middle_class_screen_total_of),
                    false
                ),
                MultipleText("${taxes.totalTaxes}₳", true),
                MultipleText(
                    stringResource(R.string.middle_class_screen_remember_state_payment),
                    false
                )
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
            regularStyle = Utils.getBoldSpanStyle(DESCRIPTION_TEXT_SIZE)
        )
    }
}