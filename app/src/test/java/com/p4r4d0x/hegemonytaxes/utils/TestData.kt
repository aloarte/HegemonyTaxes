package com.p4r4d0x.hegemonytaxes.utils


import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType

object TestData {
    val policies = listOf(
        PolicyData(
            number = 1,
            name = PolicyType.FiscalPolicy.name,
            state = PolicyState.A,
            type = PolicyType.FiscalPolicy
        ),
        PolicyData(
            number = 1,
            name = PolicyType.LaborMarket.name,
            state = PolicyState.A,
            type = PolicyType.LaborMarket
        ),
        PolicyData(
            number = 1,
            name = PolicyType.Taxation.name,
            state = PolicyState.A,
            type = PolicyType.Taxation
        ),
        PolicyData(
            number = 1,
            name = PolicyType.WEHealthcare.name,
            state = PolicyState.B,
            type = PolicyType.WEHealthcare
        ),
        PolicyData(
            number = 1,
            name = PolicyType.WEEducation.name,
            state = PolicyState.C,
            type = PolicyType.WEEducation
        )
    )

}