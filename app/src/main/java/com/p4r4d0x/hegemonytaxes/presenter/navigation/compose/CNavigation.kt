package com.p4r4d0x.hegemonytaxes.presenter.navigation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.presenter.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.NunitoFontFamily
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Orange


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HegemonyTopAppBar(
    uiState: UiState,
    onBackPressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .background(Orange),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Orange,
                    titleContentColor = DarkGrey,
                ),
                title = { AppBarTitle() },
                navigationIcon = {
                    if (!uiState.displayTitleOnAppBar) {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showBottomSheet = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.HelpOutline,
                            contentDescription = "Help"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        content(innerPadding)

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                val regularStyle = SpanStyle(
                    fontSize = 14.sp,
                    fontFamily = NunitoFontFamily,
                    color = DarkGrey
                )
                val highlightedStyle = SpanStyle(
                    fontSize = 14.sp,
                    fontFamily = NunitoFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Orange
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.info_sheet_title),
                        fontSize = 18.sp,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = DarkGrey
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    MultiStyleText(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        highlightedStyle = highlightedStyle,
                        regularStyle = regularStyle,
                        textStyleList = listOf(
                            MultipleText(stringResource(R.string.info_sheet_text_1), false),
                            MultipleText(stringResource(R.string.info_sheet_text_2), true),
                            MultipleText(stringResource(R.string.info_sheet_text_3), false)
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
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
