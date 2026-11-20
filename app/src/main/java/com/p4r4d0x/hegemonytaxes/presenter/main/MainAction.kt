package com.p4r4d0x.hegemonytaxes.presenter.main

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.RoleInputs

sealed interface MainAction {
    object FetchPolicies : MainAction
    object ResetPolicies : MainAction
    data class UpdatePolicy(val policy: PolicyData) : MainAction
    data class CalculateTaxes(val roleData: RoleInputs) : MainAction
    object ClearTaxes : MainAction
    data class UpdateSelection(val roleData: RoleInputs) : MainAction
    data class UpdateTitleVisibility(val show: Boolean) : MainAction
}
