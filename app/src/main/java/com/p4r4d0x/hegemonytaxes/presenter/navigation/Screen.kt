package com.p4r4d0x.hegemonytaxes.presenter.navigation

sealed class Screen(val route: String) {
    object WelcomeScreen : Screen("welcome_screen")
    object PoliciesScreen : Screen("policies_screen")
    object RoleSelectorScreen : Screen("role_selector_screen")
    object RoleScreen : Screen("role_screen")

    fun withArgs(vararg args: String) = buildString {
        append(route)
        args.forEach { arg ->
            append("/$arg")

        }
    }
}