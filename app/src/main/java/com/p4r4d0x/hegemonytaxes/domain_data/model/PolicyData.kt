package com.p4r4d0x.hegemonytaxes.domain_data.model

data class PolicyData(
    val number: Int,
    val name: String,
    val type: PolicyType,
    val state: PolicyState
)