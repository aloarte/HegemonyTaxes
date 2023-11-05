package com.p4r4d0x.hegemonytaxes.presenter.roles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassTaxes
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
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.buildRoleUiData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun StateClassScreen(uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()


        var wcPopulation by remember { mutableStateOf("3") }
        var mcExternalWorkedCompanies by remember { mutableStateOf("0") }
        var mcOwnCompanies by remember { mutableStateOf("0") }
        var ccCompanies by remember { mutableStateOf("0") }
        var ccProfit by remember { mutableStateOf("0") }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val roleUi = buildRoleUiData(HegemonyRole.State)
            item { RoleTitleSection(roleUi) }
            item { Divider(thickness = 20.dp, color = Color.Transparent) }
            item { StateInputsDescription() }
            item { Divider(thickness = 10.dp, color = Color.Transparent) }
            item {
                RoleInputText(
                    roleUi, "Working class population", wcPopulation, 10,
                    ImeAction.Next
                ) {
                    wcPopulation = it
                }
            }
            item {
                RoleInputText(
                    roleUi,
                    "Middle class external worked companies",
                    mcExternalWorkedCompanies,
                    (STATE_MAX_COMPANIES + CAPITALIST_CLASS_MAX_COMPANIES),
                    ImeAction.Next
                ) {
                    mcExternalWorkedCompanies = it
                }
            }
            item {
                RoleInputText(
                    roleUi,
                    "Middle class companies",
                    mcOwnCompanies,
                    MIDDLE_CLASS_MAX_COMPANIES,
                    ImeAction.Next
                ) {
                    mcOwnCompanies = it
                }
            }
            item {
                RoleInputText(
                    roleUi,
                    "Capitalist class companies",
                    ccCompanies,
                    CAPITALIST_CLASS_MAX_COMPANIES,
                    ImeAction.Next
                ) {
                    ccCompanies = it
                }
            }
            item {
                RoleInputText(roleUi, "Capitalist profit", ccProfit, Int.MAX_VALUE) {
                    ccProfit = it
                }
            }
            item { Divider(thickness = 20.dp, color = Color.Transparent) }
            item {
                StateCalculateTotalTaxesButton(
                    wcPopulation,
                    mcExternalWorkedCompanies,
                    mcOwnCompanies,
                    ccCompanies,
                    ccProfit,
                    onEventTriggered
                ) {
                    coroutineScope.launch { listState.animateScrollToItem(index = 11) }
                }
            }
            item { TotalReceivedTaxes(uiState) }
        }
    }
}

@Composable
fun StateInputsDescription() {
    MultiStyleText(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        textStyleList = listOf(
            MultipleText("Add the following inputs from other classes: \n", false),
            MultipleText(" · Working class: Population (", false),
            MultipleText(3.toString(), true),
            MultipleText(" to ", false),
            MultipleText(10.toString(), true),
            MultipleText(")\n · Middle class: External worked companies (", false),
            MultipleText(0.toString(), true),
            MultipleText(" to ", false),
            MultipleText((STATE_MAX_COMPANIES + CAPITALIST_CLASS_MAX_COMPANIES).toString(), true),
            MultipleText(") and own ompanies (", false),
            MultipleText(0.toString(), true),
            MultipleText(" to ", false),
            MultipleText(MIDDLE_CLASS_MAX_COMPANIES.toString(), true),
            MultipleText(")\n · Capitalist class: Companies (", false),
            MultipleText(0.toString(), true),
            MultipleText(" to ", false),
            MultipleText(CAPITALIST_CLASS_MAX_COMPANIES.toString(), true),
            MultipleText(") and profit.", false),
        ),
        highlightedStyle = Utils.getHighlightedSpanStyle(16.sp),
        regularStyle = Utils.getRegularSpanStyle(16.sp)
    )
}

@Composable
fun StateCalculateTotalTaxesButton(
    wcPopulation: String,
    mcExternalWorkedCompanies: String,
    mcOwnCompanies: String,
    ccCompanies: String,
    ccProfit: String,
    onEventTriggered: (UiEvent) -> Unit,
    onButtonPressed: () -> Unit
) {
    val inputs = listOf(
        wcPopulation to (3..10),
        mcExternalWorkedCompanies to (0..(CAPITALIST_CLASS_MAX_COMPANIES + STATE_MAX_COMPANIES)),
        mcOwnCompanies to (0..MIDDLE_CLASS_MAX_COMPANIES),
        ccCompanies to (0..CAPITALIST_CLASS_MAX_COMPANIES),
        ccProfit to (0..Int.MAX_VALUE)
    )
    HegemonyButton(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = "Calculate total taxes"
    ) {
        if (Utils.verifyIntInputsSelection(inputs)) {
            onEventTriggered(
                UiEvent.CalculateTaxes(
                    StateClassInputs(
                        wcPopulation = wcPopulation.toInt(),
                        mcExternalCompaniesWithWorkers = mcExternalWorkedCompanies.toInt(),
                        mcOwnCompanies = mcOwnCompanies.toInt(),
                        ccCompanies = ccCompanies.toInt(),
                        ccProfit = ccProfit.toInt()
                    )
                )
            )
            onButtonPressed()
        }
    }
}

@Composable
fun TotalReceivedTaxes(uiState: UiState) {
    (uiState.resultTaxes as? StateClassTaxes)?.let {
        MultiStyleText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            textStyleList = listOf(
                MultipleText("The total taxes receives will be ", false),
                MultipleText(uiState.resultTaxes.totalTaxes.toString(), true),
                MultipleText(". \n - ", false),
                MultipleText(uiState.resultTaxes.wcTaxes.toString(), true),
                MultipleText(" received from the Working Class \n - ", false),
                MultipleText(uiState.resultTaxes.mcTaxes.toString(), true),
                MultipleText(" received from the Middle Class \n - ", false),
                MultipleText(uiState.resultTaxes.ccTaxes.toString(), true),
                MultipleText(" received from the Capitalist Class", false)
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(16.sp),
            regularStyle = Utils.getRegularSpanStyle(16.sp)
        )
    }

}