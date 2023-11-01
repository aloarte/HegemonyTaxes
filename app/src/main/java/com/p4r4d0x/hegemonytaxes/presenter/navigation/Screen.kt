package com.p4r4d0x.hegemonytaxes.presenter.navigation

sealed class Screen(val route: String) {
    object WelcomeScreen : Screen("welcome_screen")
    object PoliciesScreen : Screen("policies_screen")
    object RoleSelectorScreen : Screen("role_selector_screen")
    object WorkingClassScreen : Screen("working_class_screen")

    object MiddleClassScreen : Screen("middle_class_screen")

    object CapitalistClassScreen : Screen("capitalist_class_screen")

    object StateScreen : Screen("state_screen")

    fun withArgs(vararg args: String) = buildString {
        append(route)
        args.forEach { arg ->
            append("/$arg")

        }
    }
}