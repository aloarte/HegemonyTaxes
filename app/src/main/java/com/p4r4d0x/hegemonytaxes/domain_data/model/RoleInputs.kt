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
    val ownCompanies: Int,
    val profit: Int
) : RoleInputs
