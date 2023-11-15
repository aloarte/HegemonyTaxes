package com.p4r4d0x.hegemonytaxes.presenter

import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.RoleInputs


sealed interface UiEvent {

    data class UpdatePolicy(val policy: PolicyData) : UiEvent

    abstract class GoScreen(val displayTitle:Boolean): UiEvent

    abstract class UiAction: UiEvent


    object GoWelcome : GoScreen(true)

    object GoPolicySelector : GoScreen(true)

    object GoPickRole : GoScreen(false)

    class GoRole(val role: HegemonyRole) : GoScreen(false)

    class CalculateTaxes(val roleData: RoleInputs) : UiAction()

    object ClearTaxes : UiAction()

    class UpdateTitleVisibility(val show:Boolean) : UiAction()


}