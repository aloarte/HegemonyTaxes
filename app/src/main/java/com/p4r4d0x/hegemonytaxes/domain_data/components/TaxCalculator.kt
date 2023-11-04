package com.p4r4d0x.hegemonytaxes.domain_data.components

import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException

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

    fun calculateTaxIncome(incomeTax: Int, population: Int): Int {
        if (incomeTax < 1 || population < 1) {
            throw TaxException("Wrong values for the calculateTaxIncome operation. Values: $incomeTax, $population")
        } else {
            return incomeTax * population
        }
    }
}