package com.example.myapplication.views

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.R
import com.example.myapplication.ui.theme.VerdeMartini
import com.example.myapplication.ui.theme.DoradoMartini

data class Producto(val nombre: String, val descripcion: String)

data class TallerMaestro(
    val titulo: String,
    val descripcion: String,
    val videoIdYoutube: String,
    val ingredientes: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var query by remember { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current
    var mostrarCatalogoCompleto by remember { mutableStateOf(false) }

    val talleres = listOf(
        TallerMaestro(
            titulo = "Clase Magistral: Volcanes de Chocolate",
            descripcion = "Aprende el secreto para lograr un centro líquido perfecto. Una técnica esencial para sorprender a tus clientes.",
            videoIdYoutube = "fR5taQ21qfg",
            ingredientes = listOf("Chocolate Puro Ariba Dark 72%", "Margarina Gran Master Horneo", "Crema Chantilly Delice")
        ),
        TallerMaestro(
            titulo = "Taller: Conos Hojaldrados y Cremas",
            descripcion = "Domina la técnica de horneado para masas crujientes, ideales para rellenar con cremas ligeras y estables.",
            videoIdYoutube = "oEcD6csEbRY",
            ingredientes = listOf("Margarina de Hojaldre Gran Master", "Master Gourmet (Crema)", "Azúcar Glass Delice")
        ),
        TallerMaestro(
            titulo = "Taller: Coberturas y Color",
            descripcion = "Técnicas modernas para derretir sucedáneos y aplicar colores vibrantes que no pierden su brillo.",
            videoIdYoutube = "gzrN_dv78Ws",
            ingredientes = listOf("Chocolates Sucedáneos Caravella", "Carnevale Berry y Lemon", "Decor Up (Crema)")
        ),
        TallerMaestro(
            titulo = "Taller: Masas Dulces y Batidos",
            descripcion = "Preparación de batidos aireados y masas dulces utilizando alternativas vegetales de alta calidad.",
            videoIdYoutube = "q9Rqg8V9PcQ",
            ingredientes = listOf("Mezclas Instantáneas Delice", "OraSí Bebidas Vegetales", "Levadura Bravo")
        )
    )

    val catalogo = listOf(
        Producto("OraSí Bebidas Vegetales", "Línea tradicional y Barista. Fortificadas en calcio y vitaminas."),
        Producto("Cremas Repostería OraSí y Bravo", "Cremas vegetales para batir, formatos Edge, Chica y Tetra."),
        Producto("Cremas Decor y Monna Lisa", "Decor Up, Decor Art, Monna Lisa para decoraciones de alto rendimiento."),
        Producto("Mezclas Instantáneas Delice", "Crema Pastelera, Chantilly, Merengue, Azúcar Glass, P. Horneo."),
        Producto("Caravella (Discos y Chips)", "Sucedáneos Dark, Alfajor, Leche, Blanco y formatos Mini."),
        Producto("Chocolate Puro Ariba", "Ariba Dark 72%, Dark 57%, Leche 32%, Blanco 31% y Cacao en Polvo."),
        Producto("Margarinas Gran Master", "Margarina Horneo y Margarina Hoja de origen vegetal.")
    )

    val catalogoFiltrado = catalogo.filter {
        it.nombre.contains(query, ignoreCase = true) || it.descripcion.contains(query, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Image(
                    painter = painterResource(id = R.drawable.logo_master_martini),
                    contentDescription = "Logo Master Martini",
                    modifier = Modifier.height(40.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeMartini),
            actions = {
                IconButton(onClick = { uriHandler.openUri("https://www.instagram.com/mastermartinichile/") }) {
                    Icon(Icons.Default.Share, contentDescription = "Instagram", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        )

        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar herramientas o productos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {

                item {
                    Text("Clases Maestras", color = VerdeMartini, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text("Aprende nuevas técnicas aplicando nuestros productos", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(talleres) { taller ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth().background(VerdeMartini).padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = DoradoMartini, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = taller.titulo,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }

                            ReproductorYouTubeLocal(videoId = taller.videoIdYoutube)

                            Text(
                                text = taller.descripcion,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray
                            )

                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.LightGray)

                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Ingredientes recomendados:", fontWeight = FontWeight.Bold, color = VerdeMartini, modifier = Modifier.padding(bottom = 8.dp))
                                taller.ingredientes.forEach { ingrediente ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Check", tint = DoradoMartini, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = ingrediente, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Catálogo de Productos",
                        color = VerdeMartini,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { mostrarCatalogoCompleto = true }
                            .padding(vertical = 8.dp)
                    )
                    Text("Haz clic en el título para ver el folleto visual", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(catalogoFiltrado) { producto ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(producto.nombre, style = MaterialTheme.typography.titleMedium, color = VerdeMartini, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(producto.descripcion, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }

    if (mostrarCatalogoCompleto) {
        Dialog(
            onDismissRequest = { mostrarCatalogoCompleto = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyRow(modifier = Modifier.fillMaxSize()) {
                        item { Image(painterResource(R.drawable.catalogo_pag1), "Página 1", Modifier.fillParentMaxSize()) }
                        item { Image(painterResource(R.drawable.catalogo_pag2), "Página 2", Modifier.fillParentMaxSize()) }
                        item { Image(painterResource(R.drawable.catalogo_pag3), "Página 3", Modifier.fillParentMaxSize()) }
                        item { Image(painterResource(R.drawable.catalogo_pag4), "Página 4", Modifier.fillParentMaxSize()) }
                    }
                    IconButton(
                        onClick = { mostrarCatalogoCompleto = false },
                        modifier = Modifier.align(Alignment.TopEnd).padding(16.dp).background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f), shape = MaterialTheme.shapes.small)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = MaterialTheme.colorScheme.onSurface)
                    }
                    Text(
                        text = "Desliza para ver más páginas ➔",
                        color = VerdeMartini, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp).background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), shape = MaterialTheme.shapes.small).padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReproductorYouTubeLocal(videoId: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false

            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()

            val html = """
                <!DOCTYPE html>
                <html>
                <body style="margin:0;padding:0;background:#000;display:flex;justify-content:center;align-items:center;height:100vh;">
                    <iframe width="100%" height="100%" 
                            src="https://www.youtube.com/embed/$videoId?playsinline=1&modestbranding=1&rel=0" 
                            frameborder="0" 
                            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                            allowfullscreen>
                    </iframe>
                </body>
                </html>
            """.trimIndent()

            loadDataWithBaseURL("http://localhost/", html, "text/html", "utf-8", null)
        }
    }, modifier = Modifier.fillMaxWidth().height(350.dp))
}