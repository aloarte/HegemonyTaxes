package com.p4r4d0x.hegemonytaxes.domain_data.repository.impl

import com.p4r4d0x.hegemonytaxes.domain_data.components.TaxCalculator
import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.domain_data.model.ResultTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.RoleInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.repository.TaxRepository
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INVALID_TAX_VALUE
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getIncomeTaxInvolvedPolicies
import com.p4r4d0x.hegemonytaxes.domain_data.utils.getTaxMultiplierInvolvedPolicies
import javax.inject.Inject

class TaxRepositoryImpl @Inject constructor(
    private val policiesDatasource: PoliciesDatasource,
    private val taxCalculator: TaxCalculator
) :
    TaxRepository {
    override fun getTaxMultiplier(policies: List<PolicyData>): Int {
        return try {
            policies.getTaxMultiplierInvolvedPolicies()?.let { policies ->
                val baseTax = policiesDatasource.getBaseTaxIncrement(policies.taxation.state)
                val welfareTaxMultiplier =
                    policiesDatasource.getWelfareIncrement(policies.taxation.state)
                val healthcareTaxIncrement =
                    policiesDatasource.getWelfareIncrement(policies.weHealthcare.state)
                val educationTaxIncrement =
                    policiesDatasource.getWelfareIncrement(policies.weEducation.state)

                taxCalculator.calculateTaxMultiplier(
                    baseTax,
                    welfareTaxMultiplier,
                    healthcareTaxIncrement,
                    educationTaxIncrement
                )
            } ?: INVALID_TAX_VALUE
        } catch (ex: TaxException) {
            INVALID_TAX_VALUE
        }
    }

    override fun getTaxationPolicyState(policies: List<PolicyData>): PolicyState =
        policies.find { it.type == PolicyType.Taxation }?.state ?: PolicyState.B

    override fun getIncomeTax(policies: List<PolicyData>): Int {
        return try {
            policies.getIncomeTaxInvolvedPolicies()?.let { policies ->
                policiesDatasource.getIncomeTax(
                    policies.laborMarket.state,
                    policies.taxation.state
                )
            } ?: INVALID_TAX_VALUE
        } catch (ex: TaxException) {
            INVALID_TAX_VALUE
        }
    }

    override fun calculateTaxes(
        taxMultiplier: Int,
        incomeTax: Int,
        taxationPolicyState: PolicyState,
        roleData: RoleInputs
    ): ResultTaxes {
        return when (roleData) {
            is CapitalistClassInputs -> {
                val employmentTaxR = taxMultiplier * roleData.companies
                val corporateTaxR =
                    taxCalculator.calculateCorporateTax(roleData.profit, taxationPolicyState)
                CapitalistClassTaxes(
                    employmentTaxR,
                    corporateTaxR,
                    employmentTaxR + corporateTaxR
                )
            }

            is MiddleClassInputs -> {
                val incomeTaxR = incomeTax * roleData.externalCompaniesWithWorkers
                val employmentTaxR = taxMultiplier * roleData.ownCompanies
                MiddleClassTaxes(
                    incomeTaxR,
                    employmentTaxR,
                    incomeTaxR + employmentTaxR
                )
            }

            is WorkingClassInputs -> {
                WorkingClassTaxes(incomeTax * roleData.population)
            }

            is StateClassInputs -> {
                val wcTaxes = incomeTax * roleData.wcPopulation
                val mcTaxes = incomeTax * roleData.mcExternalCompaniesWithWorkers +
                        taxMultiplier * roleData.mcOwnCompanies
                val ccTaxes = incomeTax * taxMultiplier * roleData.ccCompanies +
                        taxCalculator.calculateCorporateTax(roleData.ccProfit, taxationPolicyState)
                StateClassTaxes(
                    wcTaxes = wcTaxes,
                    mcTaxes = mcTaxes, ccTaxes = ccTaxes, totalTaxes = wcTaxes + mcTaxes + ccTaxes
                )
            }
        }
    }

}