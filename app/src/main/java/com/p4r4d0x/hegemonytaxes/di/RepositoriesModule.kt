package com.p4r4d0x.hegemonytaxes.di

import com.p4r4d0x.hegemonytaxes.domain_data.repository.PoliciesRepository
import com.p4r4d0x.hegemonytaxes.domain_data.repository.TaxRepository
import com.p4r4d0x.hegemonytaxes.domain_data.repository.impl.PoliciesRepositoryImpl
import com.p4r4d0x.hegemonytaxes.domain_data.repository.impl.TaxRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindPoliciesRepository(impl: PoliciesRepositoryImpl): PoliciesRepository

    @Binds
    abstract fun bindTaxRepository(impl: TaxRepositoryImpl): TaxRepository
}