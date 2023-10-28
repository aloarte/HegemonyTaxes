package com.p4r4d0x.hegemonytaxes.domain_data.repository.impl

import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.repository.TaxRepository
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getTaxMultiplierInvolvedPolicies
import javax.inject.Inject

class TaxRepositoryImpl @Inject constructor(private val policiesDatasource: PoliciesDatasource) :
    TaxRepository {
    override fun calculateTaxMultiplier(policies: List<PolicyData>): Int =
        policies.getTaxMultiplierInvolvedPolicies()?.let { policies ->
            val baseTax = policiesDatasource.getBaseTaxIncrement(policies.taxation.state)
            val welfareTaxMultiplier =
                policiesDatasource.getWelfareIncrement(policies.taxation.state)
            val healthcareTaxIncrement =
                policiesDatasource.getWelfareIncrement(policies.weHealthcare.state)
            val educationTaxIncrement =
                policiesDatasource.getWelfareIncrement(policies.weEducationPolicyData.state)

            baseTax + (welfareTaxMultiplier * healthcareTaxIncrement) + (welfareTaxMultiplier * educationTaxIncrement)
        } ?: 0

}