package com.p4r4d0x.hegemonytaxes.viewmodel

import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.domain_data.model.ResultTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.TaxesNotCalculated
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.repository.PoliciesRepository
import com.p4r4d0x.hegemonytaxes.domain_data.repository.TaxRepository
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.main.MainAction
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
    fun `GIVEN repository policies WHEN state collected THEN state updated with fetched policies and taxes`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            every { policiesRepository.fetchPolicies() } returns policies
            mockGetTaxes(1, 4, PolicyState.A)

            // WHEN
            val state = viewModel.state.first()

            // THEN
            verify(exactly = 1) { policiesRepository.fetchPolicies() }
            verifyGetTaxes(1)
            val expectedState = UiState(
                policies = policies,
                taxMultiplier = 1,
                incomeTax = 4,
                taxationPolicyState = PolicyState.A
            )
            Assert.assertEquals(expectedState, state)
        }

    @Test
    fun `GIVEN initial policies fetched WHEN reset policies action triggered THEN policies fetched again`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            viewModel.state.first()

            // WHEN
            viewModel.onAction(MainAction.ResetPolicies)

            // THEN
            verify(exactly = 2) { policiesRepository.fetchPolicies() }
        }

    @Test
    fun `GIVEN initial policies WHEN update policy action triggered THEN policy updated and taxes recalculated`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            viewModel.state.first()
            val updatedPolicy = policies[2].copy(state = PolicyState.B)

            // WHEN
            viewModel.onAction(MainAction.UpdatePolicy(updatedPolicy))

            // THEN
            verifyGetTaxes(2)
            val updatedPolicies = policies.toMutableList().apply { this[2] = updatedPolicy }
            Assert.assertEquals(updatedPolicies, viewModel.state.first().policies)
        }

    @Test
    fun `GIVEN initial policies WHEN update policy action with unknown policy triggered THEN policies state unchanged`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            val initialPolicies = viewModel.state.first().policies
            val unknownPolicy = PolicyData(99, PolicyType.ForeignTrade, PolicyState.A)

            // WHEN
            viewModel.onAction(MainAction.UpdatePolicy(unknownPolicy))

            // THEN
            Assert.assertEquals(initialPolicies, viewModel.state.first().policies)
        }

    @Test
    fun `GIVEN working class role inputs WHEN calculate taxes action triggered THEN result taxes and selection updated`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            viewModel.state.first()
            val inputData = WorkingClassInputs(population = 3)
            val taxesResult: ResultTaxes = WorkingClassTaxes(12)
            every { taxRepository.calculateTaxes(1, 4, PolicyState.A, inputData) } returns taxesResult

            // WHEN
            viewModel.onAction(MainAction.CalculateTaxes(inputData))

            // THEN
            verify { taxRepository.calculateTaxes(1, 4, PolicyState.A, inputData) }
            val currentState = viewModel.state.first()
            Assert.assertEquals(taxesResult, currentState.resultTaxes)
            Assert.assertEquals(inputData, currentState.wcSelection)
        }

    @Test
    fun `GIVEN calculated taxes result WHEN clear taxes action triggered THEN result taxes reset`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            viewModel.state.first()
            val inputData = WorkingClassInputs(population = 3)
            val taxesResult: ResultTaxes = WorkingClassTaxes(12)
            every { taxRepository.calculateTaxes(1, 4, PolicyState.A, inputData) } returns taxesResult
            viewModel.onAction(MainAction.CalculateTaxes(inputData))

            // WHEN
            viewModel.onAction(MainAction.ClearTaxes)

            // THEN
            Assert.assertEquals(TaxesNotCalculated, viewModel.state.first().resultTaxes)
        }

    @Test
    fun `GIVEN capitalist class inputs WHEN update selection action triggered THEN capitalist class selection updated`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            val selection = CapitalistClassInputs(companies = 5, profit = 200)

            // WHEN
            viewModel.onAction(MainAction.UpdateSelection(selection))

            // THEN
            Assert.assertEquals(selection, viewModel.state.first().ccSelection)
        }

    @Test
    fun `GIVEN middle class inputs WHEN update selection action triggered THEN middle class selection updated`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            val selection = MiddleClassInputs(ownCompanies = 2, externalCompaniesWithWorkers = 4)

            // WHEN
            viewModel.onAction(MainAction.UpdateSelection(selection))

            // THEN
            Assert.assertEquals(selection, viewModel.state.first().mcSelection)
        }

    @Test
    fun `GIVEN state class inputs WHEN update selection action triggered THEN state class selection updated`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            val selection = StateClassInputs(
                wcPopulation = 1,
                mcExternalCompaniesWithWorkers = 2,
                mcOwnCompanies = 3,
                ccCompanies = 4,
                ccProfit = 50
            )

            // WHEN
            viewModel.onAction(MainAction.UpdateSelection(selection))

            // THEN
            Assert.assertEquals(selection, viewModel.state.first().stateSelection)
        }

    @Test
    fun `GIVEN working class inputs WHEN update selection action triggered THEN working class selection updated`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()
            val selection = WorkingClassInputs(population = 10)

            // WHEN
            viewModel.onAction(MainAction.UpdateSelection(selection))

            // THEN
            Assert.assertEquals(selection, viewModel.state.first().wcSelection)
        }

    @Test
    fun `GIVEN initial state WHEN update title visibility action triggered THEN display title state updated`() =
        coroutinesTestRule.runBlockingTest {
            // GIVEN
            mockAndCallFetchPolicies()

            // WHEN
            viewModel.onAction(MainAction.UpdateTitleVisibility(false))

            // THEN
            Assert.assertFalse(viewModel.state.first().displayTitleOnAppBar)

            // WHEN
            viewModel.onAction(MainAction.UpdateTitleVisibility(true))

            // THEN
            Assert.assertTrue(viewModel.state.first().displayTitleOnAppBar)
        }

    private fun mockAndCallFetchPolicies() {
        every { policiesRepository.fetchPolicies() } returns policies
        mockGetTaxes(1, 4, PolicyState.A)
    }

    private fun mockGetTaxes(
        taxMultiplier: Int, taxIncome: Int, taxationState: PolicyState
    ) {
        every { taxRepository.getTaxMultiplier(any()) } returns taxMultiplier
        every { taxRepository.getIncomeTax(any()) } returns taxIncome
        every { taxRepository.getTaxationPolicyState(any()) } returns taxationState
    }

    private fun verifyGetTaxes(count: Int = 1) {
        verify(exactly = count) { taxRepository.getTaxMultiplier(any()) }
        verify(exactly = count) { taxRepository.getIncomeTax(any()) }
        verify(exactly = count) { taxRepository.getTaxationPolicyState(any()) }
    }

}