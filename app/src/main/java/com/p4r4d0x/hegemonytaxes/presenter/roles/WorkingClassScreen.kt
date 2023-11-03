package com.p4r4d0x.hegemonytaxes.presenter.roles


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.HegemonyButton
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.presenter.roles.compose.RoleInputText
import com.p4r4d0x.hegemonytaxes.presenter.roles.compose.RoleTitleSection
import com.p4r4d0x.hegemonytaxes.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.buildRoleUiData
import com.p4r4d0x.hegemonytaxes.ui.utils.Utils.verifyIntInputSelection
import java.util.Locale

@Composable
fun WorkingClassScreen(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    HegemonyTaxesCalculatorTheme {
        val context = LocalContext.current
        var population by remember { mutableStateOf("3") }
        Column(
            modifier = Modifier
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
            RoleInputText(context, roleUi, "Population", population, 10) {
                it
                population = it

            }
            Divider(thickness = 50.dp, color = Color.Transparent)
            CalculateIncomeTaxButton(context, population, onEventTriggered)
            IncomeTaxResult(state)

        }
    }
}

@Composable
fun IncomeTaxResult(state: UiState) {
    if (state.incomeTax > -1) {
        MultiStyleText(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            textStyleList = listOf(
                MultipleText("The Tax Income calculated is ", false),
                MultipleText(state.incomeTax.toString(), true),
                MultipleText(". Remember that this has to be payed to the State.", false),
            ),
            highlightedStyle = Utils.getHighlightedSpanStyle(18.sp),
            regularStyle = Utils.getRegularSpanStyle(18.sp)
        )
    }

}

@Composable
fun PopulationInputDescription() {
    MultiStyleText(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
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
fun CalculateIncomeTaxButton(
    context: Context,
    population: String,
    onEventTriggered: (UiEvent) -> Unit
) {
    HegemonyButton(modifier = Modifier, text = "Calculate income tax") {
        verifyIntInputSelection(
            context = context,
            numberInput = population,
            intRange = (3..10)
        ) {
            onEventTriggered(UiEvent.CalculateIncomeTax(population = it))
        }
    }

}


