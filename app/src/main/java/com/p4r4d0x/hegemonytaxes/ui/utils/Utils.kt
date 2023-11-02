package com.p4r4d0x.hegemonytaxes.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.ui.data.RoleUiData
import com.p4r4d0x.hegemonytaxes.ui.theme.Blue
import com.p4r4d0x.hegemonytaxes.ui.theme.CapitalistClass
import com.p4r4d0x.hegemonytaxes.ui.theme.Grey
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterBlue
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterRed
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterYellow
import com.p4r4d0x.hegemonytaxes.ui.theme.MiddleClass
import com.p4r4d0x.hegemonytaxes.ui.theme.NunitoFontFamily
import com.p4r4d0x.hegemonytaxes.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.ui.theme.Red
import com.p4r4d0x.hegemonytaxes.ui.theme.State
import com.p4r4d0x.hegemonytaxes.ui.theme.White
import com.p4r4d0x.hegemonytaxes.ui.theme.WorkingClass
import com.p4r4d0x.hegemonytaxes.ui.theme.Yellow
import java.util.Locale

object Utils {

    fun getHighlightedSpanStyle(textSize: TextUnit) = SpanStyle(
        letterSpacing = 0.5.sp,
        fontSize = textSize,
        color = Orange,
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Bold
    )

    fun getRegularSpanStyle(textSize: TextUnit) = SpanStyle(
        letterSpacing = 0.5.sp,
        fontSize = textSize,
        color = White,
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Bold
    )

    fun checkValidRange(context: Context, newValue: String, maxValue: Int, isValid: () -> Unit) {
        if (newValue.isEmpty() || tryNumberParse(context,newValue) in 0..maxValue) {
            isValid()
        } else {
            Toast.makeText(
                context,
                "Wrong value. It must be between 0 and $maxValue",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun tryNumberParse(context: Context, newValue: String) = try {
        newValue.toInt()
    } catch (e: Exception) {
        Toast.makeText(context, "Wrong input value.", Toast.LENGTH_SHORT).show()
        -1
    }

    fun verifyIntInputSelection(context: Context,numberInput: String, intRange: IntRange, isValid: (Int) -> Unit) {
        val number = tryNumberParse(context,numberInput)
        if(number in intRange){
            isValid(number)
        }else{
            Toast.makeText(context, "Wrong value. It must be in $intRange", Toast.LENGTH_SHORT).show()
        }
    }

    fun buildRoleUiData(role: HegemonyRole) =
        when (role) {
            HegemonyRole.WorkingClass -> RoleUiData(
                title = role.value.uppercase(Locale.ROOT),
                mainColor = WorkingClass,
                backgroundColor = LighterRed,
                description = "You pay the Income Tax, which depends on the combination of the current Labor Market (#2) and Taxation (#3) Policies. It consist on an amount that you need to pay per population.",
                avatar = R.drawable.avatar_working_class
            )

            HegemonyRole.MiddleClass ->
                RoleUiData(
                    title = role.value.uppercase(Locale.ROOT),
                    mainColor = MiddleClass,
                    backgroundColor = LighterYellow,
                    description = "You pay the Income Tax and the Employment Tax. The first one is based onf your income from companies other than you own in which you have Workers and the other is based on the Companies you run yourself.",
                    avatar = R.drawable.avatar_middle_class
                )

            HegemonyRole.CapitalistClass ->
                RoleUiData(
                    title = role.value.uppercase(Locale.ROOT),
                    mainColor = CapitalistClass,
                    backgroundColor = LighterBlue,
                    description = "You pay the Employment Tax and the Corporate Tax. The first one it depends on the number of Companies that you own, and the second one is based on the profit you made from the business activities.",
                    avatar = R.drawable.avatar_capitalist_class
                )

            HegemonyRole.State -> RoleUiData(
                title = role.value.uppercase(Locale.ROOT),
                mainColor = State,
                backgroundColor = LighterGrey,
                description = "",
                avatar = R.drawable.avatar_state
            )
        }


    fun getRoleBackgroundColor(role: HegemonyRole) = when (role) {
        HegemonyRole.WorkingClass -> Red
        HegemonyRole.MiddleClass -> Yellow
        HegemonyRole.CapitalistClass -> Blue
        HegemonyRole.State -> Grey
    }

}