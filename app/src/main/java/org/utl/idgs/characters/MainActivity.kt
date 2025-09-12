package org.utl.idgs.characters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.utl.idgs.characters.ui.theme.CharactersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CharactersTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "fecha") {
                    composable("fecha") {
                        FechaNacimientoScreen(
                            onDiscoverClick = { month ->
                                navController.navigate("loading/$month")
                            }
                        )
                    }
                    composable(
                        route = "loading/{month}",
                        arguments = listOf(navArgument("month") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val month = backStackEntry.arguments?.getInt("month") ?: 1
                        LoadingScreen(onFinished = { m ->
                            navController.navigate("result/$m") {
                                popUpTo("fecha") { inclusive = true }
                            }
                        }, month = month)
                    }
                    composable(
                        route = "result/{month}",
                        arguments = listOf(navArgument("month") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val month = backStackEntry.arguments?.getInt("month") ?: 1
                        ResultScreen(month)
                    }
                }
            }
        }
    }
}
