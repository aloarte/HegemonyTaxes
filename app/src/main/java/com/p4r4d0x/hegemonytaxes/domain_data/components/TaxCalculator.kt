package com.p4r4d0x.hegemonytaxes.domain_data.components

class TaxCalculator {

    fun calculateTaxMultiplier(
        baseTax: Int,
        welfareTaxMultiplier: Int,
        healthcareTaxIncrement: Int,
        educationTaxIncrement: Int
    ) = baseTax +
            (welfareTaxMultiplier * healthcareTaxIncrement) +
            (welfareTaxMultiplier * educationTaxIncrement)
}