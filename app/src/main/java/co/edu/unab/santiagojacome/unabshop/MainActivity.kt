package co.edu.unab.santiagojacome.unabshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import co.edu.unab.santiagojacome.unabshop.ui.theme.UnabShopTheme
import co.edu.unab.santiagojacome.unabshop.ui.theme.NavigationApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnabShopTheme {
                Surface(
                    modifier = Modifier,
                    color = Color(0xFFF5F5F5)
                ) {
                    NavigationApp()
                }
            }
        }
    }
}