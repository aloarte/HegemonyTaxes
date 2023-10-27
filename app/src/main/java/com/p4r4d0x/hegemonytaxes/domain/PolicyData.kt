package com.p4r4d0x.hegemonytaxes.domain

import com.p4r4d0x.hegemonytaxes.PolicyType

data class PolicyData(val number: Int, val name: String, val type: PolicyType,val defaultState:PolicyState) {
}