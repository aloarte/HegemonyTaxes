package com.p4r4d0x.hegemonytaxes.di

import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.datasource.impl.PoliciesDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindPoliciesDatasource(impl: PoliciesDatasourceImpl): PoliciesDatasource
}