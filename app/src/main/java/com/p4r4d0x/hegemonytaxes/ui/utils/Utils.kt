package com.p4r4d0x.hegemonytaxes.ui.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.InputValidation
import com.p4r4d0x.hegemonytaxes.ui.data.RoleUiData
import com.p4r4d0x.hegemonytaxes.ui.theme.Blue
import com.p4r4d0x.hegemonytaxes.ui.theme.Grey
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterBlue
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterGrey
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterRed
import com.p4r4d0x.hegemonytaxes.ui.theme.LighterYellow
import com.p4r4d0x.hegemonytaxes.ui.theme.NunitoFontFamily
import com.p4r4d0x.hegemonytaxes.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.ui.theme.Red
import com.p4r4d0x.hegemonytaxes.ui.theme.White
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

    fun checkValidRange(newValue: String, maxValue: Int): InputValidation {
        val parsedNumber = tryNumberParse(newValue)
        return when {
            newValue.isEmpty() || parsedNumber in 0..maxValue -> InputValidation.Valid
            parsedNumber == -1 -> InputValidation.NotANumber
            else -> InputValidation.WrongRange
        }
    }

    fun tryNumberParse(newValue: String) = try {
        newValue.toInt()
    } catch (e: Exception) {
        -1
    }

    fun verifyIntInputsSelection(
         inputsAndRange: List<Pair<String,IntRange>>,
    ):Boolean {
        var allInputsValid = true
        inputsAndRange.forEach {(numberInput,intRange)->
            allInputsValid = allInputsValid && verifyIntInputSelection(numberInput,intRange)
        }
        return allInputsValid
    }

    private fun verifyIntInputSelection(
        numberInput: String,
        intRange: IntRange
    ) =    tryNumberParse(numberInput) in intRange



    fun buildRoleUiData(role: HegemonyRole) = RoleUiData(
        title = role.value.uppercase(Locale.ROOT),
        mainColor = getRoleMainColor(role),
        backgroundColor = getRoleBackground(role),
        description = getRoleDescription(role),
        avatar = getRoleAvatar(role)
    )


    private fun getRoleDescription(role: HegemonyRole) = when (role) {
        HegemonyRole.WorkingClass -> "You pay the Income Tax, which depends on the combination of the current Labor Market (#2) and Taxation (#3) Policies. It consist on an amount that you need to pay per population."
        HegemonyRole.MiddleClass -> "You pay the Income Tax and the Employment Tax. The first one is based onf your income from companies other than you own in which you have Workers and the other is based on the Companies you run yourself."
        HegemonyRole.CapitalistClass -> "You pay the Employment Tax and the Corporate Tax. The first one it depends on the number of Companies that you own, and the second one is based on the profit you made from the business activities."
        HegemonyRole.State -> "You receive different taxes from each other class: \n" +
                "Working Class: Income Tax, based on their population. \n" +
                "Middle Class: Income Tax and Employment Tax. \n" +
                "Capitalist Class: Employment Tax and Corporate tax."
    }

    fun getRoleBackground(role: HegemonyRole) = when (role) {
        HegemonyRole.WorkingClass -> LighterRed
        HegemonyRole.MiddleClass -> LighterYellow
        HegemonyRole.CapitalistClass -> LighterBlue
        HegemonyRole.State -> LighterGrey
    }


    fun getRoleAvatar(role: HegemonyRole) = when (role) {
        HegemonyRole.WorkingClass -> R.drawable.avatar_working_class
        HegemonyRole.MiddleClass -> R.drawable.avatar_middle_class
        HegemonyRole.CapitalistClass -> R.drawable.avatar_capitalist_class
        HegemonyRole.State -> R.drawable.avatar_state
    }

    fun getRoleMainColor(role: HegemonyRole) = when (role) {
        HegemonyRole.WorkingClass -> Red
        HegemonyRole.MiddleClass -> Yellow
        HegemonyRole.CapitalistClass -> Blue
        HegemonyRole.State -> Grey
    }

    fun getInputValidationError(inputError: InputValidation) = when (inputError) {
        InputValidation.NotANumber -> "Symbols not allowed"
        InputValidation.WrongRange -> "The number is not on the range"
        InputValidation.Valid -> ""
    }

}