package co.edu.unab.santiagojacome.unabshop.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.unab.santiagojacome.unabshop.Producto
import co.edu.unab.santiagojacome.unabshop.ProductoRepository
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAddProduct: () -> Unit = {}
) {
    val repo = ProductoRepository()
    var productos by remember { mutableStateOf<List<Producto>>(emptyList()) }


    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var precio by remember { mutableStateOf(TextFieldValue("")) }
    var mensaje by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        repo.obtenerProductos { productos = it }
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        "Unab Shop",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                },
                actions = {
                    IconButton(onClick = { /* Notificaciones */ }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones")
                    }
                    IconButton(onClick = { /* Carrito */ }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito")
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddProduct,
                containerColor = Color(0xFFFF9900)
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Agregar", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )

            if (mensaje.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(mensaje, color = Color(0xFFFF9900))
            }

            Spacer(Modifier.height(16.dp))

            // Botón para agregar producto
            Button(
                onClick = {
                    if (nombre.text.isNotBlank() && precio.text.isNotBlank()) {
                        val nuevo = Producto(
                            nombre = nombre.text,
                            descripcion = descripcion.text,
                            precio = precio.text.toDoubleOrNull() ?: 0.0
                        )
                        repo.agregarProducto(nuevo) { ok ->
                            if (ok) {
                                repo.obtenerProductos { productos = it }
                                nombre = TextFieldValue("")
                                descripcion = TextFieldValue("")
                                precio = TextFieldValue("")
                                mensaje = "Producto agregado correctamente"
                            } else {
                                mensaje = "Error al agregar producto"
                            }
                        }
                    } else {
                        mensaje = "Por favor complete los campos"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9900))
            ) {
                Text("Agregar Producto", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productos) { producto ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("$${producto.precio}", color = Color.Gray)
                            }
                            IconButton(onClick = {
                                producto.id?.let { id ->
                                    repo.eliminarProducto(id) { ok ->
                                        if (ok) repo.obtenerProductos { productos = it }
                                    }
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}
