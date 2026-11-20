package com.p4r4d0x.hegemonytaxes.components

import com.p4r4d0x.hegemonytaxes.domain_data.components.TaxCalculator
import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
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
        private const val TAX_INCOME = 6
        private const val MULTIPLIER = 4

        private const val PROFIT_0 = 0
        private const val PROFIT_4 = 4
        private const val PROFIT_5 = 5
        private const val PROFIT_9 = 9
        private const val PROFIT_10 = 10
        private const val PROFIT_24 = 24
        private const val PROFIT_25 = 25
        private const val PROFIT_49 = 49
        private const val PROFIT_50 = 50
        private const val PROFIT_99 = 99
        private const val PROFIT_100 = 100
        private const val PROFIT_199 = 199
        private const val PROFIT_200 = 200
        private const val PROFIT_299 = 299
        private const val PROFIT_300 = 300
    }

    @Before
    fun setUp() {
        taxCalculator = TaxCalculator()
    }

    @Test
    fun `GIVEN base tax 3 and welfare multiplier 2 WHEN calculateTaxMultiplier THEN return correct multiplier`() {
        // GIVEN
        val baseTax = BASE_TAX_A
        val welfareMultiplier = WELFARE_MULTIPLIER_A

        // WHEN & THEN
        Assert.assertEquals(11, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 2, 2))
        Assert.assertEquals(9, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 2, 1))
        Assert.assertEquals(9, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 2))
        Assert.assertEquals(7, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 1))
        Assert.assertEquals(5, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 0, 1))
        Assert.assertEquals(5, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 0))
        Assert.assertEquals(3, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 0, 0))
    }

    @Test
    fun `GIVEN base tax 2 and welfare multiplier 1 WHEN calculateTaxMultiplier THEN return correct multiplier`() {
        // GIVEN
        val baseTax = BASE_TAX_B
        val welfareMultiplier = WELFARE_MULTIPLIER_B

        // WHEN & THEN
        Assert.assertEquals(6, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 2, 2))
        Assert.assertEquals(5, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 2, 1))
        Assert.assertEquals(5, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 2))
        Assert.assertEquals(4, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 1))
        Assert.assertEquals(3, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 0, 1))
        Assert.assertEquals(3, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 0))
        Assert.assertEquals(2, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 0, 0))
    }

    @Test
    fun `GIVEN base tax 1 and welfare multiplier 0 WHEN calculateTaxMultiplier THEN return correct multiplier`() {
        // GIVEN
        val baseTax = BASE_TAX_C
        val welfareMultiplier = WELFARE_MULTIPLIER_C

        // WHEN & THEN
        Assert.assertEquals(1, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 2, 2))
        Assert.assertEquals(1, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 2, 1))
        Assert.assertEquals(1, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 2))
        Assert.assertEquals(1, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 1))
        Assert.assertEquals(1, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 0, 1))
        Assert.assertEquals(1, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 1, 0))
        Assert.assertEquals(1, taxCalculator.calculateTaxMultiplier(baseTax, welfareMultiplier, 0, 0))
    }

    @Test
    fun `GIVEN negative values WHEN calculateTaxMultiplier THEN throw TaxException`() {
        // GIVEN, WHEN & THEN
        Assert.assertThrows(TaxException::class.java) {
            taxCalculator.calculateTaxMultiplier(-1, WELFARE_MULTIPLIER_C, 2, 2)
        }
        Assert.assertThrows(TaxException::class.java) {
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, -1, 2, 1)
        }
        Assert.assertThrows(TaxException::class.java) {
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, -1, 2)
        }
        Assert.assertThrows(TaxException::class.java) {
            taxCalculator.calculateTaxMultiplier(BASE_TAX_C, WELFARE_MULTIPLIER_C, 1, -1)
        }
    }

    @Test
    fun `GIVEN valid income tax and multiplier WHEN calculateTaxIncomeResult THEN return calculated product`() {
        // GIVEN
        val incomeTax = TAX_INCOME
        val multiplier = MULTIPLIER

        // WHEN
        val result = taxCalculator.calculateTaxIncomeResult(incomeTax, multiplier)

        // THEN
        Assert.assertEquals(24, result)
    }

    @Test
    fun `GIVEN invalid values below one WHEN calculateTaxIncomeResult THEN throw TaxException`() {
        // GIVEN, WHEN & THEN
        Assert.assertThrows(TaxException::class.java) {
            taxCalculator.calculateTaxIncomeResult(0, MULTIPLIER)
        }
        Assert.assertThrows(TaxException::class.java) {
            taxCalculator.calculateTaxIncomeResult(TAX_INCOME, 0)
        }
    }

    @Test
    fun `GIVEN valid income tax and multiplier WHEN calculateEmploymentTaxResult THEN return calculated product`() {
        // GIVEN
        val incomeTax = TAX_INCOME
        val multiplier = MULTIPLIER

        // WHEN
        val result = taxCalculator.calculateEmploymentTaxResult(incomeTax, multiplier)

        // THEN
        Assert.assertEquals(24, result)
    }

    @Test
    fun `GIVEN invalid values below one WHEN calculateEmploymentTaxResult THEN throw TaxException`() {
        // GIVEN, WHEN & THEN
        Assert.assertThrows(TaxException::class.java) {
            taxCalculator.calculateEmploymentTaxResult(0, MULTIPLIER)
        }
        Assert.assertThrows(TaxException::class.java) {
            taxCalculator.calculateEmploymentTaxResult(TAX_INCOME, 0)
        }
    }

    @Test
    fun `GIVEN policy state A WHEN calculateCorporateTax THEN return correct tax for profit ranges`() {
        // GIVEN
        val state = PolicyState.A

        // WHEN & THEN
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_0, state))
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_4, state))
        Assert.assertEquals(1, taxCalculator.calculateCorporateTax(PROFIT_5, state))
        Assert.assertEquals(1, taxCalculator.calculateCorporateTax(PROFIT_9, state))
        Assert.assertEquals(5, taxCalculator.calculateCorporateTax(PROFIT_10, state))
        Assert.assertEquals(5, taxCalculator.calculateCorporateTax(PROFIT_24, state))
        Assert.assertEquals(12, taxCalculator.calculateCorporateTax(PROFIT_25, state))
        Assert.assertEquals(12, taxCalculator.calculateCorporateTax(PROFIT_49, state))
        Assert.assertEquals(24, taxCalculator.calculateCorporateTax(PROFIT_50, state))
        Assert.assertEquals(24, taxCalculator.calculateCorporateTax(PROFIT_99, state))
        Assert.assertEquals(40, taxCalculator.calculateCorporateTax(PROFIT_100, state))
        Assert.assertEquals(40, taxCalculator.calculateCorporateTax(PROFIT_199, state))
        Assert.assertEquals(100, taxCalculator.calculateCorporateTax(PROFIT_200, state))
        Assert.assertEquals(100, taxCalculator.calculateCorporateTax(PROFIT_299, state))
        Assert.assertEquals(160, taxCalculator.calculateCorporateTax(PROFIT_300, state))
    }

    @Test
    fun `GIVEN policy state B WHEN calculateCorporateTax THEN return correct tax for profit ranges`() {
        // GIVEN
        val state = PolicyState.B

        // WHEN & THEN
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_0, state))
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_4, state))
        Assert.assertEquals(2, taxCalculator.calculateCorporateTax(PROFIT_5, state))
        Assert.assertEquals(2, taxCalculator.calculateCorporateTax(PROFIT_9, state))
        Assert.assertEquals(5, taxCalculator.calculateCorporateTax(PROFIT_10, state))
        Assert.assertEquals(5, taxCalculator.calculateCorporateTax(PROFIT_24, state))
        Assert.assertEquals(10, taxCalculator.calculateCorporateTax(PROFIT_25, state))
        Assert.assertEquals(10, taxCalculator.calculateCorporateTax(PROFIT_49, state))
        Assert.assertEquals(15, taxCalculator.calculateCorporateTax(PROFIT_50, state))
        Assert.assertEquals(15, taxCalculator.calculateCorporateTax(PROFIT_99, state))
        Assert.assertEquals(30, taxCalculator.calculateCorporateTax(PROFIT_100, state))
        Assert.assertEquals(30, taxCalculator.calculateCorporateTax(PROFIT_199, state))
        Assert.assertEquals(70, taxCalculator.calculateCorporateTax(PROFIT_200, state))
        Assert.assertEquals(70, taxCalculator.calculateCorporateTax(PROFIT_299, state))
        Assert.assertEquals(120, taxCalculator.calculateCorporateTax(PROFIT_300, state))
    }

    @Test
    fun `GIVEN policy state C WHEN calculateCorporateTax THEN return correct tax for profit ranges`() {
        // GIVEN
        val state = PolicyState.C

        // WHEN & THEN
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_0, state))
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_4, state))
        Assert.assertEquals(2, taxCalculator.calculateCorporateTax(PROFIT_5, state))
        Assert.assertEquals(2, taxCalculator.calculateCorporateTax(PROFIT_9, state))
        Assert.assertEquals(4, taxCalculator.calculateCorporateTax(PROFIT_10, state))
        Assert.assertEquals(4, taxCalculator.calculateCorporateTax(PROFIT_24, state))
        Assert.assertEquals(7, taxCalculator.calculateCorporateTax(PROFIT_25, state))
        Assert.assertEquals(7, taxCalculator.calculateCorporateTax(PROFIT_49, state))
        Assert.assertEquals(10, taxCalculator.calculateCorporateTax(PROFIT_50, state))
        Assert.assertEquals(10, taxCalculator.calculateCorporateTax(PROFIT_99, state))
        Assert.assertEquals(20, taxCalculator.calculateCorporateTax(PROFIT_100, state))
        Assert.assertEquals(20, taxCalculator.calculateCorporateTax(PROFIT_199, state))
        Assert.assertEquals(40, taxCalculator.calculateCorporateTax(PROFIT_200, state))
        Assert.assertEquals(40, taxCalculator.calculateCorporateTax(PROFIT_299, state))
        Assert.assertEquals(60, taxCalculator.calculateCorporateTax(PROFIT_300, state))
    }

    @Test
    fun `GIVEN policy state Unknown WHEN calculateCorporateTax THEN return zero`() {
        // GIVEN
        val state = PolicyState.Unknown

        // WHEN
        val result = taxCalculator.calculateCorporateTax(PROFIT_100, state)

        // THEN
        Assert.assertEquals(0, result)
    }
}
