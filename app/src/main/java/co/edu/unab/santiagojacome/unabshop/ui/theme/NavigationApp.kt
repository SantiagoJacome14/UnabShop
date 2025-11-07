package co.edu.unab.santiagojacome.unabshop.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
    ) { _ ->
        NavGraph(navController = navController)
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // 🔸 Pantalla de Login
        composable("login") {
            LoginScreen(
                onClickRegister = {
                    navController.navigate("register")
                },
                onSuccessfulLogin = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // 🔸 Pantalla de Registro
        composable("register") {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onSuccessfulRegister = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // 🔸 Pantalla Principal (Home)
        composable("home") {
            HomeScreen(
                onNavigateToAddProduct = {
                    navController.navigate("addProduct")
                }
            )
        }

        // 🔸 Pantalla para agregar producto
        composable("addProduct") {
            AddProductScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
