package co.edu.unab.santiagojacome.unabshop

import com.google.firebase.firestore.FirebaseFirestore

class ProductoRepository {
    private val db = FirebaseFirestore.getInstance()

    fun agregarProducto(producto: Producto, callback: (Boolean) -> Unit) {
        // guardamos el objeto sin id (Firestore genera id)
        val data = hashMapOf(
            "nombre" to producto.nombre,
            "descripcion" to producto.descripcion,
            "precio" to producto.precio
        )
        db.collection("products")
            .add(data)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun obtenerProductos(callback: (List<Producto>) -> Unit) {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val productos = result.map { doc ->
                    // construir Producto y asignar id del documento
                    Producto(
                        id = doc.id,
                        nombre = doc.getString("nombre") ?: "",
                        descripcion = doc.getString("descripcion") ?: "",
                        precio = doc.getDouble("precio") ?: doc.getLong("precio")?.toDouble() ?: 0.0
                    )
                }
                callback(productos)
            }
            .addOnFailureListener { callback(emptyList()) }
    }

    fun eliminarProducto(id: String, callback: (Boolean) -> Unit) {
        db.collection("products").document(id)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}
