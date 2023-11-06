package com.p4r4d0x.hegemonytaxes.presenter.roles

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun StateClassScreen(uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {

    HegemonyTaxesCalculatorTheme {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var inputOpened by remember { mutableStateOf(false) }
        var wcPopulation by remember { mutableStateOf(uiState.stateSelection.wcPopulation.toString()) }
        var mcExternalWorkedCompanies by remember { mutableStateOf(uiState.stateSelection.mcExternalCompaniesWithWorkers.toString()) }
        var mcOwnCompanies by remember { mutableStateOf(uiState.stateSelection.mcOwnCompanies.toString()) }
        var ccCompanies by remember { mutableStateOf(uiState.stateSelection.ccCompanies.toString()) }
        var ccProfit by remember { mutableStateOf(uiState.stateSelection.ccProfit.toString()) }
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
                    Modifier.onFocusChanged {
                        if (it.isFocused) {
                            focusPosition(coroutineScope, listState, 4)
                            inputOpened = true
                        } else {
                            inputOpened = false
                        }
                    },
                    roleUi, "WC population", wcPopulation, 10,
                    ImeAction.Next
                ) {
                    wcPopulation = it
                }
            }
            item {
                RoleInputText(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            focusPosition(coroutineScope, listState, 4)
                            inputOpened = true

                        } else {
                            inputOpened = false
                        }

                    },
                    roleUi = roleUi,
                    labelText = "MC external worked companies",
                    inputText = mcExternalWorkedCompanies,
                    maxValue = (STATE_MAX_COMPANIES + CAPITALIST_CLASS_MAX_COMPANIES),
                    imeAction = ImeAction.Next
                ) {
                    mcExternalWorkedCompanies = it
                }
            }
            item {
                RoleInputText(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            focusPosition(coroutineScope, listState, 4)
                            inputOpened = true

                        } else {
                            inputOpened = false
                        }

                    },
                    roleUi = roleUi,
                    labelText = "MC companies",
                    inputText = mcOwnCompanies,
                    maxValue = MIDDLE_CLASS_MAX_COMPANIES,
                    imeAction = ImeAction.Next
                ) {
                    mcOwnCompanies = it
                }
            }
            item {
                RoleInputText(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            focusPosition(coroutineScope, listState, 4)
                            inputOpened = true

                        } else {
                            inputOpened = false
                        }

                    },
                    roleUi = roleUi,
                    labelText = "CC companies",
                    inputText = ccCompanies,
                    maxValue = CAPITALIST_CLASS_MAX_COMPANIES,
                    imeAction = ImeAction.Next
                ) {
                    ccCompanies = it
                }
            }
            item {
                RoleInputText(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            focusPosition(coroutineScope, listState, 4)
                            inputOpened = true

                        } else {
                            inputOpened = false
                        }

                    },
                    roleUi = roleUi,
                    labelText = "CC profit",
                    inputText = ccProfit,
                    maxValue = Int.MAX_VALUE
                ) {
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
                    focusPosition(coroutineScope, listState, 11)
                }
            }
            item { TotalReceivedTaxes(uiState) }
            if (inputOpened) {
                item { Divider(thickness = 150.dp, color = Color.Transparent) }
            }


        }
    }
}

fun focusPosition(coroutineScope: CoroutineScope, listState: LazyListState, index: Int) {
    Log.d("ALRALR", "trying to focus position $index")
    coroutineScope.launch { listState.animateScrollToItem(index = index) }
}

@Composable
fun StateInputsDescription() {
    MultiStyleText(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
        textStyleList = listOf(
            MultipleText("Add the following inputs from other classes: \n", false),
            MultipleText("Working class: Population (", false),
            MultipleText(3.toString(), true),
            MultipleText(" to ", false),
            MultipleText(10.toString(), true),
            MultipleText(")\nMiddle class: External worked companies (", false),
            MultipleText(0.toString(), true),
            MultipleText(" to ", false),
            MultipleText((STATE_MAX_COMPANIES + CAPITALIST_CLASS_MAX_COMPANIES).toString(), true),
            MultipleText(") and own ompanies (", false),
            MultipleText(0.toString(), true),
            MultipleText(" to ", false),
            MultipleText(MIDDLE_CLASS_MAX_COMPANIES.toString(), true),
            MultipleText(")\nCapitalist class: Companies (", false),
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
                MultipleText("The total taxes received will be ", false),
                MultipleText(uiState.resultTaxes.totalTaxes.toString(), true),
                MultipleText(". \n - ", false),
                MultipleText(uiState.resultTaxes.wcTaxes.toString(), true),
                MultipleText(" from the Working Class \n - ", false),
                MultipleText(uiState.resultTaxes.mcTaxes.toString(), true),
                MultipleText(" from the Middle Class \n - ", false),
                MultipleText(uiState.resultTaxes.ccTaxes.toString(), true),
                MultipleText(" from the Capitalist Class", false)
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(16.sp),
            regularStyle = Utils.getRegularSpanStyle(16.sp)
        )
    }

}