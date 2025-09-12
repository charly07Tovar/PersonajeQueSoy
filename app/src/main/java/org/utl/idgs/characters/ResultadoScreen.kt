package org.utl.idgs.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

data class SuperHero(
    val nombre: String,
    val mes: String,
    val descripcion: String,
    val iconRes: Int,
    val fondoRes: Int,
    val fondoVideoRes: Int? = null, // Para videos de fondo
    val colorPrimario: Color = Color(0xFF1976D2),
    val colorSecundario: Color = Color(0xFF42A5F5),
    val descripcionDetallada: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(month: Int, navController: NavController) {
    // Configuración responsive
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp
    val screenHeight = configuration.screenHeightDp.dp

    // Datos mejorados de los superhéroes por mes
    val heroes = listOf(
        SuperHero(
            "Iron Man",
            "Enero",
            "Innovador, valiente y siempre al frente de la batalla.",
            R.drawable.ironman_icon,
            R.drawable.fondo_super,
            colorPrimario = Color(0xFFD32F2F),
            colorSecundario = Color(0xFFFFC107),
            descripcionDetallada = "Tony Stark es un genio, multimillonario, playboy y filántropo. Como Iron Man, utiliza su intelecto superior y recursos para crear tecnología avanzada que lo convierte en uno de los vengadores más poderosos."
        ),
        SuperHero(
            "Batman",
            "Febrero",
            "Astuto y estratega, lucha en las sombras por la justicia.",
            R.drawable.batman_icon,
            R.drawable.fondo_super_gris,
            colorPrimario = Color(0xFF212121),
            colorSecundario = Color(0xFF616161),
            descripcionDetallada = "Bruce Wayne es el Caballero Oscuro de Gotham. Sin superpoderes, confía en su intelecto, habilidades de detective, tecnología y artes marciales para combatir el crimen y proteger a los inocentes."
        ),
        SuperHero(
            "Spider-Man",
            "Marzo",
            "Ágil y responsable, siempre protege a su ciudad.",
            R.drawable.spiderman_icon,
            R.drawable.fondo_super,
            colorPrimario = Color(0xFFD32F2F),
            colorSecundario = Color(0xFF1976D2),
            descripcionDetallada = "Peter Parker aprendió que 'con gran poder viene gran responsabilidad'. Sus habilidades arácnidas, sentido arácnido y web-shooters lo convierten en el amigable vecino Spider-Man de Nueva York."
        ),
        SuperHero(
            "Wonder Woman",
            "Abril",
            "Valiente y justa, inspira a todos con su fuerza.",
            R.drawable.wonder_woman_icon,
            R.drawable.fondo_super,
            colorPrimario = Color(0xFFD32F2F),
            colorSecundario = Color(0xFFFFC107),
            descripcionDetallada = "Diana Prince es una amazona de Themyscira, dotada de poderes divinos. Con su lazo de la verdad, brazaletes y tiara, lucha por la justicia, la paz y la igualdad en el mundo de los mortales."
        ),
        SuperHero(
            "Thor",
            "Mayo",
            "Fuerte y noble, siempre defiende a los suyos.",
            R.drawable.thor_icon,
            R.drawable.fondo_super,
            colorPrimario = Color(0xFF1976D2),
            colorSecundario = Color(0xFFFFC107),
            descripcionDetallada = "El Dios del Trueno de Asgard empuña el martillo encantado Mjolnir. Con su fuerza divina y control sobre los rayos, Thor protege tanto Asgard como Midgard de las amenazas cósmicas."
        ),
        SuperHero(
            "Black Panther",
            "Junio",
            "Líder sabio y protector de su reino.",
            R.drawable.black_panter_icon,
            R.drawable.fondo_super_gris,
            colorPrimario = Color(0xFF424242),
            colorSecundario = Color(0xFF9C27B0),
            descripcionDetallada = "T'Challa es el rey de Wakanda y portador del título de Black Panther. Con el vibranium y la tecnología avanzada de su nación, protege tanto Wakanda como el mundo entero."
        ),
        SuperHero(
            "Mr. Terrific",
            "Julio",
            "Genio científico y estratega, con habilidades atléticas excepcionales.",
            R.drawable.mr_terrific_icon,
            R.drawable.fondo_super_gris,
            colorPrimario = Color(0xFF1976D2),
            colorSecundario = Color(0xFF4CAF50),
            descripcionDetallada = "Michael Holt es considerado el tercer hombre más inteligente del mundo. Sus esferas T y tecnología avanzada, combinadas con su intelecto superior, lo convierten en un miembro valioso de la JSA."
        ),
        SuperHero(
            "Flash",
            "Agosto",
            "Rápido y decidido, siempre llega a tiempo.",
            R.drawable.flash_icon,
            R.drawable.fondo_super,
            colorPrimario = Color(0xFFD32F2F),
            colorSecundario = Color(0xFFFFC107),
            descripcionDetallada = "Barry Allen es el velocista más rápido vivo, capaz de correr a velocidades que desafían las leyes de la física. Su conexión con la Speed Force le permite viajar en el tiempo y entre dimensiones."
        ),
        SuperHero(
            "Doctor Strange",
            "Septiembre",
            "Místico y sabio, domina las artes del tiempo.",
            R.drawable.dr_strange_icon,
            R.drawable.fondo_super,
            colorPrimario = Color(0xFF1976D2),
            colorSecundario = Color(0xFF9C27B0),
            descripcionDetallada = "Stephen Strange es el Hechicero Supremo de la Tierra. Maestro de las artes místicas, protege nuestro mundo de amenazas mágicas y dimensionales usando el Ojo de Agamotto y otros artefactos místicos."
        ),
        SuperHero(
            "Hulk",
            "Octubre",
            "Fuerza imparable, protege a los inocentes.",
            R.drawable.hulk_icon,
            R.drawable.fondo_super,
            colorPrimario = Color(0xFF4CAF50),
            colorSecundario = Color(0xFF8BC34A),
            descripcionDetallada = "Bruce Banner se transforma en el increíble Hulk cuando se enfurece. Con fuerza prácticamente ilimitada y resistencia sobrehumana, es una de las fuerzas más poderosas del universo Marvel."
        ),
        SuperHero(
            "Superman",
            "Noviembre",
            "Símbolo de esperanza y justicia para todos.",
            R.drawable.super_man_icon,
            R.drawable.fondo_super,
            colorPrimario = Color(0xFF1976D2),
            colorSecundario = Color(0xFFD32F2F),
            descripcionDetallada = "Kal-El de Krypton, criado como Clark Kent en Kansas. Con poderes prácticamente ilimitados bajo el sol amarillo, Superman representa la esperanza y es el símbolo definitivo de la justicia."
        ),
        SuperHero(
            "Viuda Negra",
            "Diciembre",
            "Letal y estratégica, una fuerza imparable.",
            R.drawable.viuda_negra_icon,
            R.drawable.fondo_super_gris,
            colorPrimario = Color(0xFF212121),
            colorSecundario = Color(0xFFD32F2F),
            descripcionDetallada = "Natasha Romanoff es una espía y asesina altamente entrenada. Ex-agente de la Sala Roja, ahora usa sus habilidades letales y expertise en combate para proteger al mundo como Avenger."
        ),
    )

    val hero = heroes.getOrElse(month - 1) { heroes.last() }
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Contenedor del header con fondo y overlay (posición fija)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLandscape) screenHeight * 0.5f else 300.dp)
            ) {
                // Fondo con imagen/video
                HeroBackgroundMedia(
                    imageRes = hero.fondoRes,
                    videoRes = hero.fondoVideoRes,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 44.dp))
                )

                // Overlay gradiente para mejor legibilidad
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                // Botón de regresar en la esquina superior
                IconButton(
                    onClick = { navController.navigate("fecha") },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White
                    )
                }

                // Título del superhéroe en el header
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(40.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "Tu superhéroe es:",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = hero.nombre,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Mes de ${hero.mes}",
                        fontSize = 14.sp,
                        color = hero.colorSecundario.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // --- ICONO FIJO ENTRE HEADER Y CONTENIDO ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-45).dp), // lo “pone” entre el header y el contenido
                contentAlignment = Alignment.Center
            ) {
                FloatingHeroIcon(
                    iconRes = hero.iconRes,
                    heroName = hero.nombre,
                    primaryColor = hero.colorPrimario,
                    secondaryColor = hero.colorSecundario
                )
            }

            // Espaciador para el icono flotante
            Spacer(modifier = Modifier.height(5.dp))

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card principal con información
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Descripción principal
                        Text(
                            text = hero.descripcion,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Divisor decorativo
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(4.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            hero.colorPrimario,
                                            hero.colorSecundario
                                        )
                                    ),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Descripción detallada
                        Text(
                            text = hero.descripcionDetallada,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Justify,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            lineHeight = 22.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Botón de acción principal
                Button(
                    onClick = { navController.navigate("fecha") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = hero.colorPrimario
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Text(
                        "Descubrir Otro Personaje",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }


    }
}

@Composable
fun HeroBackgroundMedia(
    imageRes: Int,
    videoRes: Int? = null,
    modifier: Modifier = Modifier
) {
    // Por ahora implementamos solo imágenes, pero la estructura permite videos
    Box(modifier = modifier) {
        if (videoRes != null) {
            // TODO: Implementar reproductor de video cuando sea necesario
            // VideoPlayer(videoRes = videoRes, modifier = Modifier.fillMaxSize())

            // Fallback a imagen mientras tanto
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Fondo superhéroe",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Fondo superhéroe",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun FloatingHeroIcon(
    iconRes: Int,
    heroName: String,
    primaryColor: Color,
    secondaryColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Círculo de fondo con gradiente
        Box(
            modifier = Modifier
                .size(150.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape
                )
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.95f)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // Borde interno con gradiente heroico
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                primaryColor,
                                secondaryColor,
                                primaryColor.copy(alpha = 0.7f),
                                secondaryColor,
                                primaryColor
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Círculo interior blanco
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Icono del superhéroe
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = heroName,
                        modifier = Modifier.size(90.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}