package com.p4r4d0x.hegemonytaxes.presenter.navigation

import android.content.SharedPreferences
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole.CapitalistClass
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole.MiddleClass
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole.State
import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole.WorkingClass
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent.ClearTaxes
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent.GoPickRole
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent.GoPolicySelector
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent.GoRole
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent.GoScreen
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent.GoWelcome
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent.UpdateTitleVisibility
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.navigation.Screen.CapitalistClassScreen
import com.p4r4d0x.hegemonytaxes.presenter.navigation.Screen.MiddleClassScreen
import com.p4r4d0x.hegemonytaxes.presenter.navigation.Screen.PoliciesScreen
import com.p4r4d0x.hegemonytaxes.presenter.navigation.Screen.RoleSelectorScreen
import com.p4r4d0x.hegemonytaxes.presenter.navigation.Screen.StateScreen
import com.p4r4d0x.hegemonytaxes.presenter.navigation.Screen.WelcomeScreen
import com.p4r4d0x.hegemonytaxes.presenter.navigation.Screen.WorkingClassScreen
import com.p4r4d0x.hegemonytaxes.presenter.navigation.compose.HegemonyTopAppBar
import com.p4r4d0x.hegemonytaxes.presenter.policies.PoliciesScreenScrollable
import com.p4r4d0x.hegemonytaxes.presenter.roles.CapitalistClassScreenScrollable
import com.p4r4d0x.hegemonytaxes.presenter.roles.MiddleClassScreenScrollable
import com.p4r4d0x.hegemonytaxes.presenter.roles.RolesScreenScrollable
import com.p4r4d0x.hegemonytaxes.presenter.roles.StateClassScreenScrollable
import com.p4r4d0x.hegemonytaxes.presenter.roles.WorkingClassScreenScrollable
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val handleBackAction: () -> Unit = {
        when (currentRoute) {
            PoliciesScreen.route -> {
                // Do nothing
            }

            RoleSelectorScreen.route -> {
                onEventTriggered.invoke(UpdateTitleVisibility(true))
                navController.navigate(PoliciesScreen.route)
            }

            WorkingClassScreen.route,
            MiddleClassScreen.route,
            CapitalistClassScreen.route,
            StateScreen.route -> {
                onEventTriggered.invoke(UpdateTitleVisibility(false))
                navController.popBackStack()
            }

            else -> {
                navController.popBackStack()
            }
        }
    }

    HegemonyTopAppBar(
        uiState = uiState,
        onBackPressed = handleBackAction
    ) { paddingValues ->
        BackHandler {
            handleBackAction()
        }
        NavHost(
            navController = navController,
            startDestination = getStartDestination(preferences),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            composable(route = WelcomeScreen.route) {
                WelcomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    preferences = preferences,
                    onEventTriggered = onInnerEventTriggered
                )
            }
            composable(route = PoliciesScreen.route) {
                PoliciesScreenScrollable(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }
            composable(route = RoleSelectorScreen.route) {
                RolesScreenScrollable(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }
            composable(route = WorkingClassScreen.route) {
                WorkingClassScreenScrollable(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }

            composable(route = MiddleClassScreen.route) {
                MiddleClassScreenScrollable(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }

            composable(route = CapitalistClassScreen.route) {
                CapitalistClassScreenScrollable(
                    modifier = Modifier.padding(paddingValues),
                    uiState = uiState,
                    onEventTriggered = onInnerEventTriggered
                )
            }
            composable(route = StateScreen.route) {
                StateClassScreenScrollable(
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
        PoliciesScreen.route
    } else {
        WelcomeScreen.route
    }

fun manageNavigationEvent(
    event: UiEvent,
    navController: NavHostController,
    onEventTriggered: (UiEvent) -> Unit
) {
    if (event is GoScreen) {
        onEventTriggered.invoke(UpdateTitleVisibility(event.displayTitle))
    }

    when (event) {
        GoWelcome -> navController.navigate(WelcomeScreen.route)
        GoPolicySelector -> navController.navigate(PoliciesScreen.route)
        GoPickRole -> navController.navigate(RoleSelectorScreen.route)
        is GoRole -> {
            onEventTriggered(ClearTaxes)
            when (event.role) {
                WorkingClass -> navController.navigate(WorkingClassScreen.route)
                MiddleClass -> navController.navigate(MiddleClassScreen.route)
                CapitalistClass -> navController.navigate(CapitalistClassScreen.route)
                State -> navController.navigate(StateScreen.route)
            }
        }

        else -> onEventTriggered.invoke(event)
    }


}
