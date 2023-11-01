package com.p4r4d0x.hegemonytaxes.presenter

import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData


sealed interface UiEvent {

    data class UpdatePolicy(val policy: PolicyData) : UiEvent

    object GoWelcome : UiEvent

    object GoPolicySelector : UiEvent

    object GoPickRole : UiEvent

    data class GoRole(val role: HegemonyRole) : UiEvent


}