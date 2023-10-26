package com.p4r4d0x.hegemonytaxes

import android.os.Bundle
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Row(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(DarkGrey)
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
        ) {
            PolicyHorizontal(PolicyType.FiscalPolicy)
            PolicyHorizontal(PolicyType.LaborMarket)
            PolicyHorizontal(PolicyType.Taxation)
            PolicyHorizontal(PolicyType.WEHealthcare)
            PolicyHorizontal(PolicyType.WEEducation)
        }
        Column(
            modifier = Modifier
                .padding(5.dp)
                .width(70.dp)
                .height(380.dp)
        ) {
            PolicyVertical(PolicyType.ForeignTrade)
            PolicyVertical(PolicyType.Immigration)
        }

    }

}

@Composable
fun PolicyHorizontal(policy: PolicyType) {
    val color = when (policy) {
        PolicyType.FiscalPolicy -> FiscalPolicy
        PolicyType.LaborMarket -> LaborMarket
        PolicyType.Taxation -> Taxation
        PolicyType.WEHealthcare -> WEHealthcare
        PolicyType.WEEducation -> WEEducation
        PolicyType.ForeignTrade -> ForeignTrade
        PolicyType.Immigration -> Immigration
    }
    Row(
        modifier = Modifier
            .padding(5.dp)
            .height(70.dp)
            .width(280.dp)
            .background(color)
    ) {
    }
}

@Composable
fun PolicyVertical(policy: PolicyType) {
    val color = when (policy) {
        PolicyType.FiscalPolicy -> FiscalPolicy
        PolicyType.LaborMarket -> LaborMarket
        PolicyType.Taxation -> Taxation
        PolicyType.WEHealthcare -> WEHealthcare
        PolicyType.WEEducation -> WEEducation
        PolicyType.ForeignTrade -> ForeignTrade
        PolicyType.Immigration -> Immigration
    }
    Column(
        modifier = Modifier
            .height(200.dp)
            .width(80.dp)
            .background(color)
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HegemonyTaxesCalculatorTheme {
        Greeting("Android")
    }
}