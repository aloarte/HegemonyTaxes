package com.p4r4d0x.hegemonytaxes.domain_data.datasource

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState

interface PoliciesDatasource {

    fun getBaseTaxIncrement(policyState: PolicyState):Int

    fun getWelfareIncrement(policyState: PolicyState):Int

    fun getPoliciesData():List<PolicyData>

}