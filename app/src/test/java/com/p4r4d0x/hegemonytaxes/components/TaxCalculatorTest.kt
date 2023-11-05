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
        private const val POPULATION = 4


        private const val PROFIT_0 = 0
        private const val PROFIT_9 = 9
        private const val PROFIT_24 = 24
        private const val PROFIT_49 = 49
        private const val PROFIT_99 = 99
        private const val PROFIT_199 = 199
        private const val PROFIT_299 = 299
        private const val PROFIT_300 = 300

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

    @Test
    fun `test calculate tax multiplier throws exceptions`() {

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

//    @Test
//    fun `test calculate tax income`() {
//        Assert.assertEquals(24, taxCalculator.calculateTaxIncome(TAX_INCOME, POPULATION))
//    }
//
//    @Test
//    fun `test calculate tax income throws exceptions`() {
//        Assert.assertThrows(TaxException::class.java) {
//            taxCalculator.calculateTaxIncome(-1, POPULATION)
//        }
//        Assert.assertThrows(TaxException::class.java) {
//            taxCalculator.calculateTaxIncome(TAX_INCOME, -1)
//        }
//    }


    @Test
    fun `test calculate corporate tax taxation policy A`() {
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_0, PolicyState.A))
        Assert.assertEquals(1, taxCalculator.calculateCorporateTax(PROFIT_9, PolicyState.A))
        Assert.assertEquals(5, taxCalculator.calculateCorporateTax(PROFIT_24, PolicyState.A))
        Assert.assertEquals(12, taxCalculator.calculateCorporateTax(PROFIT_49, PolicyState.A))
        Assert.assertEquals(24, taxCalculator.calculateCorporateTax(PROFIT_99, PolicyState.A))
        Assert.assertEquals(40, taxCalculator.calculateCorporateTax(PROFIT_199, PolicyState.A))
        Assert.assertEquals(100, taxCalculator.calculateCorporateTax(PROFIT_299, PolicyState.A))
        Assert.assertEquals(160, taxCalculator.calculateCorporateTax(PROFIT_300, PolicyState.A))
    }

    @Test
    fun `test calculate corporate tax taxation policy B`() {
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_0, PolicyState.B))
        Assert.assertEquals(2, taxCalculator.calculateCorporateTax(PROFIT_9, PolicyState.B))
        Assert.assertEquals(5, taxCalculator.calculateCorporateTax(PROFIT_24, PolicyState.B))
        Assert.assertEquals(10, taxCalculator.calculateCorporateTax(PROFIT_49, PolicyState.B))
        Assert.assertEquals(15, taxCalculator.calculateCorporateTax(PROFIT_99, PolicyState.B))
        Assert.assertEquals(30, taxCalculator.calculateCorporateTax(PROFIT_199, PolicyState.B))
        Assert.assertEquals(70, taxCalculator.calculateCorporateTax(PROFIT_299, PolicyState.B))
        Assert.assertEquals(120, taxCalculator.calculateCorporateTax(PROFIT_300, PolicyState.B))
    }

    @Test
    fun `test calculate corporate tax taxation policy C`() {
        Assert.assertEquals(0, taxCalculator.calculateCorporateTax(PROFIT_0, PolicyState.C))
        Assert.assertEquals(2, taxCalculator.calculateCorporateTax(PROFIT_9, PolicyState.C))
        Assert.assertEquals(4, taxCalculator.calculateCorporateTax(PROFIT_24, PolicyState.C))
        Assert.assertEquals(7, taxCalculator.calculateCorporateTax(PROFIT_49, PolicyState.C))
        Assert.assertEquals(10, taxCalculator.calculateCorporateTax(PROFIT_99, PolicyState.C))
        Assert.assertEquals(20, taxCalculator.calculateCorporateTax(PROFIT_199, PolicyState.C))
        Assert.assertEquals(40, taxCalculator.calculateCorporateTax(PROFIT_299, PolicyState.C))
        Assert.assertEquals(60, taxCalculator.calculateCorporateTax(PROFIT_300, PolicyState.C))
    }

}