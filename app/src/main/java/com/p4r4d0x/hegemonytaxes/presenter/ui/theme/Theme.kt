package com.p4r4d0x.hegemonytaxes.presenter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun HegemonyTaxesCalculatorTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        typography = typography,
        content = content
    )
}