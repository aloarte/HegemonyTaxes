package com.p4r4d0x.hegemonytaxes.presenter.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.p4r4d0x.hegemonytaxes.ui.data.MultipleText

@Composable
fun MultiStyleText(
    modifier: Modifier,
    highlightedStyle: SpanStyle,
    regularStyle: SpanStyle,
    textStyleList:List<MultipleText>
) {
    Text(buildAnnotatedString {
        textStyleList.forEach {
            withStyle(style = if(it.highlighted) highlightedStyle else regularStyle) { append(it.text) }
        }
    }, modifier)
}
