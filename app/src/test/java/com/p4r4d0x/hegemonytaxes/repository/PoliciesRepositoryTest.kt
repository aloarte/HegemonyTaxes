package com.p4r4d0x.hegemonytaxes.repository

import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.domain_data.repository.PoliciesRepository
import com.p4r4d0x.hegemonytaxes.domain_data.repository.impl.PoliciesRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PoliciesRepositoryTest {

    @MockK
    private lateinit var datasource: PoliciesDatasource

    private lateinit var repository: PoliciesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PoliciesRepositoryImpl(datasource)
    }

    @Test
    fun `GIVEN datasource policies WHEN fetchPolicies THEN return list of policies from datasource`() {
        // GIVEN
        val policies = listOf(PolicyData(1, PolicyType.Immigration, PolicyState.B))
        coEvery { datasource.getPoliciesData() } returns policies

        // WHEN
        val detailResult = repository.fetchPolicies()

        // THEN
        coVerify { datasource.getPoliciesData() }
        Assert.assertEquals(policies, detailResult)
    }
}
