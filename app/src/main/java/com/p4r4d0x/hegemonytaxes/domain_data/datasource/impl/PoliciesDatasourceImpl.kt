package com.p4r4d0x.hegemonytaxes.domain_data.datasource.impl

import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.BASE_TAX_INCREMENT_A
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.BASE_TAX_INCREMENT_B
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.BASE_TAX_INCREMENT_C
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2A_3A
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2A_3B
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2A_3C
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2B_3A
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2B_3B
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2B_3C
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2C_3A
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2C_3B
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.INCOME_TAX_2C_3C
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.WELFARE_TAX_INCREMENT_A
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.WELFARE_TAX_INCREMENT_B
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.WELFARE_TAX_INCREMENT_C
import javax.inject.Inject

class PoliciesDatasourceImpl @Inject constructor() : PoliciesDatasource {

    override fun getBaseTaxIncrement(policyState: PolicyState) = when (policyState) {
        PolicyState.A -> BASE_TAX_INCREMENT_A
        PolicyState.B -> BASE_TAX_INCREMENT_B
        PolicyState.C -> BASE_TAX_INCREMENT_C
        PolicyState.Unknown -> throw TaxException("getBaseTaxIncrement Unknown policy state")
    }

    override fun getWelfareIncrement(policyState: PolicyState) = when (policyState) {
        PolicyState.A -> WELFARE_TAX_INCREMENT_A
        PolicyState.B -> WELFARE_TAX_INCREMENT_B
        PolicyState.C -> WELFARE_TAX_INCREMENT_C
        PolicyState.Unknown -> throw TaxException("getWelfareIncrement Unknown policy state")
    }

    override fun getIncomeTax(laborMarketState: PolicyState, taxationState: PolicyState) =
        when (laborMarketState) {
            PolicyState.A -> when (taxationState) {
                PolicyState.A -> INCOME_TAX_2A_3A
                PolicyState.B -> INCOME_TAX_2A_3B
                PolicyState.C -> INCOME_TAX_2A_3C
                PolicyState.Unknown -> throw TaxException("getIncomeTax labor market B, unknown state")
            }

            PolicyState.B -> when (taxationState) {
                PolicyState.A -> INCOME_TAX_2B_3A
                PolicyState.B -> INCOME_TAX_2B_3B
                PolicyState.C -> INCOME_TAX_2B_3C
                PolicyState.Unknown -> throw TaxException("getIncomeTax labor market B, unknown state")
            }

            PolicyState.C -> when (taxationState) {
                PolicyState.A -> INCOME_TAX_2C_3A
                PolicyState.B -> INCOME_TAX_2C_3B
                PolicyState.C -> INCOME_TAX_2C_3C
                PolicyState.Unknown -> throw TaxException("getIncomeTax labor market C, unknown state")
            }

            PolicyState.Unknown -> throw TaxException("getIncomeTax labor market unknown state")
        }

    override fun getPoliciesData(): List<PolicyData> = listOf(
        PolicyData(
            1,
            "Fiscal Policy",
            PolicyType.FiscalPolicy,
            PolicyState.A
        ),
        PolicyData(
            2,
            "Labor Market",
            PolicyType.LaborMarket,
            PolicyState.A
        ),
        PolicyData(
            3,
            "Taxation",
            PolicyType.Taxation,
            PolicyState.A
        ),
        PolicyData(
            4,
            "Welfare State: Healthcare & benefits",
            PolicyType.WEHealthcare,
            PolicyState.A
        ),
        PolicyData(
            5,
            "Welfare State: Education",
            PolicyType.WEEducation,
            PolicyState.A
        ),
        PolicyData(
            6,
            "Foreign Trade",
            PolicyType.ForeignTrade,
            PolicyState.A
        ),
        PolicyData(
            7,
            "Immigration",
            PolicyType.Immigration,
            PolicyState.A
        )

    )
}