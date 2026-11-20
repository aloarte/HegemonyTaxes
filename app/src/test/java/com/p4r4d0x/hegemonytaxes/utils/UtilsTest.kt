package com.p4r4d0x.hegemonytaxes.utils

import com.p4r4d0x.hegemonytaxes.R
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.InputValidation
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Blue
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Grey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.LighterBlue
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.LighterGrey
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.LighterRed
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.LighterYellow
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Red
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.Yellow
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.Utils
import org.junit.Assert
import org.junit.Test

class UtilsTest {

    @Test
    fun `GIVEN empty string WHEN checkValidRange THEN return InputValidation Valid`() {
        // GIVEN
        val input = ""
        val maxValue = 10

        // WHEN
        val result = Utils.checkValidRange(input, maxValue)

        // THEN
        Assert.assertEquals(InputValidation.Valid, result)
    }

    @Test
    fun `GIVEN number within range WHEN checkValidRange THEN return InputValidation Valid`() {
        // GIVEN
        val input = "5"
        val maxValue = 10

        // WHEN
        val result = Utils.checkValidRange(input, maxValue)

        // THEN
        Assert.assertEquals(InputValidation.Valid, result)
    }

    @Test
    fun `GIVEN non-numeric string WHEN checkValidRange THEN return InputValidation NotANumber`() {
        // GIVEN
        val input = "abc"
        val maxValue = 10

        // WHEN
        val result = Utils.checkValidRange(input, maxValue)

        // THEN
        Assert.assertEquals(InputValidation.NotANumber, result)
    }

    @Test
    fun `GIVEN number out of range WHEN checkValidRange THEN return InputValidation WrongRange`() {
        // GIVEN
        val input = "15"
        val maxValue = 10

        // WHEN
        val result = Utils.checkValidRange(input, maxValue)

        // THEN
        Assert.assertEquals(InputValidation.WrongRange, result)
    }

    @Test
    fun `GIVEN valid numeric string WHEN tryNumberParse THEN return parsed integer`() {
        // GIVEN
        val input = "42"

        // WHEN
        val result = Utils.tryNumberParse(input)

        // THEN
        Assert.assertEquals(42, result)
    }

    @Test
    fun `GIVEN invalid string WHEN tryNumberParse THEN return minus one`() {
        // GIVEN
        val input = "invalid"

        // WHEN
        val result = Utils.tryNumberParse(input)

        // THEN
        Assert.assertEquals(-1, result)
    }

    @Test
    fun `GIVEN all valid inputs in ranges WHEN verifyIntInputsSelection THEN return true`() {
        // GIVEN
        val inputs = listOf("3" to 0..5, "10" to 0..20)

        // WHEN
        val isValid = Utils.verifyIntInputsSelection(inputs)

        // THEN
        Assert.assertTrue(isValid)
    }

    @Test
    fun `GIVEN any input out of range WHEN verifyIntInputsSelection THEN return false`() {
        // GIVEN
        val inputs = listOf("3" to 0..5, "25" to 0..20)

        // WHEN
        val isValid = Utils.verifyIntInputsSelection(inputs)

        // THEN
        Assert.assertFalse(isValid)
    }

    @Test
    fun `GIVEN input validation values WHEN getInputValidationError THEN return correct error text`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals("Symbols not allowed", Utils.getInputValidationError(InputValidation.NotANumber))
        Assert.assertEquals("The number is not on the range", Utils.getInputValidationError(InputValidation.WrongRange))
        Assert.assertEquals("", Utils.getInputValidationError(InputValidation.Valid))
    }

    @Test
    fun `GIVEN roles WHEN getRoleBackground THEN return correct background color`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals(LighterRed, Utils.getRoleBackground(HegemonyRole.WorkingClass))
        Assert.assertEquals(LighterYellow, Utils.getRoleBackground(HegemonyRole.MiddleClass))
        Assert.assertEquals(LighterBlue, Utils.getRoleBackground(HegemonyRole.CapitalistClass))
        Assert.assertEquals(LighterGrey, Utils.getRoleBackground(HegemonyRole.State))
    }

    @Test
    fun `GIVEN roles WHEN getRoleAvatar THEN return correct avatar drawable resource`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals(R.drawable.avatar_working_class, Utils.getRoleAvatar(HegemonyRole.WorkingClass))
        Assert.assertEquals(R.drawable.avatar_middle_class, Utils.getRoleAvatar(HegemonyRole.MiddleClass))
        Assert.assertEquals(R.drawable.avatar_capitalist_class, Utils.getRoleAvatar(HegemonyRole.CapitalistClass))
        Assert.assertEquals(R.drawable.avatar_state, Utils.getRoleAvatar(HegemonyRole.State))
    }

    @Test
    fun `GIVEN roles WHEN getRoleMainColor THEN return correct main color`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals(Red, Utils.getRoleMainColor(HegemonyRole.WorkingClass))
        Assert.assertEquals(Yellow, Utils.getRoleMainColor(HegemonyRole.MiddleClass))
        Assert.assertEquals(Blue, Utils.getRoleMainColor(HegemonyRole.CapitalistClass))
        Assert.assertEquals(Grey, Utils.getRoleMainColor(HegemonyRole.State))
    }
}
