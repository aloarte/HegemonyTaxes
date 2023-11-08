package com.p4r4d0x.hegemonytaxes.presenter

import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.ResultTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.TaxesNotCalculated
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs

data class UiState(
    val policies: List<PolicyData> = emptyList(),
    val taxMultiplier: Int = 0,
    val incomeTax: Int = 0,
    val taxationPolicyState: PolicyState = PolicyState.Unknown,
    val resultTaxes: ResultTaxes = TaxesNotCalculated,
    val wcSelection: WorkingClassInputs = WorkingClassInputs(3),
    val mcSelection: MiddleClassInputs = MiddleClassInputs(0, 0),
    val ccSelection: CapitalistClassInputs = CapitalistClassInputs(0, 0),
    val stateSelection: StateClassInputs = StateClassInputs(3, 0, 0, 0, 0)

)