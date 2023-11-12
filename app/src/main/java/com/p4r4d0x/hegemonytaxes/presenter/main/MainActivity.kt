package com.p4r4d0x.hegemonytaxes.presenter.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.navigation.NavigationComponent
import com.p4r4d0x.hegemonytaxes.presenter.ui.theme.HegemonyTaxesCalculatorTheme
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.UiConstants.HEGEMONY_PREFERENCES_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state = viewModel.state.collectAsState().value

            //The rest of the events that doesn't require any screen navigation are handled here
            val eventListener: (UiEvent) -> Unit = { event ->
                when (event) {
                    is UiEvent.UpdatePolicy -> {
                        viewModel.updatePolicy(updatedPolicy = event.policy)
                    }

                    is UiEvent.CalculateTaxes -> {
                        viewModel.updateSelection(event.roleData)
                        viewModel.calculateTaxesResult(event.roleData)
                    }

                    is UiEvent.ClearTaxes -> {
                        viewModel.clearTaxesResult()
                    }

                    is UiEvent.UpdateTitleVisibility -> {
                        viewModel.updateTitleVisibility(event.show)
                    }

                    else -> {}
                }
            }

            HegemonyTaxesCalculatorTheme {
                NavigationComponent(
                    uiState = state,
                    preferences = getSharedPreferences(
                        HEGEMONY_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                    ),
                    onEventTriggered = eventListener
                )
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        //Avoid the on back pressed to force use the appbar button
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchPolicies()
    }
}
