package com.example.myapplication.views

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.R
import com.example.myapplication.ui.theme.VerdeMartini
import com.example.myapplication.ui.theme.DoradoMartini

// Estructuras de datos para poblar nuestra vista... // DB
data class Producto(val nombre: String, val descripcion: String)
data class RecetaVideo(val titulo: String, val descripcion: String, val videoIdYoutube: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var query by remember { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current

    // Estado para controlar la apertura del carrusel del catálogo // DB
    var mostrarCatalogoCompleto by remember { mutableStateOf(false) }

    // Base de datos local extraída del catálogo oficial Retail de Master Martini... // DB
    val catalogo = listOf(
        Producto("OraSí Bebidas Vegetales", "Línea tradicional (Avena, Coco, Almendra, Avellana, Arroz, Soya). Fortificadas en calcio y vitaminas."),
        Producto("OraSí Barista y Orgánica", "Línea para espumar en casa/uso profesional y línea 100% orgánica sin azúcar añadida."),
        Producto("Cremas Repostería OraSí y Bravo", "Cremas vegetales para batir, formatos Edge, Chica y Tetra."),
        Producto("Cremas Decor y Monna Lisa", "Decor Up, Decor Art, Monna Lisa y Monna Lisa Pro para decoraciones de alto rendimiento."),
        Producto("Cremas Cocina", "Master Gourmet y C Cuisine Leve para preparaciones saladas."),
        Producto("Mezclas Instantáneas Delice", "Crema Pastelera, Chantilly, Merengue, Azúcar Glass, P. Horneo, Azúcar Morena y Coco Rallado."),
        Producto("Salsas y Baños", "Calda D R3 Leches y Golden C Choc Líquido."),
        Producto("Caravella (Discos y Chips)", "Sucedáneos Dark, Alfajor, Leche, Blanco, Chips y formatos Mini para fundir."),
        Producto("Carnevale (Color)", "Chocolates de color: Berry, Lemon, Orange, Pistacchio y Blue para coberturas."),
        Producto("Caravella Deco (Escamas)", "Formato escamas para decoraciones profesionales: Dark, Leche, Blanco y Avellana."),
        Producto("Chocolate Puro Ariba", "Ariba Dark 72%, Dark 57%, Leche 32%, Blanco 31% y Cacao en Polvo."),
        Producto("Cereales Avena Mix Sole", "Avena tradicional e instantánea."),
        Producto("Proteína Vegana Vegan Up!", "Proteína de soya texturizada."),
        Producto("Margarinas Gran Master", "Margarina Horneo y Margarina Hoja de origen vegetal."),
        Producto("Línea Vita Bake", "Margarina y Manteca vegetal de alto rendimiento."),
        Producto("Levadura Bravo", "Levadura para uso en panadería en distintos formatos."),
        Producto("Aceites Vegetales", "Top Frit Maravilla, Premium Frit, Vitafrit 40 y Oliva Vita para frituras de alta calidad.")
    )

    // Videos de demostración oficiales in-app... // DB
    val videos = listOf(
        RecetaVideo("Gallina - Maurizio Frau", "Tutorial para hacer repostería con forma de gallina.", "1pTyQObs0DE"),
        RecetaVideo("Happy Valentine - Lorenzo Puca", "Tutorial para hacer un pastel con forma de corazón.", "t0RKDz0TvSc"),
        RecetaVideo("Winter Temptation - Valerio Barralis", "Tutorial para hacer un pastel invernal.", "Wjja1iEHAks"),
        RecetaVideo("Bossolà - Rita Busalacchi", "Tutorial para un pastel glaseado.", "r8IEDXSwdvQ")
    )

    // Filtro reactivo de búsqueda... // DB
    val catalogoFiltrado = catalogo.filter {
        it.nombre.contains(query, ignoreCase = true) || it.descripcion.contains(query, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopAppBar(
            title = {
                // Inserción del logo en la esquina superior izquierda reemplazando el texto... // DB
                Image(
                    painter = painterResource(id = R.drawable.logo_master_martini),
                    contentDescription = "Logo Master Martini",
                    modifier = Modifier.height(40.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeMartini),
            actions = {
                IconButton(onClick = { uriHandler.openUri("https://www.facebook.com/mastermartinichile") }) {
                    Icon(Icons.Default.ThumbUp, contentDescription = "Facebook", tint = MaterialTheme.colorScheme.onPrimary)
                }
                IconButton(onClick = { uriHandler.openUri("https://www.instagram.com/mastermartinichile/") }) {
                    Icon(Icons.Default.Share, contentDescription = "Instagram", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        )

        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar productos (ej. Ariba, Delice...)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de texto interactivo para abrir el visor del catálogo completo... // DB
            Text(
                text = "Catálogo de Productos",
                color = VerdeMartini,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { mostrarCatalogoCompleto = true }
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {

                // Renderizado dinámico de tarjetas de productos... // DB
                items(catalogoFiltrado) { producto ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(producto.nombre, style = MaterialTheme.typography.titleMedium, color = VerdeMartini, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(producto.descripcion, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Recetas en Video", color = VerdeMartini, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Renderizado de reproductores in-app... // DB
                items(videos) { video ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column {
                            Text(
                                text = video.titulo,
                                modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp),
                                fontWeight = FontWeight.Bold,
                                color = VerdeMartini
                            )
                            Text(
                                text = video.descripcion,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ReproductorYouTubeLocal(videoId = video.videoIdYoutube)
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    // Modal in-app que implementa un Carrusel (LazyRow) para visualizar el PDF convertido a imágenes // DB
    if (mostrarCatalogoCompleto) {
        Dialog(
            onDismissRequest = { mostrarCatalogoCompleto = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    LazyRow(modifier = Modifier.fillMaxSize()) {
                        item {
                            Image(
                                painter = painterResource(id = R.drawable.catalogo_pag1),
                                contentDescription = "Página 1",
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                        item {
                            Image(
                                painter = painterResource(id = R.drawable.catalogo_pag2),
                                contentDescription = "Página 2",
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                        item {
                            Image(
                                painter = painterResource(id = R.drawable.catalogo_pag3),
                                contentDescription = "Página 3",
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                        item {
                            Image(
                                painter = painterResource(id = R.drawable.catalogo_pag4),
                                contentDescription = "Página 4",
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                    }

                    // Botón para cerrar la superposición // DB
                    IconButton(
                        onClick = { mostrarCatalogoCompleto = false },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f), shape = MaterialTheme.shapes.small)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MaterialTheme.colorScheme.onSurface)
                    }

                    // Indicador de navegación para guiar la UX // DB
                    Text(
                        text = "Desliza para ver más páginas ➔",
                        color = VerdeMartini,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), shape = MaterialTheme.shapes.small)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

// Reproductor encapsulado que utiliza el entorno móvil de YouTube para saltar bloqueos de inserción // DB
@Composable
fun ReproductorYouTubeLocal(videoId: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false

            webViewClient = android.webkit.WebViewClient()
            webChromeClient = android.webkit.WebChromeClient()

            loadUrl("https://m.youtube.com/watch?v=$videoId")
        }
    }, modifier = Modifier.fillMaxWidth().height(250.dp))
}