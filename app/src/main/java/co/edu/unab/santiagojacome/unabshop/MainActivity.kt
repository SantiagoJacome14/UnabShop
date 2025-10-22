package co.edu.unab.santiagojacome.unabshop

import  android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import co.edu.unab.santiagojacome.unabshop.ui.theme.LoginScreen
import co.edu.unab.santiagojacome.unabshop.ui.theme.NavigationApp
import co.edu.unab.santiagojacome.unabshop.ui.theme.RegisterScreen
import co.edu.unab.santiagojacome.unabshop.ui.theme.UnabShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            UnabShopTheme {
                NavigationApp()
            }


        }
    }
}


