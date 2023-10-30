package com.p4r4d0x.hegemonytaxes.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.repository.PoliciesRepository
import com.p4r4d0x.hegemonytaxes.domain_data.repository.TaxRepository
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taxRepository: TaxRepository,
    private val policiesRepository: PoliciesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    fun fetchPolicies() {
        _state.update {
            it.copy(policies = policiesRepository.fetchPolicies())
        }
        calculateTaxMultiplier()
    }

    fun calculateTaxMultiplier() {
        _state.update {
            it.copy(taxMultiplier = taxRepository.calculateTaxMultiplier(it.policies))
        }
    }

    fun updatePolicy(updatedPolicy: PolicyData) {
        _state.value.policies.find { it.type == updatedPolicy.type }?.let { listPolicy ->
            _state.update { uiState ->
                val index = uiState.policies.indexOf(listPolicy)
                uiState.copy(
                    policies = uiState.policies.toMutableList().also { it[index] = updatedPolicy })
            }
        }
        calculateTaxMultiplier()

    }

}