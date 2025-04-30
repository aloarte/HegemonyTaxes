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
import java.lang.Integer.max
import javax.inject.Inject

class TaxRepositoryImpl @Inject constructor(
    private val policiesDatasource: PoliciesDatasource,
    private val taxCalculator: TaxCalculator
) :
    TaxRepository {
    override fun getTaxMultiplier(policies: List<PolicyData>): Int {
        return try {
            policies.getTaxMultiplierInvolvedPolicies()?.let { involvedPolicies ->
                val baseTax =
                    policiesDatasource.getBaseTaxIncrement(involvedPolicies.taxation.state)
                val welfareTaxMultiplier =
                    policiesDatasource.getWelfareIncrement(involvedPolicies.taxation.state)
                val healthcareTaxIncrement =
                    policiesDatasource.getWelfareIncrement(involvedPolicies.weHealthcare.state)
                val educationTaxIncrement =
                    policiesDatasource.getWelfareIncrement(involvedPolicies.weEducation.state)

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
            policies.getIncomeTaxInvolvedPolicies()?.let { involvedPolicies ->
                policiesDatasource.getIncomeTax(
                    involvedPolicies.laborMarket.state,
                    involvedPolicies.taxation.state
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
                val (employmentTax, corporateTax) = calculateCCTax(
                    taxationPolicyState = taxationPolicyState,
                    taxMultiplier = taxMultiplier,
                    companies = roleData.companies,
                    profit = roleData.profit
                )
                CapitalistClassTaxes(
                    employmentTax,
                    corporateTax,
                    employmentTax + corporateTax
                )
            }

            is MiddleClassInputs -> {
                val (incomeTaxR, employmentTaxR) = calculateMCTax(
                    taxMultiplier = taxMultiplier,
                    incomeTax = incomeTax,
                    externalCompaniesWithWorkers = roleData.externalCompaniesWithWorkers,
                    ownCompanies = roleData.ownCompanies
                )

                MiddleClassTaxes(
                    incomeTaxR,
                    employmentTaxR,
                    incomeTaxR + employmentTaxR
                )
            }

            is WorkingClassInputs -> {
                WorkingClassTaxes(
                    calculateWCTax(
                        incomeTax = incomeTax,
                        population = roleData.population
                    )
                )
            }

            is StateClassInputs -> {
                val wcTaxes = calculateWCTax(
                    incomeTax = incomeTax,
                    population = roleData.wcPopulation
                )
                val (incomeTaxR, employmentTaxR) = calculateMCTax(
                    taxMultiplier = taxMultiplier,
                    incomeTax = incomeTax,
                    externalCompaniesWithWorkers = roleData.mcExternalCompaniesWithWorkers,
                    ownCompanies = roleData.mcOwnCompanies
                )
                val mcTaxes = incomeTaxR + employmentTaxR

                val (ccEmploymentTax, ccCorporateTax) = calculateCCTax(
                    taxationPolicyState = taxationPolicyState,
                    taxMultiplier = taxMultiplier,
                    companies = roleData.ccCompanies,
                    profit = roleData.ccProfit
                )
                val ccTaxes = ccEmploymentTax + ccCorporateTax
                StateClassTaxes(
                    wcTaxes = wcTaxes,
                    mcTaxes = mcTaxes,
                    ccTaxes = ccTaxes,
                    totalTaxes = wcTaxes + mcTaxes + ccTaxes
                )
            }
        }
    }

    private fun calculateWCTax(
        incomeTax: Int,
        population: Int
    ) = incomeTax * population

    private fun calculateMCTax(
        taxMultiplier: Int,
        incomeTax: Int,
        externalCompaniesWithWorkers: Int,
        ownCompanies: Int
    ): Pair<Int, Int> {
        val incomeTaxM = incomeTax * externalCompaniesWithWorkers
        val employmentTax = taxMultiplier * ownCompanies
        return incomeTaxM to employmentTax
    }

    private fun calculateCCTax(
        taxationPolicyState: PolicyState,
        taxMultiplier: Int,
        companies: Int,
        profit: Int
    ): Pair<Int, Int> {
        val employmentTax = taxMultiplier * companies
        val corporateTaxR =
            taxCalculator.calculateCorporateTax(
                max(profit - employmentTax, 0),
                taxationPolicyState
            )
        return employmentTax to corporateTaxR
    }


}