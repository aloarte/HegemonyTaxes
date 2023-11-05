package com.p4r4d0x.hegemonytaxes.domain_data.model

sealed interface RoleInputs

class WorkingClassInputs(
    val population: Int
) : RoleInputs

class MiddleClassInputs(
    val externalCompaniesWithWorkers: Int,
    val ownCompanies: Int
) : RoleInputs

class CapitalistClassInputs(
    val companies: Int,
    val profit: Int
) : RoleInputs

class StateClassInputs(
    val wcPopulation: Int,
    val mcExternalCompaniesWithWorkers: Int,
    val mcOwnCompanies: Int,
    val ccCompanies: Int,
    val ccProfit: Int
) : RoleInputs

