package com.p4r4d0x.hegemonytaxes.presenter.policies.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.PolicyType
import com.p4r4d0x.hegemonytaxes.domain.PolicyData
import com.p4r4d0x.hegemonytaxes.domain.PolicyState
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.FiscalPolicy
import com.p4r4d0x.hegemonytaxes.ui.theme.ForeignTrade
import com.p4r4d0x.hegemonytaxes.ui.theme.Immigration
import com.p4r4d0x.hegemonytaxes.ui.theme.LaborMarket
import com.p4r4d0x.hegemonytaxes.ui.theme.MidDarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.Taxation
import com.p4r4d0x.hegemonytaxes.ui.theme.WEEducation
import com.p4r4d0x.hegemonytaxes.ui.theme.WEHealthcare
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import java.util.Locale


fun getPolicyColor(policyType: PolicyType) = when (policyType) {
    PolicyType.FiscalPolicy -> FiscalPolicy
    PolicyType.LaborMarket -> LaborMarket
    PolicyType.Taxation -> Taxation
    PolicyType.WEHealthcare -> WEHealthcare
    PolicyType.WEEducation -> WEEducation
    PolicyType.ForeignTrade -> ForeignTrade
    PolicyType.Immigration -> Immigration
}

@Composable
fun PolicySliderComponent(
    policyData: PolicyData,
    onPolicySelected: (PolicyState, PolicyType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 5.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(100.dp)
            .fillMaxWidth()
            .background(getPolicyColor(policyData.type))
    ) {
        PolicyNumber(policyData.number)
        Column {
            PolicyTitle(policyData.name)
            PolicySlider(policyData.defaultState, policyData.type, onPolicySelected)
            PolicySliderLabels()
        }
    }
}


@Composable
fun PolicySlider(
    defaultState: PolicyState,
    type: PolicyType,
    onPolicySelected: (PolicyState, PolicyType) -> Unit,
) {
    var sliderPosition by remember {
        mutableStateOf(PolicyState.fromState(defaultState))
    }
    Slider(
        value = sliderPosition,
        onValueChange = { newPolicyValue ->
            sliderPosition = newPolicyValue
            onPolicySelected(PolicyState.getState(newPolicyValue), type)
        },
        colors = SliderDefaults.colors(
            thumbColor = DarkGrey,
            activeTickColor = MidDarkGrey,
            activeTrackColor = DarkGrey,
            inactiveTickColor = DarkGrey,
            inactiveTrackColor = MidDarkGrey
        ),
        valueRange = 1f..3f,
        steps = 1,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun PolicyNumber(number: Int) {
    Column(
        Modifier
            .fillMaxHeight()
            .wrapContentWidth(), verticalArrangement = Arrangement.Center
    ) {
        Text(
            color = White,
            text = number.toString().uppercase(Locale.ROOT),
            fontSize = 50.sp,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(50.dp)
        )
    }

}

@Composable
fun PolicyTitle(policyDataTitle: String) {
    Box(
        Modifier
            .height(25.dp)
            .fillMaxWidth()
    ) {
        Text(
            color = White,
            text = policyDataTitle.uppercase(Locale.ROOT),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun PolicySliderLabels() {
    Row(modifier = Modifier.padding(horizontal = 25.dp, vertical = 0.dp)) {
        val sliderLabelModifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        PolicySlideLabelText("A", TextAlign.Start, sliderLabelModifier)
        PolicySlideLabelText("B", TextAlign.Center, sliderLabelModifier)
        PolicySlideLabelText("C", TextAlign.End, sliderLabelModifier)

    }
}

@Composable
fun PolicySlideLabelText(text: String, align: TextAlign, modifier: Modifier) {
    Text(
        color = White,
        text = text,
        style = MaterialTheme.typography.titleMedium,
        textAlign = align,
        modifier = modifier
    )

}
