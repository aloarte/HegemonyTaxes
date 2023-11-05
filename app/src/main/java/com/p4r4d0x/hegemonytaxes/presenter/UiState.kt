package com.p4r4d0x.hegemonytaxes.presenter

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.ResultTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.TaxesNotCalculated

data class UiState(
    val policies: List<PolicyData> = emptyList(),
    val taxMultiplier: Int = 0,
    val incomeTax:Int=0,
    val taxationPolicyState:PolicyState = PolicyState.Unknown,
    val resultTaxes: ResultTaxes = TaxesNotCalculated
)