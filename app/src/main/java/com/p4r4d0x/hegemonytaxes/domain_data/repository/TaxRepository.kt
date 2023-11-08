package com.p4r4d0x.hegemonytaxes.domain_data.repository

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.ResultTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.RoleInputs

interface TaxRepository {

    fun getTaxMultiplier(policies: List<PolicyData>): Int

    fun getIncomeTax(policies: List<PolicyData>): Int

    fun calculateTaxes(taxMultiplier:Int, incomeTax:Int, taxationPolicyState:PolicyState, roleData: RoleInputs): ResultTaxes

    fun getTaxationPolicyState(policies: List<PolicyData>): PolicyState

}