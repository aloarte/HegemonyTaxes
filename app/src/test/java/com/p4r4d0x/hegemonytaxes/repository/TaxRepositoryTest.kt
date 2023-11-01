package com.p4r4d0x.hegemonytaxes.repository

import com.p4r4d0x.hegemonytaxes.domain_data.components.TaxCalculator
import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException
import com.p4r4d0x.hegemonytaxes.domain_data.repository.TaxRepository
import com.p4r4d0x.hegemonytaxes.domain_data.repository.impl.TaxRepositoryImpl
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants
import com.p4r4d0x.hegemonytaxes.utils.TestData.policies
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TaxRepositoryTest {

    @MockK
    private lateinit var datasource: PoliciesDatasource

    @MockK
    private lateinit var taxCalculator: TaxCalculator

    private lateinit var repository: TaxRepository

    companion object {
        private const val POPULATION = 3
        private const val TAX_VALUE = 4
        private const val INCOME_TAX = 4
        private const val INCOME_TAX_PRICE = POPULATION * INCOME_TAX
        private val policiesMissingLaborMarket = policies.toMutableList().apply { removeAt(1) }
        private val policiesMissingTaxation = policies.toMutableList().apply { removeAt(2) }
        private val policiesMissingHealthcare = policies.toMutableList().apply { removeAt(3) }
        private val policiesMissingEducation = policies.toMutableList().apply { removeAt(4) }

    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = TaxRepositoryImpl(datasource, taxCalculator)
    }

    @Test
    fun `test calculate tax multiplier having correct policies`() {
        val exceptionsRaisedList = listOf(false, false, false, false)
        mockCalculateTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.calculateTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, true, true, true, true)
        verifyCalculateTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier having correct policies but wrong state 1`() {
        val exceptionsRaisedList = listOf(true, false, false, false)
        mockCalculateTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.calculateTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, false, false, false, false)
        verifyCalculateTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier having correct policies but wrong state 2`() {
        val exceptionsRaisedList = listOf(false, true, false, false)
        mockCalculateTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.calculateTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, true, false, false, false)
        verifyCalculateTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier having correct policies but wrong state 3`() {
        val exceptionsRaisedList = listOf(false, false, true, false)
        mockCalculateTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.calculateTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, true, true, false, false)
        verifyCalculateTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }


    @Test
    fun `test calculate tax multiplier having correct policies but wrong state 4`() {
        val exceptionsRaisedList = listOf(false, false, false, true)
        mockCalculateTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.calculateTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, true, true, true, false)
        verifyCalculateTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier missing taxation policy`() {
        val taxMultiplier = repository.calculateTaxMultiplier(policiesMissingTaxation)

        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyCalculateTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier missing healthcare policy`() {
        val taxMultiplier = repository.calculateTaxMultiplier(policiesMissingHealthcare)

        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyCalculateTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier missing education policy`() {
        val taxMultiplier = repository.calculateTaxMultiplier(policiesMissingEducation)

        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyCalculateTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate income tax`() {
        mockCalculateTaxIncomeFunctions(listOf(false, false))
        val taxMultiplier = repository.calculateIncomeTax(policies, POPULATION)

        verifyCalculateTaxIncomeFunctions(listOf(true, true))
        Assert.assertEquals(INCOME_TAX_PRICE, taxMultiplier)
    }


    @Test
    fun `test calculate income tax getIncomeTax exception`() {
        mockCalculateTaxIncomeFunctions(listOf(false, true))
        val taxMultiplier = repository.calculateIncomeTax(policies, POPULATION)

        verifyCalculateTaxIncomeFunctions(listOf(true, true))
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate income tax calculateTaxIncome exception`() {
        mockCalculateTaxIncomeFunctions(listOf(true, false))
        val taxMultiplier = repository.calculateIncomeTax(policies, POPULATION)

        verifyCalculateTaxIncomeFunctions(listOf(true, false))
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate income tax missing labor market policy`() {
        val taxMultiplier = repository.calculateIncomeTax(policiesMissingLaborMarket, POPULATION)

        verifyCalculateTaxIncomeFunctions(listOf(false, false))
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate income tax missing taxation policy`() {
        val taxMultiplier = repository.calculateIncomeTax(policiesMissingTaxation, POPULATION)

        verifyCalculateTaxIncomeFunctions(listOf(false, false))
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    private fun mockCalculateTaxMultiplierFunctions(excRaised: List<Boolean>) {
        if (excRaised[0]) {
            every { datasource.getBaseTaxIncrement(policies[2].state) } throws TaxException("")
        } else {
            every { datasource.getBaseTaxIncrement(policies[2].state) } returns Constants.BASE_TAX_INCREMENT_B
        }
        if (excRaised[1]) {
            every { datasource.getWelfareIncrement(policies[2].state) } throws TaxException("")
        } else {
            every { datasource.getWelfareIncrement(policies[2].state) } returns Constants.WELFARE_TAX_INCREMENT_B
        }
        if (excRaised[2]) {
            every { datasource.getWelfareIncrement(policies[3].state) } throws TaxException("")
        } else {
            every { datasource.getWelfareIncrement(policies[3].state) } returns Constants.WELFARE_TAX_INCREMENT_A
        }
        if (excRaised[3]) {
            every { datasource.getWelfareIncrement(policies[4].state) } throws TaxException("")
        } else {
            every { datasource.getWelfareIncrement(policies[4].state) } returns Constants.WELFARE_TAX_INCREMENT_C
        }

        every {
            taxCalculator.calculateTaxMultiplier(

                Constants.BASE_TAX_INCREMENT_B,
                Constants.WELFARE_TAX_INCREMENT_B,
                Constants.WELFARE_TAX_INCREMENT_A,
                Constants.WELFARE_TAX_INCREMENT_C
            )
        } returns TAX_VALUE
    }

    private fun mockCalculateTaxIncomeFunctions(excRaised: List<Boolean>) {
        if (excRaised[0]) {
            every {
                datasource.getIncomeTax(
                    policies[1].state,
                    policies[2].state
                )
            } throws TaxException("")
        } else {
            every {
                datasource.getIncomeTax(
                    policies[1].state,
                    policies[2].state
                )
            } returns INCOME_TAX
        }
        if (excRaised[1]) {
            every {
                taxCalculator.calculateTaxIncome(
                    INCOME_TAX,
                    POPULATION
                )
            } throws TaxException("")
        } else {
            every {
                taxCalculator.calculateTaxIncome(
                    INCOME_TAX,
                    POPULATION
                )
            } returns INCOME_TAX_PRICE
        }
    }

    private fun verifyCalculateTaxMultiplierFunctions(shouldVerifyList: List<Boolean>) {
        verify(exactly = if (shouldVerifyList[0]) 1 else 0) {
            datasource.getBaseTaxIncrement(policies[2].state)
        }
        verify(exactly = if (shouldVerifyList[1]) 1 else 0) {
            datasource.getWelfareIncrement(policies[2].state)
        }
        verify(exactly = if (shouldVerifyList[2]) 1 else 0) {
            datasource.getWelfareIncrement(policies[3].state)
        }
        verify(exactly = if (shouldVerifyList[3]) 1 else 0) {
            datasource.getWelfareIncrement(policies[4].state)
        }
        verify(exactly = if (shouldVerifyList[4]) 1 else 0) {
            taxCalculator.calculateTaxMultiplier(
                Constants.BASE_TAX_INCREMENT_B,
                Constants.WELFARE_TAX_INCREMENT_B,
                Constants.WELFARE_TAX_INCREMENT_A,
                Constants.WELFARE_TAX_INCREMENT_C
            )
        }
    }

    private fun verifyCalculateTaxIncomeFunctions(shouldVerifyList: List<Boolean>) {
        verify(exactly = if (shouldVerifyList[0]) 1 else 0) {
            datasource.getIncomeTax(policies[1].state, policies[2].state)
        }
        verify(exactly = if (shouldVerifyList[1]) 1 else 0) {
            taxCalculator.calculateTaxIncome(INCOME_TAX, POPULATION)
        }
    }
}