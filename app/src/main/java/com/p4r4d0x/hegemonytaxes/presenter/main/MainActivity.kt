package com.p4r4d0x.hegemonytaxes.presenter.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.navigation.NavigationComponent
import com.p4r4d0x.hegemonytaxes.ui.theme.HegemonyTaxesCalculatorTheme
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
                NavigationComponent(state,eventListener)
            }
        }
    }

    override fun onBackPressed() {

    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchPolicies()
    }
}
