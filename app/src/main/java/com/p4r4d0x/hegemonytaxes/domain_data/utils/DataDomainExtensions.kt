package com.p4r4d0x.hegemonytaxes.domain_data.utils

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.domain_data.model.TaxIncomeData
import com.p4r4d0x.hegemonytaxes.domain_data.model.TaxMultiplierData

fun List<PolicyData>.getTaxMultiplierInvolvedPolicies(): TaxMultiplierData? {
    val taxationPolicy = find { it.type == PolicyType.Taxation }
    val wsHealthcarePolicy = find { it.type == PolicyType.WEHealthcare }
    val wsEducationPolicy = find { it.type == PolicyType.WEEducation }

    return if (taxationPolicy != null && wsHealthcarePolicy != null && wsEducationPolicy != null) {
        TaxMultiplierData(taxationPolicy, wsHealthcarePolicy, wsEducationPolicy)
    } else {
        null
    }
}

fun List<PolicyData>.getIncomeTaxInvolvedPolicies(): TaxIncomeData? {
    val laborMarketPolicy = find { it.type == PolicyType.LaborMarket }
    val taxationPolicy = find { it.type == PolicyType.Taxation }

    return if (laborMarketPolicy != null && taxationPolicy != null ) {
        TaxIncomeData(laborMarketPolicy, taxationPolicy)
    } else {
        null
    }
}

fun List<PolicyData>.getInvolvedTaxPoliciesSummary(): String? =
    getIncomeTaxInvolvedPolicies()?.let {
        "${it.laborMarket.getSummary()} / ${it.taxation.getSummary()}"
    }

fun PolicyData.getSummary() = "${number}${state}"
