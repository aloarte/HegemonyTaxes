package com.p4r4d0x.hegemonytaxes.repository

import com.p4r4d0x.hegemonytaxes.domain_data.components.TaxCalculator
import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs
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
        private const val TAX_MULTIPLIER = 4
        private const val INCOME_TAX = 4
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
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.getTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, true, true, true, true)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(TAX_MULTIPLIER, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier having correct policies but wrong state 1`() {
        val exceptionsRaisedList = listOf(true, false, false, false)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.getTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, false, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier having correct policies but wrong state 2`() {
        val exceptionsRaisedList = listOf(false, true, false, false)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.getTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, true, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier having correct policies but wrong state 3`() {
        val exceptionsRaisedList = listOf(false, false, true, false)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.getTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, true, true, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }


    @Test
    fun `test calculate tax multiplier having correct policies but wrong state 4`() {
        val exceptionsRaisedList = listOf(false, false, false, true)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        val taxMultiplier = repository.getTaxMultiplier(policies)

        val shouldVerifyList = listOf(true, true, true, true, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier missing taxation policy`() {
        val taxMultiplier = repository.getTaxMultiplier(policiesMissingTaxation)

        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier missing healthcare policy`() {
        val taxMultiplier = repository.getTaxMultiplier(policiesMissingHealthcare)

        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test calculate tax multiplier missing education policy`() {
        val taxMultiplier = repository.getTaxMultiplier(policiesMissingEducation)

        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `test get income tax`() {
        mockGetTaxIncomeFunctions(false)
        val taxMultiplier = repository.getIncomeTax(policies)

        verifyGetTaxIncomeFunctions(true)
        Assert.assertEquals(INCOME_TAX, taxMultiplier)
    }


    @Test
    fun `test get income tax getIncomeTax exception`() {
        mockGetTaxIncomeFunctions(true)
        val incomeTax = repository.getIncomeTax(policies)

        verifyGetTaxIncomeFunctions(true)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, incomeTax)
    }

    @Test
    fun `test get income tax missing labor market policy`() {
        val incomeTax = repository.getIncomeTax(policiesMissingLaborMarket)

        verifyGetTaxIncomeFunctions(false)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, incomeTax)
    }

    @Test
    fun `test get income tax missing taxation policy`() {
        val incomeTax = repository.getIncomeTax(policiesMissingTaxation)

        verifyGetTaxIncomeFunctions(false)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, incomeTax)
    }

    @Test
    fun `test calculate capitalist class taxes`() {
        val classInput = CapitalistClassInputs(companies = 3, profit = 120)
        every { taxCalculator.calculateCorporateTax(classInput.profit, PolicyState.A) } returns 40

        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.A, classInput)

        verify { taxCalculator.calculateCorporateTax(classInput.profit, PolicyState.A) }
        val expectedResult =CapitalistClassTaxes(
                employmentTaxResult = 12,
                corporateTaxResult = 40,
                totalTaxes = 52
            )
        Assert.assertEquals(expectedResult, taxes)
    }

    @Test
    fun `test calculate middle class taxes`() {
        val classInput = MiddleClassInputs(ownCompanies = 6, externalCompaniesWithWorkers = 3)

        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.A, classInput)

        verify(exactly = 0) { taxCalculator.calculateCorporateTax(any(), any()) }
        val expectedResult =
            MiddleClassTaxes(
                employmentTaxResult = 24,
                incomeTaxResult = 12,
                totalTaxes = 36
            )
        Assert.assertEquals(expectedResult, taxes)
    }

    @Test
    fun `test calculate state class taxes`() {
        val classInput = StateClassInputs(
            wcPopulation = 3,
            mcExternalCompaniesWithWorkers = 2,
            mcOwnCompanies = 2,
            ccCompanies = 4,
            ccProfit = 80)
        every { taxCalculator.calculateCorporateTax(classInput.ccProfit, PolicyState.A) } returns 40

        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.A, classInput)

        verify{ taxCalculator.calculateCorporateTax(classInput.ccProfit, PolicyState.A) }
        val expectedResult =
            StateClassTaxes(
                wcTaxes = 12,
                mcTaxes = 16,
                ccTaxes = 56,
                totalTaxes = 84
            )
        Assert.assertEquals(expectedResult, taxes)
    }

    @Test
    fun `test calculate working class taxes`() {
        val classInput = WorkingClassInputs(population = 5)

        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.A, classInput)

        verify(exactly = 0) { taxCalculator.calculateCorporateTax(any(), any()) }
        Assert.assertEquals(WorkingClassTaxes(incomeTaxResult = 20), taxes)
    }


    private fun mockGetTaxMultiplierFunctions(excRaised: List<Boolean>) {
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
        } returns TAX_MULTIPLIER
    }

    private fun mockGetTaxIncomeFunctions(excRaised: Boolean) {
        if (excRaised) {
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
    }

    private fun verifyGetTaxMultiplierFunctions(shouldVerifyList: List<Boolean>) {
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

    private fun verifyGetTaxIncomeFunctions(shouldVerift: Boolean) {
        verify(exactly = if (shouldVerift) 1 else 0) {
            datasource.getIncomeTax(policies[1].state, policies[2].state)
        }
    }
}