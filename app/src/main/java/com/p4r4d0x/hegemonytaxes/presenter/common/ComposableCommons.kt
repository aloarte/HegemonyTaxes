package com.p4r4d0x.hegemonytaxes.presenter.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.ui.data.MultipleText
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.MidDarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import java.util.Locale

@Composable
fun MultiStyleText(
    modifier: Modifier,
    highlightedStyle: SpanStyle,
    regularStyle: SpanStyle,
    textStyleList: List<MultipleText>
) {
    Text(buildAnnotatedString {
        textStyleList.forEach {
            withStyle(style = if (it.highlighted) highlightedStyle else regularStyle) { append(it.text) }
        }
    }, modifier,textAlign = TextAlign.Justify,)
}

@Composable
fun HegemonyButton(
    modifier: Modifier,
    text: String,
    mainColor: Color = White,
    backgroundColor: Color = DarkGrey,
    drawable: Int? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .height(55.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, mainColor),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = backgroundColor),
        onClick = { onClick() }) {
        drawable?.let {
            Image(
                painterResource(id = it),
                contentDescription = "button image",
                modifier = Modifier.size(40.dp)
            )
        }

        Text(
            color = mainColor,
            text = text.uppercase(Locale.ROOT),
            fontSize = 16.sp,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
    }
}
