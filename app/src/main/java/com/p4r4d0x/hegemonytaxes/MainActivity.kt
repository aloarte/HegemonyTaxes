package com.p4r4d0x.hegemonytaxes

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.p4r4d0x.hegemonytaxes.domain.PolicyData
import com.p4r4d0x.hegemonytaxes.domain.PolicyState
import com.p4r4d0x.hegemonytaxes.presenter.policies.compose.PolicySlider
import com.p4r4d0x.hegemonytaxes.presenter.policies.compose.PolicySliderComponent
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.FiscalPolicy
import com.p4r4d0x.hegemonytaxes.ui.theme.ForeignTrade
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.ui.theme.Immigration
import com.p4r4d0x.hegemonytaxes.ui.theme.LaborMarket
import com.p4r4d0x.hegemonytaxes.ui.theme.Taxation
import com.p4r4d0x.hegemonytaxes.ui.theme.WEEducation
import com.p4r4d0x.hegemonytaxes.ui.theme.WEHealthcare

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HegemonyTaxesCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }

            (1f..3f)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    HegemonyTaxesCalculatorTheme{
        Row(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
        ) {
            val policyDataList = getPolicyData()
            val callbackcomun: (PolicyState, PolicyType) -> Unit = { state,type->
                Log.d("ALRALR","Selected: $state , $type")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                PolicySliderComponent(policyDataList[0],callbackcomun)
                PolicySliderComponent(policyDataList[1],callbackcomun)
                PolicySliderComponent(policyDataList[2],callbackcomun)
                PolicySliderComponent(policyDataList[3],callbackcomun)
                PolicySliderComponent(policyDataList[4],callbackcomun)
            }

        }

    }

}


fun getPolicyData() = listOf(
    PolicyData(
        1,
        "Fiscal Policy",
        PolicyType.FiscalPolicy,
        PolicyState.A
    ),
    PolicyData(
        2,
        "Labor Market",
        PolicyType.LaborMarket,
        PolicyState.A
    ),
    PolicyData(
        3,
        "Taxation",
        PolicyType.Taxation,
        PolicyState.A
    ),
    PolicyData(
        4,
        "Welfare State: Healthcare & benefits",
        PolicyType.WEHealthcare,
        PolicyState.A
    ),
    PolicyData(
        5,
        "Welfare State: Education",
        PolicyType.WEEducation,
        PolicyState.A
    ),
    PolicyData(
        6,
        "Foreign Trade",
        PolicyType.ForeignTrade,
        PolicyState.A
    ),
    PolicyData(
        7,
        "Immigration",
        PolicyType.Immigration,
        PolicyState.A
    )

)


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HegemonyTaxesCalculatorTheme {
        Greeting("Android")
    }
}