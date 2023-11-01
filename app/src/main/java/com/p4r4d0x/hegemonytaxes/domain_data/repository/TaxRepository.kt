package com.p4r4d0x.hegemonytaxes.domain_data.repository

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData

interface TaxRepository {

    fun calculateTaxMultiplier(policies:List<PolicyData>):Int

    fun calculateIncomeTax(policies:List<PolicyData>,population: Int):Int

}