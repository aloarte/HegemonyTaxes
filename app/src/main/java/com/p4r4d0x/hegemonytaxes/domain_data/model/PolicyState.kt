package com.p4r4d0x.hegemonytaxes.domain_data.model

enum class PolicyState(val value:Float) {
    A(1f),B(2f),C(3f),Unknown(-1f);

    companion object {
        fun getState(value: Float): PolicyState = values().find { it.value == value } ?: Unknown

        fun fromState(policyState: PolicyState): Float = policyState.value
    }

}