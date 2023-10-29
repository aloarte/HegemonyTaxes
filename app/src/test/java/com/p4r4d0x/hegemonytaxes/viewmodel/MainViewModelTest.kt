package com.p4r4d0x.hegemonytaxes.viewmodel

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
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
        mockFetchPoliciesCalculateTaxMultiplier(7)

        viewModel.fetchPolicies()

        verify { policiesRepository.fetchPolicies() }
        verify { taxRepository.calculateTaxMultiplier(any()) }
        Assert.assertEquals(
            UiState(policies = policies, taxMultiplier = 7),
            viewModel.state.first()
        )
    }

    @Test
    fun `test update policy`() = coroutinesTestRule.runBlockingTest {
        mockFetchPoliciesCalculateTaxMultiplier(1)
        viewModel.fetchPolicies()
        val updatedPolicy = policies[2].copy(state = PolicyState.A)

        viewModel.updatePolicy(updatedPolicy)

        verify { taxRepository.calculateTaxMultiplier(any()) }
        Assert.assertEquals(
            UiState(policies = policies, taxMultiplier = 1),
            viewModel.state.first()
        )
    }

    private fun mockFetchPoliciesCalculateTaxMultiplier(taxMultiplier:Int){
        every { policiesRepository.fetchPolicies() } returns policies
        every { taxRepository.calculateTaxMultiplier(any()) } returns taxMultiplier
    }

}