package com.p4r4d0x.hegemonytaxes.presenter.roles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
import com.p4r4d0x.hegemonytaxes.presenter.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.presenter.ui.data.RoleUiData
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.White
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.UiConstants.DESCRIPTION_TEXT_SIZE
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils.buildRoleUiData

@Composable
fun StateClassScreen(modifier: Modifier, uiState: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val roleUi = buildRoleUiData(HegemonyRole.State)

    HegemonyTaxesCalculatorTheme {
        var showDialog by remember { mutableStateOf(false) }
        if (showDialog) {
            InputsDialog(uiState, roleUi, onEventTriggered) {
                showDialog = false
            }
        }

        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoleTitleSection(roleUi)
            Divider(thickness = 20.dp, color = Color.Transparent)
            StateInputsDescription()
            Divider(thickness = 10.dp, color = Color.Transparent)
            HegemonyButton(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "Input data"
            ) {
                showDialog = true
            }
            TotalReceivedTaxes(uiState)
        }
    }
}

@Composable
fun StateClassScreenScrollable(
    modifier: Modifier,
    uiState: UiState,
    onEventTriggered: (UiEvent) -> Unit
) {
    val roleUi = buildRoleUiData(HegemonyRole.State)

    HegemonyTaxesCalculatorTheme {
        var showDialog by remember { mutableStateOf(false) }
        if (showDialog) {
            InputsDialogScrollable(uiState, roleUi, onEventTriggered) {
                showDialog = false
            }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { RoleTitleSection(roleUi) }
            item { Divider(thickness = 20.dp, color = Color.Transparent) }
            item { StateInputsDescription() }
            item { Divider(thickness = 10.dp, color = Color.Transparent) }
            item {
                HegemonyButton(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Input data"
                ) {
                    showDialog = true
                }
            }
            item { TotalReceivedTaxes(uiState) }
        }
    }
}

