package com.p4r4d0x.hegemonytaxes.domain_data.components

import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState

class TaxCalculator {

    fun calculateTaxMultiplier(
        baseTax: Int,
        welfareTaxMultiplier: Int,
        healthcareTaxIncrement: Int,
        educationTaxIncrement: Int
    ): Int {
        if (baseTax < 0 || welfareTaxMultiplier < 0 || healthcareTaxIncrement < 0 || educationTaxIncrement < 0) {
            throw TaxException("Wrong values for the calculateTaxMultiplier operation. Values: $baseTax, $welfareTaxMultiplier, $healthcareTaxIncrement, $educationTaxIncrement")

        } else {
            return baseTax + (welfareTaxMultiplier * healthcareTaxIncrement) +
                    (welfareTaxMultiplier * educationTaxIncrement)
        }
    }

    fun calculateTaxIncomeResult(incomeTax: Int, multiplier: Int): Int {
        if (incomeTax < 1 || multiplier < 1) {
            throw TaxException("Wrong values for the calculateTaxIncome operation. Values: $incomeTax, $multiplier")
        } else {
            return incomeTax * multiplier
        }
    }

    fun calculateEmploymentTaxResult(incomeTax: Int, multiplier: Int): Int {
        if (incomeTax < 1 || multiplier < 1) {
            throw TaxException("Wrong values for the calculateTaxIncome operation. Values: $incomeTax, $multiplier")
        } else {
            return incomeTax * multiplier
        }
    }

    fun calculateCorporateTax(profit: Int, taxationPolicyState: PolicyState): Int {
        return when (taxationPolicyState) {
            PolicyState.A -> when {
                profit in 5..9 -> 1
                profit in 10..24 -> 5
                profit in 25..49 -> 12
                profit in 50..99 -> 24
                profit in 100..199 -> 40
                profit in 200..299 -> 100
                profit >= 300 -> 160
                else -> 0
            }

            PolicyState.B -> when {
                profit in 5..9 -> 2
                profit in 10..24 -> 5
                profit in 25..49 -> 10
                profit in 50..99 -> 15
                profit in 100..199 -> 30
                profit in 200..299 -> 70
                profit >= 300 -> 120
                else -> 0
            }

            PolicyState.C -> when {
                profit in 5..9 -> 2
                profit in 10..24 -> 4
                profit in 25..49 -> 7
                profit in 50..99 -> 10
                profit in 100..199 -> 20
                profit in 200..299 -> 40
                profit >= 300 -> 60
                else -> 0
            }

            PolicyState.Unknown -> 0
        }
    }
}