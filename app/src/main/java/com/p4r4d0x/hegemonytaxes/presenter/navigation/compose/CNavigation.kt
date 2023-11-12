package com.p4r4d0x.hegemonytaxes.presenter.navigation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Orange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HegemonyTopAppBar(
    uiState: UiState,
    onBackPressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            androidx.compose.material3.CenterAlignedTopAppBar(
                modifier = Modifier.height(45.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Orange,
                    titleContentColor = DarkGrey,
                ),
                title = {
                    if (uiState.displayTitleOnAppBar) {
                        AppBarTitle()
                    } else {
                        AppBarTaxes(uiState)
                    }
                },
                navigationIcon = {
                    if (!uiState.displayTitleOnAppBar) {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun AppBarTitle() {
    Column(
        Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            maxLines = 1,
            text = "HEGEMONY TAXES",
            fontSize = 16.sp,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun AppBarTaxes(uiState: UiState) {
    Column(
        Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            maxLines = 1,
            text = "Tax Multiplier: ${uiState.taxMultiplier}        Income Tax: ${uiState.incomeTax}",
            fontSize = 14.sp,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
