package com.p4r4d0x.hegemonytaxes.presenter.roles

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.ResultTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.CAPITALIST_CLASS_MAX_COMPANIES
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.MIDDLE_CLASS_MAX_COMPANIES
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.STATE_MAX_COMPANIES
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.WORKING_CLASS_MAX_COMPANIES
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
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.verifyIntInputSelection

@Composable
fun MiddleClassScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        val context = LocalContext.current
        var companiesWithWorkers by remember { mutableStateOf("0") }
        var ownCompanies by remember { mutableStateOf("0") }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val roleUi = buildRoleUiData(HegemonyRole.MiddleClass)
            RoleTitleSection(roleUi)
            Divider(thickness = 20.dp, color = Color.Transparent)
            IncomeTaxDescription()
            RoleInputText(
                roleUi = roleUi,
                labelText = "External companies with workers",
                inputText = companiesWithWorkers,
                maxValue = STATE_MAX_COMPANIES + CAPITALIST_CLASS_MAX_COMPANIES
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
            Divider(thickness = 50.dp, color = Color.Transparent)
            CalculateIncomeAndEmploymentTaxesButton(
                context,
                companiesWithWorkers,
                ownCompanies,
                onEventTriggered
            )
            IncomeAndEmploymentTaxesResult(state)

        }
    }
}

@Composable
fun IncomeTaxDescription() {
    MultiStyleText(
        modifier = Modifier
            .height(95.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        textStyleList = listOf(
            MultipleText(
                "Add your current population number. Remember that the values may be in the range of ",
                false
            ),
            MultipleText(3.toString(), true),
            MultipleText(" to ", false),
            MultipleText(10.toString(), true)
        ),
        highlightedStyle = Utils.getHighlightedSpanStyle(18.sp),
        regularStyle = Utils.getRegularSpanStyle(18.sp)
    )
}

@Composable
fun CalculateIncomeAndEmploymentTaxesButton(
    context: Context,
    companiesWithWorkers: String,
    ownCompanies: String,
    onEventTriggered: (UiEvent) -> Unit
) {
    HegemonyButton(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = "Calculate total taxes"
    ) {
//        verifyIntInputSelection(
//            context = context,
//            numberInput = population,
//            intRange = (3..10)
//        ) {
        onEventTriggered(  //TODO: Add a verification to parse number to int
            UiEvent.CalculateTaxes(MiddleClassInputs(ownCompanies.toInt(), companiesWithWorkers.toInt()))
        )
//        }
    }
}

@Composable
fun IncomeAndEmploymentTaxesResult(state: UiState) {
    (state.resultTaxes as? MiddleClassTaxes)?.let { taxes ->
        MultiStyleText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            textStyleList = listOf(
                MultipleText("The Income Tax calculated is ", false),
                MultipleText(taxes.incomeTaxResult.toString(), true),
                MultipleText(", while the Employment Tax is ", false),
                MultipleText(taxes.employmentTaxResult.toString(), true),
                MultipleText(". This is a total of ", false),
                MultipleText(taxes.totalTaxes.toString(), true),
                MultipleText(". Remember that this amount has to be payed to the State.", false)
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(18.sp),
            regularStyle = Utils.getRegularSpanStyle(18.sp)
        )
    }

}