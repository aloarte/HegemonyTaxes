package com.p4r4d0x.hegemonytaxes.utils


import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType

object TestData {
    val policies = listOf(
        PolicyData(
            number = 1,
            state = PolicyState.A,
            type = PolicyType.FiscalPolicy
        ),
        PolicyData(
            number = 2,
            state = PolicyState.A,
            type = PolicyType.LaborMarket
        ),
        PolicyData(
            number = 3,
            state = PolicyState.A,
            type = PolicyType.Taxation
        ),
        PolicyData(
            number = 4,
            state = PolicyState.B,
            type = PolicyType.WEHealthcare
        ),
        PolicyData(
            number = 5,
            state = PolicyState.C,
            type = PolicyType.WEEducation
        )
    )

}