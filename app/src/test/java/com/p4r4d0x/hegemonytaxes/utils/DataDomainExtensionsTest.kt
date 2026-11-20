package com.p4r4d0x.hegemonytaxes.utils

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getIncomeTaxInvolvedPolicies
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getInvolvedTaxPoliciesSummary
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getSummary
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getTaxMultiplierInvolvedPolicies
import com.p4r4d0x.hegemonytaxes.utils.TestData.policies
import org.junit.Assert
import org.junit.Test

class DataDomainExtensionsTest {

    @Test
    fun `GIVEN list with all tax multiplier policies WHEN getTaxMultiplierInvolvedPolicies THEN return TaxMultiplierData`() {
        // GIVEN
        val inputPolicies = policies

        // WHEN
        val result = inputPolicies.getTaxMultiplierInvolvedPolicies()

        // THEN
        Assert.assertNotNull(result)
        Assert.assertEquals(PolicyType.Taxation, result?.taxation?.type)
        Assert.assertEquals(PolicyType.WEHealthcare, result?.weHealthcare?.type)
        Assert.assertEquals(PolicyType.WEEducation, result?.weEducation?.type)
    }

    @Test
    fun `GIVEN list missing healthcare policy WHEN getTaxMultiplierInvolvedPolicies THEN return null`() {
        // GIVEN
        val inputPolicies = policies.filterNot { it.type == PolicyType.WEHealthcare }

        // WHEN
        val result = inputPolicies.getTaxMultiplierInvolvedPolicies()

        // THEN
        Assert.assertNull(result)
    }

    @Test
    fun `GIVEN list with all income tax policies WHEN getIncomeTaxInvolvedPolicies THEN return TaxIncomeData`() {
        // GIVEN
        val inputPolicies = policies

        // WHEN
        val result = inputPolicies.getIncomeTaxInvolvedPolicies()

        // THEN
        Assert.assertNotNull(result)
        Assert.assertEquals(PolicyType.LaborMarket, result?.laborMarket?.type)
        Assert.assertEquals(PolicyType.Taxation, result?.taxation?.type)
    }

    @Test
    fun `GIVEN list missing labor market policy WHEN getIncomeTaxInvolvedPolicies THEN return null`() {
        // GIVEN
        val inputPolicies = policies.filterNot { it.type == PolicyType.LaborMarket }

        // WHEN
        val result = inputPolicies.getIncomeTaxInvolvedPolicies()

        // THEN
        Assert.assertNull(result)
    }

    @Test
    fun `GIVEN list with required policies WHEN getInvolvedTaxPoliciesSummary THEN return formatted summary string`() {
        // GIVEN
        val inputPolicies = policies

        // WHEN
        val summary = inputPolicies.getInvolvedTaxPoliciesSummary()

        // THEN
        Assert.assertEquals("2A / 3A", summary)
    }

    @Test
    fun `GIVEN list missing required policies WHEN getInvolvedTaxPoliciesSummary THEN return null`() {
        // GIVEN
        val inputPolicies = emptyList<PolicyData>()

        // WHEN
        val summary = inputPolicies.getInvolvedTaxPoliciesSummary()

        // THEN
        Assert.assertNull(summary)
    }

    @Test
    fun `GIVEN policy data WHEN getSummary THEN return concatenated number and state`() {
        // GIVEN
        val policy = PolicyData(number = 1, type = PolicyType.FiscalPolicy, state = PolicyState.B)

        // WHEN
        val summary = policy.getSummary()

        // THEN
        Assert.assertEquals("1B", summary)
    }
}
