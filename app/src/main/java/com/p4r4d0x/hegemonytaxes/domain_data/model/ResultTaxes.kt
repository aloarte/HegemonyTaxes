package com.p4r4d0x.hegemonytaxes.domain_data.model

sealed interface ResultTaxes

object TaxesNotCalculated : ResultTaxes
data class WorkingClassTaxes(
    val incomeTaxResult: Int
) : ResultTaxes

data class MiddleClassTaxes(
    val incomeTaxResult: Int,
    val employmentTaxResult: Int,
    val totalTaxes: Int
) : ResultTaxes

data class CapitalistClassTaxes(
    val employmentTaxResult: Int,
    val corporateTaxResult: Int,
    val totalTaxes: Int
) : ResultTaxes

data class StateClassTaxes(
    val wcTaxes: Int,
    val mcTaxes: Int,
    val ccTaxes: Int,
    val totalTaxes: Int
) : ResultTaxes