@Composable
fun InputsDialog(
    uiState: UiState,
    roleUi: RoleUiData,
    onEventTriggered: (UiEvent) -> Unit,
    onDismissed: () -> Unit
) {
    var wcPopulation by remember { mutableStateOf(uiState.stateSelection.wcPopulation.toString()) }
    var mcExternalWorkedCompanies by remember { mutableStateOf(uiState.stateSelection.mcExternalCompaniesWithWorkers.toString()) }
    var mcOwnCompanies by remember { mutableStateOf(uiState.stateSelection.mcOwnCompanies.toString()) }
    var ccCompanies by remember { mutableStateOf(uiState.stateSelection.ccCompanies.toString()) }
    var ccProfit by remember { mutableStateOf(uiState.stateSelection.ccProfit.toString()) }

    Dialog(
        onDismissRequest = onDismissed,
        content = {
            OutlinedCard(
                border = BorderStroke(1.dp, White),
                colors = CardDefaults.outlinedCardColors(
                    contentColor = DarkGrey,
                    containerColor = DarkGrey
                ),
                modifier = Modifier
                    .fillMaxHeight(0.80f)
                    .fillMaxWidth(1f),
                shape = RoundedCornerShape(8.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        color = White,
                        text = "CLASSES DATA",
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }


                Divider(thickness = 10.dp, color = Color.Transparent)
                RoleInputText(
                    roleUi = roleUi,
                    labelText = "WC population",
                    inputText = wcPopulation,
                    maxValue = 10,
                    imeAction = ImeAction.Next
                ) {
                    wcPopulation = it
                }
                RoleInputText(
                    roleUi = roleUi,
                    labelText = "MC external worked companies",
                    inputText = mcExternalWorkedCompanies,
                    maxValue = (STATE_MAX_COMPANIES + CAPITALIST_CLASS_MAX_COMPANIES),
                    imeAction = ImeAction.Next
                ) {
                    mcExternalWorkedCompanies = it
                }

                RoleInputText(
                    roleUi = roleUi,
                    labelText = "MC companies",
                    inputText = mcOwnCompanies,
                    maxValue = MIDDLE_CLASS_MAX_COMPANIES,
                    imeAction = ImeAction.Next
                ) {
                    mcOwnCompanies = it
                }

                RoleInputText(
                    roleUi = roleUi,
                    labelText = "CC companies",
                    inputText = ccCompanies,
                    maxValue = CAPITALIST_CLASS_MAX_COMPANIES,
                    imeAction = ImeAction.Next
                ) {
                    ccCompanies = it
                }

                RoleInputText(
                    roleUi = roleUi,
                    labelText = "CC profit",
                    inputText = ccProfit,
                    maxValue = Int.MAX_VALUE
                ) {
                    ccProfit = it
                }

                Divider(thickness = 20.dp, color = Color.Transparent)
                StateCalculateTotalTaxesButton(
                    wcPopulation,
                    mcExternalWorkedCompanies,
                    mcOwnCompanies,
                    ccCompanies,
                    ccProfit,
                    onEventTriggered,
                    onDismissed
                )
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}

@Composable
fun InputsDialogScrollable(
    uiState: UiState,
    roleUi: RoleUiData,
    onEventTriggered: (UiEvent) -> Unit,
    onDismissed: () -> Unit
) {
    var wcPopulation by remember { mutableStateOf(uiState.stateSelection.wcPopulation.toString()) }
    var mcExternalWorkedCompanies by remember { mutableStateOf(uiState.stateSelection.mcExternalCompaniesWithWorkers.toString()) }
    var mcOwnCompanies by remember { mutableStateOf(uiState.stateSelection.mcOwnCompanies.toString()) }
    var ccCompanies by remember { mutableStateOf(uiState.stateSelection.ccCompanies.toString()) }
    var ccProfit by remember { mutableStateOf(uiState.stateSelection.ccProfit.toString()) }

    Dialog(
        onDismissRequest = onDismissed,
        content = {
            OutlinedCard(
                border = BorderStroke(1.dp, White),
                colors = CardDefaults.outlinedCardColors(
                    contentColor = DarkGrey,
                    containerColor = DarkGrey
                ),
                modifier = Modifier
                    .fillMaxHeight(0.80f)
                    .fillMaxWidth(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                color = White,
                                text = "CLASSES DATA",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.labelMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    item { Divider(thickness = 10.dp, color = Color.Transparent) }
                    item {
                        RoleInputText(
                            roleUi = roleUi,
                            labelText = "WC population",
                            inputText = wcPopulation,
                            maxValue = 10,
                            imeAction = ImeAction.Next
                        ) {
                            wcPopulation = it
                        }
                    }
                    item {
                        RoleInputText(
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
                            onEventTriggered,
                            onDismissed
                        )
                    }
                }


            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
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
    onButtonPressed: () -> Unit = {}
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
        highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
        regularStyle = Utils.getBoldSpanStyle(DESCRIPTION_TEXT_SIZE)
    )
}

@Composable
fun TotalReceivedTaxes(uiState: UiState) {
    (uiState.resultTaxes as? StateClassTaxes)?.let {
        MultiStyleText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            textStyleList = listOf(
                MultipleText("The total taxes received will be ", false),
                MultipleText("${uiState.resultTaxes.totalTaxes}₳", true),
                MultipleText(". \n - ", false),
                MultipleText("${uiState.resultTaxes.wcTaxes}₳", true),
                MultipleText(" from the Working Class \n - ", false),
                MultipleText("${uiState.resultTaxes.mcTaxes}₳", true),
                MultipleText(" from the Middle Class \n - ", false),
                MultipleText("${uiState.resultTaxes.ccTaxes}₳", true),
                MultipleText(" from the Capitalist Class", false)
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
            regularStyle = Utils.getBoldSpanStyle(DESCRIPTION_TEXT_SIZE)
        )
    }

}
