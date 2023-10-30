package com.p4r4d0x.hegemonytaxes.di

import com.p4r4d0x.hegemonytaxes.domain_data.components.TaxCalculator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ComponentsModule {
    @Singleton
    @Provides
    fun providesTaxCalculator() : TaxCalculator = TaxCalculator()
}