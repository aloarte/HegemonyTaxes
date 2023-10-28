package com.p4r4d0x.hegemonytaxes.domain_data.datasource.impl

import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import javax.inject.Inject

class PoliciesDatasourceImpl @Inject constructor():PoliciesDatasource {

    override fun getBaseTaxIncrement(policyState: PolicyState) = when (policyState) {
        PolicyState.A -> 3
        PolicyState.B -> 2
        PolicyState.C -> 1
        PolicyState.Unknown -> 0
    }

    override fun getWelfareIncrement(policyState: PolicyState) = when (policyState) {
        PolicyState.A -> 2
        PolicyState.B -> 1
        PolicyState.C -> 0
        PolicyState.Unknown -> 0
    }

    override fun getPoliciesData(): List<PolicyData> = listOf(
        PolicyData(
            1,
            "Fiscal Policy",
            PolicyType.FiscalPolicy,
            PolicyState.A
        ),
        PolicyData(
            2,
            "Labor Market",
            PolicyType.LaborMarket,
            PolicyState.A
        ),
        PolicyData(
            3,
            "Taxation",
            PolicyType.Taxation,
            PolicyState.A
        ),
        PolicyData(
            4,
            "Welfare State: Healthcare & benefits",
            PolicyType.WEHealthcare,
            PolicyState.A
        ),
        PolicyData(
            5,
            "Welfare State: Education",
            PolicyType.WEEducation,
            PolicyState.A
        ),
        PolicyData(
            6,
            "Foreign Trade",
            PolicyType.ForeignTrade,
            PolicyState.A
        ),
        PolicyData(
            7,
            "Immigration",
            PolicyType.Immigration,
            PolicyState.A
        )

    )
}