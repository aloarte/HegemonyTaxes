package com.p4r4d0x.hegemonytaxes.viewmodel

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.ResultTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.repository.PoliciesRepository
import com.p4r4d0x.hegemonytaxes.domain_data.repository.TaxRepository
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.main.MainViewModel
import com.p4r4d0x.hegemonytaxes.utils.CoroutinesTestRule
import com.p4r4d0x.hegemonytaxes.utils.TestData.policies
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var taxRepository: TaxRepository

    @MockK
    private lateinit var policiesRepository: PoliciesRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(taxRepository, policiesRepository)
    }

    @Test
    fun `test fetch policies`() = coroutinesTestRule.runBlockingTest {
        every { policiesRepository.fetchPolicies() } returns policies
        mockGetTaxes(1, 4, PolicyState.A)

        val state =
            viewModel.state.first() //This forces the fetchPolicies call that is on the onStart method

        verify { policiesRepository.fetchPolicies() }
        verifyGetTaxes()
        val expectedState = UiState(
            policies = policies,
            taxMultiplier = 1,
            incomeTax = 4,
            taxationPolicyState = PolicyState.A
        )
        Assert.assertEquals(expectedState, state)
    }

    @Test
    fun `test update policy`() = coroutinesTestRule.runBlockingTest {
        mockAndCallFetchPolicies()
        viewModel.state.first() //This forces the fetchPolicies call that is on the onStart method
        val updatedPolicy = policies[2].copy(state = PolicyState.B)
        viewModel.updatePolicy(updatedPolicy)

        verifyGetTaxes()
        val updatedPolicies = policies.toMutableList().apply { this[2] = updatedPolicy }
        Assert.assertEquals(updatedPolicies, viewModel.state.first().policies)
    }

    @Ignore("")
    @Test
    fun `test calculate tax result`() = coroutinesTestRule.runBlockingTest {
        mockAndCallFetchPolicies()
        viewModel.state.first() //This forces the fetchPolicies call that is on the onStart method
        val inputData = WorkingClassInputs(population = 3)
        val taxesResult: ResultTaxes = WorkingClassTaxes(12)
        every { taxRepository.calculateTaxes(1, 4, PolicyState.A, inputData) } returns taxesResult

        viewModel.calculateTaxesResult(inputData)

        verifyGetTaxes()
        verify { taxRepository.calculateTaxes(1, 4, PolicyState.A, inputData) }
        Assert.assertEquals(taxesResult, viewModel.state.first().resultTaxes)
    }

    private fun mockAndCallFetchPolicies() {
        every { policiesRepository.fetchPolicies() } returns policies
        mockGetTaxes(1, 4, PolicyState.A)
        viewModel.fetchPolicies()
    }

    private fun mockGetTaxes(
        taxMultiplier: Int, taxIncome: Int, taxationState: PolicyState
    ) {
        every { taxRepository.getTaxMultiplier(any()) } returns taxMultiplier
        every { taxRepository.getIncomeTax(any()) } returns taxIncome
        every { taxRepository.getTaxationPolicyState(any()) } returns taxationState
    }

    private fun verifyGetTaxes() {
        every { taxRepository.getTaxMultiplier(any()) }
        every { taxRepository.getIncomeTax(any()) }
        every { taxRepository.getTaxationPolicyState(any()) }
    }

}