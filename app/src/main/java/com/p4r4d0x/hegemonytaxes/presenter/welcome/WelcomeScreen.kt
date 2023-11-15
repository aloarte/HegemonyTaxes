package com.p4r4d0x.hegemonytaxes.presenter.welcome

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.common.HegemonyButton
import com.p4r4d0x.hegemonytaxes.presenter.common.MultiStyleText
import com.p4r4d0x.hegemonytaxes.presenter.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.UiConstants.DESCRIPTION_TEXT_SIZE
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.UiConstants.PREFERENCE_WELCOME
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils

@Composable
fun WelcomeScreen(
    modifier: Modifier,
    preferences: SharedPreferences,
    onEventTriggered: (UiEvent) -> Unit
) {
    HegemonyTaxesCalculatorTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(DarkGrey),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                painter = painterResource(R.drawable.tax_icon),
                contentDescription = "Tax icon"
            )
            Divider(thickness = 40.dp, color = Color.Transparent)
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                MultiStyleText(
                    modifier = Modifier,
                    textStyleList = listOf(
                        MultipleText("This app is intended to be used while playing the ", false),
                        MultipleText("Hegemony", true),
                        MultipleText(" board game. It's useful in calculating all the taxes that every class must pay / receive.\n\n" +
                                "I don't own any right on the assets used and the purpose of this app is just to improve the enjoyment of the game.", false)
                    ),
                    highlightedStyle = Utils.getHighlightedSpanStyle(DESCRIPTION_TEXT_SIZE),
                    regularStyle = Utils.getBoldSpanStyle(DESCRIPTION_TEXT_SIZE)
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                HegemonyButton(modifier = Modifier, text = "Continue") {
                    preferences.edit()?.let {
                        it.putBoolean(PREFERENCE_WELCOME, true)?.apply()
                    }
                    onEventTriggered(UiEvent.GoPolicySelector)
                }
            }
        }


    }
}

