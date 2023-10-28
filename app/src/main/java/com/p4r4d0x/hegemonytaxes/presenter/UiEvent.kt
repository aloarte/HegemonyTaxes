package com.p4r4d0x.hegemonytaxes.presenter

import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData


sealed interface UiEvent {

    object GoWelcome : UiEvent

    object GoPolicySelector : UiEvent

    object GoPickRole : UiEvent

    data class GoRole(val policies:List<PolicyData>, val role: HegemonyRole) : UiEvent


}