package com.p4r4d0x.hegemonytaxes.presenter.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.InputValidation
import com.p4r4d0x.hegemonytaxes.presenter.ui.data.RoleUiData
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Blue
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Grey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.LighterBlue
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.LighterGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.LighterRed
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.LighterYellow
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.NunitoFontFamily
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Orange
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Red
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.White
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Yellow
import java.util.Locale

object Utils {

    fun getHighlightedSpanStyle(textSize: TextUnit) = SpanStyle(
        letterSpacing = 0.5.sp,
        fontSize = textSize,
        color = Orange,
        fontFamily = NunitoFontFamily,
        fontWeight = FontWeight.Bold
    )

    fun getBoldSpanStyle(textSize: TextUnit) = SpanStyle(
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


    @Composable
    fun buildRoleUiData(role: HegemonyRole) = RoleUiData(
        title = getRoleName(role),
        mainColor = getRoleMainColor(role),
        backgroundColor = getRoleBackground(role),
        description = getRoleDescription(role),
        avatar = getRoleAvatar(role)
    )


    @Composable
    private fun getRoleDescription(role: HegemonyRole) = when (role) {
        HegemonyRole.WorkingClass -> stringResource(R.string.description_working_class)
        HegemonyRole.MiddleClass -> stringResource(R.string.description_middle_class)
        HegemonyRole.CapitalistClass -> stringResource(R.string.description_capitalist_class)
        HegemonyRole.State -> stringResource(R.string.description_state)
    }

    @Composable
    private fun getRoleName(role: HegemonyRole) = when (role) {
        HegemonyRole.WorkingClass -> stringResource(R.string.name_working_class)
        HegemonyRole.MiddleClass -> stringResource(R.string.name_middle_class)
        HegemonyRole.CapitalistClass -> stringResource(R.string.name_capitalist_class)
        HegemonyRole.State -> stringResource(R.string.name_state)
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