package com.p4r4d0x.hegemonytaxes.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.RoleInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.TaxesNotCalculated
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.repository.PoliciesRepository
import com.p4r4d0x.hegemonytaxes.domain_data.repository.TaxRepository
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taxRepository: TaxRepository,
    private val policiesRepository: PoliciesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state
        .onStart { fetchPolicies() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    fun fetchPolicies() {
        _state.update {
            it.copy(policies = policiesRepository.fetchPolicies())
        }
        updateTaxes()
    }

    fun updatePolicy(updatedPolicy: PolicyData) {
        _state.value.policies.find { it.type == updatedPolicy.type }?.let { listPolicy ->
            _state.update { uiState ->
                val index = uiState.policies.indexOf(listPolicy)
                uiState.copy(
                    policies = uiState.policies.toMutableList()
                        .apply { this[index] = updatedPolicy })
            }
        }
        updateTaxes()
    }

    private fun updateTaxes() {
        _state.update {
            it.copy(
                taxMultiplier = taxRepository.getTaxMultiplier(it.policies),
                incomeTax = taxRepository.getIncomeTax(it.policies),
                taxationPolicyState = taxRepository.getTaxationPolicyState(it.policies)
            )
        }
    }

    fun calculateTaxesResult(data: RoleInputs) {
        _state.update {
            it.copy(
                resultTaxes = taxRepository.calculateTaxes(
                    it.taxMultiplier,
                    it.incomeTax,
                    it.taxationPolicyState,
                    data
                )
            )
        }
    }

    fun clearTaxesResult() {
        _state.update {
            it.copy(resultTaxes = TaxesNotCalculated)
        }
    }

    fun updateSelection(roleData: RoleInputs) {
        when (roleData) {
            is CapitalistClassInputs -> _state.update {
                it.copy(ccSelection = roleData)
            }

            is MiddleClassInputs -> _state.update {
                it.copy(mcSelection = roleData)
            }

            is StateClassInputs -> _state.update {
                it.copy(stateSelection = roleData)
            }

            is WorkingClassInputs -> _state.update {
                it.copy(wcSelection = roleData)
            }
        }
    }

    fun updateTitleVisibility(show: Boolean) {
        _state.update {
            it.copy(displayTitleOnAppBar = show)
        }
    }
}