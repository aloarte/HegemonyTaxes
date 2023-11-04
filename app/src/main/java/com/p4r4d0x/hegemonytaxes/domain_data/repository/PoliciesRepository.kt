package com.p4r4d0x.hegemonytaxes.domain_data.repository

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData

interface PoliciesRepository {

    fun fetchPolicies(): List<PolicyData>

}