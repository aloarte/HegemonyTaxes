package com.p4r4d0x.hegemonytaxes.presenter.roles

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.ui.data.HegemonyRoleUi
import com.p4r4d0x.hegemonytaxes.ui.theme.CapitalistClass
import com.p4r4d0x.hegemonytaxes.ui.theme.DarkGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.ui.theme.LightRed
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterBlue
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterRed
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterYellow
import com.p4r4d0x.hegemonytaxes.ui.theme.MiddleClass
import com.p4r4d0x.hegemonytaxes.ui.theme.State
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import com.p4r4d0x.hegemonytaxes.ui.theme.WorkingClass
import java.util.Locale

@Composable
fun WorkingClassScreen(state: UiState) {
    HegemonyTaxesCalculatorTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(DarkGrey)
        ) {
            RoleTitleSection(HegemonyRole.WorkingClass)
            RoleTitleSection(HegemonyRole.MiddleClass)
            RoleTitleSection(HegemonyRole.CapitalistClass)

            Divider(thickness = 50.dp, color = Color.Transparent)
            DropdownDemo()
        }
    }
}

@Composable
fun RoleTitleSection(role: HegemonyRole) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Divider(thickness = 30.dp, color = Color.Transparent)
        RoleTitleCard(buildRoleUiData(role))
    }
}


fun buildRoleUiData(role: HegemonyRole) =
    when (role) {
        HegemonyRole.WorkingClass -> HegemonyRoleUi(
            title = role.value.uppercase(Locale.ROOT),
            mainColor = WorkingClass,
            backgroundColor = LighterRed,
            description = "You pay the Income Tax, which depends on the combination of the current Labor Market (#2) and Taxation (#3) Policies. It consist on an amount that you need to pay per population.",
            avatar = R.drawable.avatar_working_class
        )

        HegemonyRole.MiddleClass ->
            HegemonyRoleUi(
                title = role.value.uppercase(Locale.ROOT),
                mainColor = MiddleClass,
                backgroundColor = LighterYellow,
                description = "You pay the Income Tax and the Employment Tax. The first one is based onf your income from companies other than you own in which you have Workers and the other is based on the Companies you run yourself.",
                avatar = R.drawable.avatar_working_class
            )

        HegemonyRole.CapitalistClass ->
            HegemonyRoleUi(
                title = role.value.uppercase(Locale.ROOT),
                mainColor = CapitalistClass,
                backgroundColor = LighterBlue,
                description = "You pay the Employment Tax and the Corporate Tax. The first one it depends on the number of Companies that you own, and the second one is based on the profit you made from the business activities.",
                avatar = R.drawable.avatar_capitalist_class
            )

        HegemonyRole.State -> HegemonyRoleUi(
            title = role.value.uppercase(Locale.ROOT),
            mainColor = State,
            backgroundColor = LighterGrey,
            description = "",
            avatar = R.drawable.avatar_capitalist_class
        )
    }



@Composable
fun RoleTitleCard(roleUi: HegemonyRoleUi) {

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(roleUi.backgroundColor)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(40.dp)
                .width(40.dp),
            painter = painterResource(id = roleUi.avatar),
            contentDescription = null
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                color = roleUi.mainColor,
                text = roleUi.title,
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 25.dp)
            )
            Divider(thickness = 10.dp, color = Color.Transparent)
            Text(
                color = roleUi.mainColor,
                text = roleUi.description,
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun DropdownDemo() {
    var expanded by remember { mutableStateOf(false) }
    val items = (3..10).toList()
    var selectedIndex by remember { mutableStateOf(3) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {


        DropdownResult(items[selectedIndex], expanded) {
            expanded = true
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(200.dp)
                .background(LightRed)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(text = { Text(text = s.toString()) }, onClick = {
                    selectedIndex = index
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun DropdownResult(dropdownValue: Int, expanded: Boolean, onClicked: () -> Unit) {
    Row(modifier = Modifier
        .background(DarkGrey)
        .width(400.dp)
        .clickable {
            onClicked()
        }) {
        Text(
            color = White,
            text = "Current population is $dropdownValue",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            tint = White,
            contentDescription = ""
        )
    }

}
