package com.p4r4d0x.hegemonytaxes.presenter.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.NunitoFontFamily
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.White

@Composable
fun TaxSubToolbar(uiState: UiState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Orange)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.tax_row_tax_multiplier).trimEnd(':', ' '),
                    fontSize = 11.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = DarkGrey
                )
                Text(
                    text = "${uiState.taxMultiplier}",
                    fontSize = 14.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = DarkGrey
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.tax_row_income_tax),
                    fontSize = 11.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = DarkGrey
                )
                Text(
                    text = "${uiState.incomeTax}",
                    fontSize = 14.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = DarkGrey
                )
            }
        }
    }
}
