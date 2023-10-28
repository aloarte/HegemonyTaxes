package com.p4r4d0x.hegemonytaxes.presenter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.p4r4d0x.hegemonytaxes.presenter.UiEvent
import com.p4r4d0x.hegemonytaxes.presenter.UiState
import com.p4r4d0x.hegemonytaxes.presenter.policies.PoliciesScreen

@Composable
fun NavigationComponent(state: UiState, onEventTriggered: (UiEvent) -> Unit) {
    val navController = rememberNavController()
    val onInnerEventTriggered: (UiEvent) -> Unit = { event ->
        when (event) {
            UiEvent.GoWelcome -> navController.navigate(Screen.WelcomeScreen.route)
            UiEvent.GoPolicySelector -> navController.navigate(Screen.PoliciesScreen.route)
            UiEvent.GoPickRole ->  navController.navigate(Screen.RoleSelectorScreen.route)
            //            is UiEvent.GoRole -> navController.navigate(
//                route = Screen.RoleScreen.withArgs(event.price.toString())
//            )

            else -> onEventTriggered.invoke(event)
        }

    }
    NavHost(navController = navController, startDestination = Screen.PoliciesScreen.route) {
//        composable(route = Screen.WelcomeScreen.route) {
//            Screen.WelcomeScreen(
//                state = state,
//                onEventTriggered = onInnerEventTriggered
//            )
//        }
//        composable(
//            route = Screen.DetailScreen.route + "/{productId}",
//            arguments = listOf(
//                navArgument("productId") {
//                    type = NavType.StringType
//                    defaultValue = "UNKNOWN"
//                    nullable = true
//                })
//        ) { entry ->
//            DetailScreen(
//                productId = entry.arguments?.getString("productId"),
//                state = state,
//                onEventTriggered = onInnerEventTriggered
//            )
//
//        }
        composable(route = Screen.PoliciesScreen.route) {
            PoliciesScreen(
                state = state,
                onEventTriggered = onInnerEventTriggered
            )

        }
//        composable(
//            route = Screen.PaymentScreen.route + "/{price}",
//            arguments = listOf(
//                navArgument("price") {
//                    type = NavType.StringType
//                    defaultValue = "0.0"
//                })
//        ) { entry ->
//            PaymentScreen(
//                price = entry.arguments?.getString("price")?.toDouble(),
//                state = state,
//                onEventTriggered = onInnerEventTriggered
//            )
//
//        }
//        composable(route = Screen.ResultScreen.route) {
//            ResultScreen(state = state,
//                onEventTriggered = onInnerEventTriggered)
//        }
    }
}