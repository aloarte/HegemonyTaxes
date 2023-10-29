package com.p4r4d0x.hegemonytaxes.components

import com.p4r4d0x.hegemonytaxes.domain_data.components.TaxCalculator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TaxCalculatorTest {

    private lateinit var taxCalculator: TaxCalculator

    companion object {
        private const val BASE_TAX_A = 3
        private const val WELFARE_MULTIPLIER_A = 2
        private const val BASE_TAX_B = 2
        private const val WELFARE_MULTIPLIER_B = 1
        private const val BASE_TAX_C = 1
        private const val WELFARE_MULTIPLIER_C = 0
    }

    @Before
    fun setUp() {
        taxCalculator = TaxCalculator()
    }

    @Test
    fun `test calculate tax multiplier base 3 welfare tax multiplier 2`() {
        Assert.assertEquals(
            11,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_A, WELFARE_MULTIPLIER_A, 2, 2)
        )
        Assert.assertEquals(
            9,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_A, WELFARE_MULTIPLIER_A, 2, 1)
        )
        Assert.assertEquals(
            9,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_A, WELFARE_MULTIPLIER_A, 1, 2)
        )
        Assert.assertEquals(
            7,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_A, WELFARE_MULTIPLIER_A, 1, 1)
        )
        Assert.assertEquals(
            5,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_A, WELFARE_MULTIPLIER_A, 0, 1)
        )
        Assert.assertEquals(
            5,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_A, WELFARE_MULTIPLIER_A, 1, 0)
        )
        Assert.assertEquals(
            3,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_A, WELFARE_MULTIPLIER_A, 0, 0)
        )
    }

    @Test
    fun `test calculate tax multiplier base 2 welfare tax multiplier 1`() {
        Assert.assertEquals(
            6,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_B, WELFARE_MULTIPLIER_B, 2, 2)
        )
        Assert.assertEquals(
            5,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_B, WELFARE_MULTIPLIER_B, 2, 1)
        )
        Assert.assertEquals(
            5,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_B, WELFARE_MULTIPLIER_B, 1, 2)
        )
        Assert.assertEquals(
            4,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_B, WELFARE_MULTIPLIER_B, 1, 1)
        )
        Assert.assertEquals(
            3,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_B, WELFARE_MULTIPLIER_B, 0, 1)
        )
        Assert.assertEquals(
            3,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_B, WELFARE_MULTIPLIER_B, 1, 0)
        )
        Assert.assertEquals(
            2,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_B, WELFARE_MULTIPLIER_B, 0, 0)
        )
    }

    @Test
    fun `test calculate tax multiplier base 1 welfare tax multiplier 0`() {
        Assert.assertEquals(
            1,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, 2, 2)
        )
        Assert.assertEquals(
            1,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, 2, 1)
        )
        Assert.assertEquals(
            1,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, 1, 2)
        )
        Assert.assertEquals(
            1,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, 1, 1)
        )
        Assert.assertEquals(
            1,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, 0, 1)
        )
        Assert.assertEquals(
            1,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, 1, 0)
        )
        Assert.assertEquals(
            1,
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, 0, 0)
        )
    }

}