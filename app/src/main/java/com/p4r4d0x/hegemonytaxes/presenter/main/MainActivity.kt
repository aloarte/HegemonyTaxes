package com.p4r4d0x.hegemonytaxes.presenter.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.main.MainAction.CalculateTaxes
import com.p4r4d0x.hegemonytaxes.presenter.main.MainAction.ClearTaxes
import com.p4r4d0x.hegemonytaxes.presenter.main.MainAction.ResetPolicies
import com.p4r4d0x.hegemonytaxes.presenter.main.MainAction.UpdatePolicy
import com.p4r4d0x.hegemonytaxes.presenter.main.MainAction.UpdateTitleVisibility
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
                    is UiEvent.UpdatePolicy -> viewModel.onAction(UpdatePolicy(event.policy))
                    is UiEvent.CalculateTaxes -> viewModel.onAction(CalculateTaxes(event.roleData))
                    is UiEvent.ClearTaxes -> viewModel.onAction(ClearTaxes)
                    is UiEvent.UpdateTitleVisibility -> viewModel.onAction(
                        UpdateTitleVisibility(event.show)
                    )
                    is UiEvent.ResetPolicies -> viewModel.onAction(ResetPolicies)
                    else -> {}
                }
            }

            HegemonyTaxesCalculatorTheme {
                NavigationComponent(
                    uiState = state,
                    preferences = getSharedPreferences(
                        HEGEMONY_PREFERENCES_NAME,
                        MODE_PRIVATE
                    ),
                    onEventTriggered = eventListener
                )
            }
        }
    }
}
