package com.p4r4d0x.hegemonytaxes.repository

import com.p4r4d0x.hegemonytaxes.domain_data.components.TaxCalculator
import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassTaxes
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
    fun `GIVEN correct policies WHEN getTaxMultiplier THEN return calculated tax multiplier`() {
        // GIVEN
        val exceptionsRaisedList = listOf(false, false, false, false)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        // WHEN
        val taxMultiplier = repository.getTaxMultiplier(policies)

        // THEN
        val shouldVerifyList = listOf(true, true, true, true, true)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(TAX_MULTIPLIER, taxMultiplier)
    }

    @Test
    fun `GIVEN exception in base tax calculation WHEN getTaxMultiplier THEN return invalid tax value`() {
        // GIVEN
        val exceptionsRaisedList = listOf(true, false, false, false)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        // WHEN
        val taxMultiplier = repository.getTaxMultiplier(policies)

        // THEN
        val shouldVerifyList = listOf(true, false, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `GIVEN exception in welfare tax calculation WHEN getTaxMultiplier THEN return invalid tax value`() {
        // GIVEN
        val exceptionsRaisedList = listOf(false, true, false, false)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        // WHEN
        val taxMultiplier = repository.getTaxMultiplier(policies)

        // THEN
        val shouldVerifyList = listOf(true, true, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `GIVEN exception in healthcare tax calculation WHEN getTaxMultiplier THEN return invalid tax value`() {
        // GIVEN
        val exceptionsRaisedList = listOf(false, false, true, false)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        // WHEN
        val taxMultiplier = repository.getTaxMultiplier(policies)

        // THEN
        val shouldVerifyList = listOf(true, true, true, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `GIVEN exception in education tax calculation WHEN getTaxMultiplier THEN return invalid tax value`() {
        // GIVEN
        val exceptionsRaisedList = listOf(false, false, false, true)
        mockGetTaxMultiplierFunctions(exceptionsRaisedList)

        // WHEN
        val taxMultiplier = repository.getTaxMultiplier(policies)

        // THEN
        val shouldVerifyList = listOf(true, true, true, true, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `GIVEN policies missing taxation policy WHEN getTaxMultiplier THEN return invalid tax value`() {
        // GIVEN & WHEN
        val taxMultiplier = repository.getTaxMultiplier(policiesMissingTaxation)

        // THEN
        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `GIVEN policies missing healthcare policy WHEN getTaxMultiplier THEN return invalid tax value`() {
        // GIVEN & WHEN
        val taxMultiplier = repository.getTaxMultiplier(policiesMissingHealthcare)

        // THEN
        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `GIVEN policies missing education policy WHEN getTaxMultiplier THEN return invalid tax value`() {
        // GIVEN & WHEN
        val taxMultiplier = repository.getTaxMultiplier(policiesMissingEducation)

        // THEN
        val shouldVerifyList = listOf(false, false, false, false, false)
        verifyGetTaxMultiplierFunctions(shouldVerifyList)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, taxMultiplier)
    }

    @Test
    fun `GIVEN policies list with taxation policy WHEN getTaxationPolicyState THEN return state of taxation policy`() {
        // GIVEN
        val testPolicies = listOf(PolicyData(3, PolicyType.Taxation, PolicyState.C))

        // WHEN
        val state = repository.getTaxationPolicyState(testPolicies)

        // THEN
        Assert.assertEquals(PolicyState.C, state)
    }

    @Test
    fun `GIVEN policies list without taxation policy WHEN getTaxationPolicyState THEN return default policy state B`() {
        // GIVEN
        val testPolicies = listOf(PolicyData(1, PolicyType.FiscalPolicy, PolicyState.A))

        // WHEN
        val state = repository.getTaxationPolicyState(testPolicies)

        // THEN
        Assert.assertEquals(PolicyState.B, state)
    }

    @Test
    fun `GIVEN correct policies WHEN getIncomeTax THEN return income tax value`() {
        // GIVEN
        mockGetTaxIncomeFunctions(false)

        // WHEN
        val taxMultiplier = repository.getIncomeTax(policies)

        // THEN
        verifyGetTaxIncomeFunctions(true)
        Assert.assertEquals(INCOME_TAX, taxMultiplier)
    }

    @Test
    fun `GIVEN exception in datasource WHEN getIncomeTax THEN return invalid tax value`() {
        // GIVEN
        mockGetTaxIncomeFunctions(true)

        // WHEN
        val incomeTax = repository.getIncomeTax(policies)

        // THEN
        verifyGetTaxIncomeFunctions(true)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, incomeTax)
    }

    @Test
    fun `GIVEN policies missing labor market policy WHEN getIncomeTax THEN return invalid tax value`() {
        // GIVEN & WHEN
        val incomeTax = repository.getIncomeTax(policiesMissingLaborMarket)

        // THEN
        verifyGetTaxIncomeFunctions(false)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, incomeTax)
    }

    @Test
    fun `GIVEN policies missing taxation policy WHEN getIncomeTax THEN return invalid tax value`() {
        // GIVEN & WHEN
        val incomeTax = repository.getIncomeTax(policiesMissingTaxation)

        // THEN
        verifyGetTaxIncomeFunctions(false)
        Assert.assertEquals(Constants.INVALID_TAX_VALUE, incomeTax)
    }

    @Test
    fun `GIVEN capitalist class inputs with positive net income WHEN calculateTaxes THEN return capitalist class taxes`() {
        // GIVEN
        val classInput = CapitalistClassInputs(companies = 3, profit = 120)
        every {
            taxCalculator.calculateCorporateTax(
                108, // classInput.profit - employmentTax
                PolicyState.A
            )
        } returns 40

        // WHEN
        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.A, classInput)

        // THEN
        verify {
            taxCalculator.calculateCorporateTax(
                108, // classInput.profit - employmentTax = 120 - 12 = 108
                PolicyState.A
            )
        }
        val expectedResult = CapitalistClassTaxes(
            employmentTaxResult = 12,
            corporateTaxResult = 40,
            reducedFromRevenue = 108,
            totalTaxes = 52
        )
        Assert.assertEquals(expectedResult, taxes)
    }

    @Test
    fun `GIVEN capitalist class inputs with negative net income WHEN calculateTaxes THEN return capitalist class taxes with zero corporate tax`() {
        // GIVEN
        val classInput = CapitalistClassInputs(companies = 3, profit = 11)
        every {
            taxCalculator.calculateCorporateTax(
                0, // classInput.profit - employmentTax = 11 - 12 = -1 (clamped to 0)
                PolicyState.A
            )
        } returns 0

        // WHEN
        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.A, classInput)

        // THEN
        verify {
            taxCalculator.calculateCorporateTax(
                0,
                PolicyState.A
            )
        }
        val expectedResult = CapitalistClassTaxes(
            employmentTaxResult = 12,
            corporateTaxResult = 0,
            reducedFromRevenue = 0,
            totalTaxes = 12
        )
        Assert.assertEquals(expectedResult, taxes)
    }

    @Test
    fun `GIVEN middle class inputs WHEN calculateTaxes THEN return middle class taxes`() {
        // GIVEN
        val classInput = MiddleClassInputs(ownCompanies = 6, externalCompaniesWithWorkers = 3)

        // WHEN
        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.A, classInput)

        // THEN
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
    fun `GIVEN state class inputs WHEN calculateTaxes THEN return state class taxes`() {
        // GIVEN
        val classInput = StateClassInputs(
            wcPopulation = 3,
            mcExternalCompaniesWithWorkers = 2,
            mcOwnCompanies = 3,
            ccCompanies = 8,
            ccProfit = 100
        )
        every {
            taxCalculator.calculateCorporateTax(
                classInput.ccProfit - TAX_MULTIPLIER * classInput.ccCompanies,
                PolicyState.B
            )
        } returns 15

        // WHEN
        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.B, classInput)

        // THEN
        verify {
            taxCalculator.calculateCorporateTax(
                classInput.ccProfit - TAX_MULTIPLIER * classInput.ccCompanies,
                PolicyState.B
            )
        }
        val expectedResult =
            StateClassTaxes(
                wcTaxes = 12,
                mcTaxes = 20,
                ccTaxes = 47,
                totalTaxes = 79
            )
        Assert.assertEquals(expectedResult, taxes)
    }

    @Test
    fun `GIVEN working class inputs WHEN calculateTaxes THEN return working class taxes`() {
        // GIVEN
        val classInput = WorkingClassInputs(population = 5)

        // WHEN
        val taxes = repository.calculateTaxes(TAX_MULTIPLIER, INCOME_TAX, PolicyState.A, classInput)

        // THEN
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

    private fun verifyGetTaxIncomeFunctions(shouldVerify: Boolean) {
        verify(exactly = if (shouldVerify) 1 else 0) {
            datasource.getIncomeTax(policies[1].state, policies[2].state)
        }
    }
}
