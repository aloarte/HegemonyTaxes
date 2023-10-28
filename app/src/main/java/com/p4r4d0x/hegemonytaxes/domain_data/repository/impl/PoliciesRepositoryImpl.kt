package com.p4r4d0x.hegemonytaxes.domain_data.repository.impl

import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.repository.PoliciesRepository
import javax.inject.Inject

class PoliciesRepositoryImpl @Inject constructor(private val policiesDatasource: PoliciesDatasource) :
    PoliciesRepository {
    override fun fetchPolicies(): List<PolicyData> = policiesDatasource.getPoliciesData()
}