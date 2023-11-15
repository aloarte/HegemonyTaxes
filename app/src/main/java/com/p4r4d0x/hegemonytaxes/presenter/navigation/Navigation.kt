package com.p4r4d0x.hegemonytaxes.presenter.navigation

import android.content.SharedPreferences
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.navigation.compose.HegemonyTopAppBar
import com.p4r4d0x.hegemonytaxes.presenter.policies.PoliciesScreen
import com.p4r4d0x.hegemonytaxes.presenter.roles.CapitalistClassScreen
import com.p4r4d0x.hegemonytaxes.presenter.roles.MiddleClassScreen
import com.p4r4d0x.hegemonytaxes.presenter.roles.RolesScreen
import com.p4r4d0x.hegemonytaxes.presenter.roles.StateClassScreen
import com.p4r4d0x.hegemonytaxes.presenter.roles.WorkingClassScreen
import com.p4r4d0x.hegemonytaxes.presenter.ui.utils.UiConstants.PREFERENCE_WELCOME
import com.p4r4d0x.hegemonytaxes.presenter.welcome.WelcomeScreen

@Composable
fun NavigationComponent(
    uiState: UiState,
    preferences: SharedPreferences,
    onEventTriggered: (UiEvent) -> Unit
) {
    val navController = rememberNavController()
    //The the events that require any screen navigation are handled here
    val onInnerEventTriggered: (UiEvent) -> Unit =
        { manageNavigationEvent(it, navController, onEventTriggered) }
    HegemonyTopAppBar(
        uiState = uiState,
        onBackPressed = {
            onEventTriggered.invoke(UiEvent.UpdateTitleVisibility(true))
            navController.navigate(Screen.PoliciesScreen.route)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = getStartDestination(preferences)
        ) {
            composable(route = Screen.WelcomeScreen.route) {
                WelcomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    preferences = preferences,
                    onEventTriggered = onInnerEventTriggered
                )
            }
            composable(route = Screen.PoliciesScreen.route) {
                PoliciesScreen(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }
            composable(route = Screen.RoleSelectorScreen.route) {
                RolesScreen(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }
            composable(route = Screen.WorkingClassScreen.route) {
                WorkingClassScreen(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }

            composable(route = Screen.MiddleClassScreen.route) {
                MiddleClassScreen(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }

            composable(route = Screen.CapitalistClassScreen.route) {
                CapitalistClassScreen(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }
            composable(route = Screen.StateScreen.route) {
                StateClassScreen(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }
        }
    }
}

fun getStartDestination(preferences: SharedPreferences) =
    if (preferences.getBoolean(PREFERENCE_WELCOME, false)) {
        Screen.PoliciesScreen.route
    } else {
        Screen.WelcomeScreen.route
    }

fun manageNavigationEvent(
    event: UiEvent,
    navController: NavHostController,
    onEventTriggered: (UiEvent) -> Unit
) {
    if (event is UiEvent.GoScreen) {
        onEventTriggered.invoke(UiEvent.UpdateTitleVisibility(event.displayTitle))
    }

    when (event) {
        UiEvent.GoWelcome -> navController.navigate(Screen.WelcomeScreen.route)
        UiEvent.GoPolicySelector -> navController.navigate(Screen.PoliciesScreen.route)
        UiEvent.GoPickRole -> navController.navigate(Screen.RoleSelectorScreen.route)
        is UiEvent.GoRole -> {
            onEventTriggered(UiEvent.ClearTaxes)
            when (event.role) {
                HegemonyRole.WorkingClass -> navController.navigate(Screen.WorkingClassScreen.route)
                HegemonyRole.MiddleClass -> navController.navigate(Screen.MiddleClassScreen.route)
                HegemonyRole.CapitalistClass -> navController.navigate(Screen.CapitalistClassScreen.route)
                HegemonyRole.State -> navController.navigate(Screen.StateScreen.route)
            }
        }

        else -> onEventTriggered.invoke(event)
    }


}
