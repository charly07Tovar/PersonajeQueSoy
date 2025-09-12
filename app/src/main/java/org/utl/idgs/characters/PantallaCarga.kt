package org.utl.idgs.characters

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

@Composable
fun LoadingScreen(onFinished: (month: Int) -> Unit, month: Int) {
    // Estados para las animaciones
    var progress by remember { mutableFloatStateOf(0f) }
    var currentStep by remember { mutableIntStateOf(0) }

    // Configuración responsive
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    // Tamaños adaptativos
    val circleSize = if (isLandscape) minOf(screenWidth, screenHeight) * 0.25f else screenWidth * 0.4f
    val titleSize = if (isLandscape) 20.sp else 24.sp
    val subtitleSize = if (isLandscape) 14.sp else 16.sp

    // Colores del tema moderno superheroico
    val primaryColor = Color(0xFF1E40AF) // Azul Superman
    val secondaryColor = Color(0xFFDC2626) // Rojo heroico
    val accentColor = Color(0xFFFCD34D) // Dorado Wonder Woman
    val textPrimary = Color(0xFFFFFFFF) // Blanco para contraste
    val textSecondary = Color(0xFFE5E7EB) // Gris claro

    // Animaciones
    val infiniteTransition = rememberInfiniteTransition(label = "loading_animations")

    val rotationAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulseAnimation by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val glowAnimation by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    // Pasos de carga con sus respectivos textos
    val loadingSteps = listOf(
        "Iniciando análisis..." to 0.2f,
        "Procesando datos..." to 0.4f,
        "Analizando patrones..." to 0.6f,
        "Generando perfil..." to 0.8f,
        "Descubriendo tu personaje..." to 1f
    )

    // Simulación del progreso
    LaunchedEffect(Unit) {
        loadingSteps.forEachIndexed { index, (_, targetProgress) ->
            currentStep = index

            // Animación suave del progreso
            val startProgress = progress
            val duration = 800L
            val startTime = System.currentTimeMillis()

            while (progress < targetProgress) {
                val elapsed = System.currentTimeMillis() - startTime
                val progressRatio = (elapsed.toFloat() / duration).coerceAtMost(1f)
                progress = startProgress + (targetProgress - startProgress) * progressRatio
                delay(16) // ~60 FPS
            }

            delay(200) // Pausa entre pasos
        }

        delay(300) // Pausa final antes de completar
        onFinished(month)
    }

    // UI Principal con fondo superheroico
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo animado de superhéroes
        SuperheroBackground(
            modifier = Modifier.fillMaxSize(),
            primaryColor = primaryColor,
            secondaryColor = secondaryColor,
            accentColor = accentColor,
            progress = progress,
            glowAnimation = glowAnimation,
            rotationAnimation = rotationAnimation
        )

        // Contenedor principal centrado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (isLandscape) {
                Arrangement.Center
            } else {
                Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
            }
        ) {

            // Título principal
            Text(
                text = "Análisis Heroico",
                fontSize = titleSize,
                fontWeight = FontWeight.Bold,
                color = textPrimary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                )
            )

            // Contenedor del indicador de progreso
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(circleSize)
            ) {
                // Círculo de fondo con sombra suave
                Canvas(
                    modifier = Modifier
                        .size(circleSize)
                        .scale(pulseAnimation)
                        .alpha(0.2f)
                ) {
                    drawCircle(
                        color = primaryColor,
                        radius = size.minDimension / 2
                    )
                }

                // Indicador de progreso principal
                CustomCircularProgressIndicator(
                    progress = progress,
                    size = circleSize,
                    strokeWidth = 8.dp,
                    primaryColor = primaryColor,
                    secondaryColor = secondaryColor,
                    accentColor = accentColor,
                    rotation = rotationAnimation,
                    glow = glowAnimation
                )

                // Contenido central
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Icono animado
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = accentColor.copy(alpha = glowAnimation),
                        modifier = Modifier
                            .size(32.dp)
                            .scale(pulseAnimation * 0.9f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Porcentaje
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimary,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.7f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )
                }
            }

            // Texto de estado actual
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (currentStep < loadingSteps.size) loadingSteps[currentStep].first else "Completando...",
                    fontSize = subtitleSize,
                    color = textSecondary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(1f, 1f),
                            blurRadius = 2f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Barra de progreso lineal decorativa
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .width(200.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = accentColor,
                    trackColor = accentColor.copy(alpha = 0.3f)
                )
            }

            // Indicadores de pasos (solo en orientación vertical)
            if (!isLandscape && screenHeight > 600.dp) {
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(loadingSteps.size) { index ->
                        val isActive = index <= currentStep
                        val isCompleted = index < currentStep

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = when {
                                        isCompleted -> accentColor
                                        isActive -> primaryColor
                                        else -> textSecondary.copy(alpha = 0.3f)
                                    },
                                    shape = CircleShape
                                )
                                .scale(if (isActive && !isCompleted) pulseAnimation * 0.9f else 1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SuperheroBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    secondaryColor: Color,
    accentColor: Color,
    progress: Float,
    glowAnimation: Float,
    rotationAnimation: Float
) {
    val infiniteTransition = rememberInfiniteTransition(label = "superhero_bg")

    // Animaciones para elementos del fondo
    val floatingAnimation1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float1"
    )

    val floatingAnimation2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -25f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float2"
    )

    val energyWaveAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "energy_wave"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2

        // Fondo base con gradiente heroico oscuro
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF0C1129), // Azul muy oscuro tipo Batman
                    Color(0xFF1A202C), // Gris azulado oscuro
                    Color(0xFF000000)  // Negro profundo
                ),
                center = Offset(centerX, centerY),
                radius = maxOf(width, height) * 0.8f
            )
        )

        // Ondas de energía estilo Dr. Strange/Scarlet Witch
        repeat(4) { i ->
            val waveRadius = (120f + i * 180f) + progress * 300f
            val alpha = (0.15f - i * 0.03f) * glowAnimation

            drawCircle(
                color = when (i % 3) {
                    0 -> primaryColor.copy(alpha = alpha)
                    1 -> secondaryColor.copy(alpha = alpha)
                    else -> accentColor.copy(alpha = alpha)
                },
                radius = waveRadius,
                center = Offset(centerX, centerY),
                style = Stroke(width = 3f)
            )
        }

        // Símbolos de superhéroes flotantes
        drawSuperheroSymbols(
            centerX = centerX,
            centerY = centerY,
            width = width,
            height = height,
            floatingOffset1 = floatingAnimation1,
            floatingOffset2 = floatingAnimation2,
            primaryColor = primaryColor,
            secondaryColor = secondaryColor,
            accentColor = accentColor,
            alpha = glowAnimation * 0.4f + 0.2f
        )

        // Rayos de energía estilo Thor/Shazam
        drawEnergyBolts(
            centerX = centerX,
            centerY = centerY,
            width = width,
            height = height,
            rotation = energyWaveAnimation,
            progress = progress,
            primaryColor = primaryColor,
            accentColor = accentColor
        )

        // Partículas flotantes estilo Iron Man
        drawFloatingParticles(
            width = width,
            height = height,
            time = energyWaveAnimation,
            primaryColor = primaryColor,
            secondaryColor = secondaryColor,
            accentColor = accentColor,
            sparkle = glowAnimation
        )

        // Hexágonos tecnológicos estilo Iron Man/Cyborg
        drawTechHexagons(
            centerX = centerX,
            centerY = centerY,
            width = width,
            height = height,
            rotation = energyWaveAnimation * 0.5f,
            primaryColor = primaryColor.copy(alpha = 0.15f),
            secondaryColor = secondaryColor.copy(alpha = 0.1f)
        )

        // Efecto de aurora estilo Asgard
        drawAuroraEffect(
            width = width,
            height = height,
            time = energyWaveAnimation,
            primaryColor = primaryColor,
            secondaryColor = secondaryColor,
            accentColor = accentColor
        )
    }
}

fun DrawScope.drawSuperheroSymbols(
    centerX: Float,
    centerY: Float,
    width: Float,
    height: Float,
    floatingOffset1: Float,
    floatingOffset2: Float,
    primaryColor: Color,
    secondaryColor: Color,
    accentColor: Color,
    alpha: Float
) {
    // Símbolo estilo escudo de Capitán América (anillos concéntricos)
    val shieldX = width * 0.15f
    val shieldY = height * 0.25f + floatingOffset1
    repeat(3) { i ->
        drawCircle(
            color = when (i % 2) {
                0 -> primaryColor.copy(alpha = alpha)
                else -> Color.White.copy(alpha = alpha * 0.6f)
            },
            radius = 50f - i * 15f,
            center = Offset(shieldX, shieldY),
            style = if (i == 2) Stroke(width = 4f) else androidx.compose.ui.graphics.drawscope.Fill
        )
    }

    // Símbolo estilo rayo de Flash/Shazam
    val lightningX = width * 0.85f
    val lightningY = height * 0.75f + floatingOffset2
    val lightningPath = Path().apply {
        moveTo(lightningX, lightningY - 40f)
        lineTo(lightningX + 20f, lightningY - 15f)
        lineTo(lightningX + 8f, lightningY)
        lineTo(lightningX + 25f, lightningY + 25f)
        lineTo(lightningX - 8f, lightningY + 8f)
        lineTo(lightningX + 8f, lightningY - 20f)
        close()
    }
    drawPath(
        path = lightningPath,
        color = accentColor.copy(alpha = alpha * 1.2f),
        style = androidx.compose.ui.graphics.drawscope.Fill
    )

    // Símbolo estilo diamante de Superman
    val diamondX = width * 0.2f
    val diamondY = height * 0.8f + floatingOffset1 * 0.5f
    val diamondPath = Path().apply {
        moveTo(diamondX, diamondY - 30f)
        lineTo(diamondX + 25f, diamondY - 8f)
        lineTo(diamondX, diamondY + 30f)
        lineTo(diamondX - 25f, diamondY - 8f)
        close()
    }
    drawPath(
        path = diamondPath,
        color = secondaryColor.copy(alpha = alpha),
        style = androidx.compose.ui.graphics.drawscope.Fill
    )

    // Símbolo estilo estrella de Wonder Woman
    val starX = width * 0.8f
    val starY = height * 0.2f + floatingOffset2 * 0.7f
    drawStar(
        center = Offset(starX, starY),
        radius = 25f,
        color = accentColor.copy(alpha = alpha * 1.3f)
    )
}

fun DrawScope.drawStar(center: Offset, radius: Float, color: Color) {
    val path = Path()
    val angles = (0..9).map { it * 36f * (PI / 180f) }

    angles.forEachIndexed { index, angle ->
        val currentRadius = if (index % 2 == 0) radius else radius * 0.5f
        val x = center.x + currentRadius * cos(angle - PI / 2).toFloat()
        val y = center.y + currentRadius * sin(angle - PI / 2).toFloat()

        if (index == 0) path.moveTo(x, y)
        else path.lineTo(x, y)
    }
    path.close()

    drawPath(path, color)
}

fun DrawScope.drawEnergyBolts(
    centerX: Float,
    centerY: Float,
    width: Float,
    height: Float,
    rotation: Float,
    progress: Float,
    primaryColor: Color,
    accentColor: Color
) {
    val numBolts = 8
    repeat(numBolts) { i ->
        val angle = (i * 45f + rotation * 0.5f) * (PI / 180f)
        val distance = 180f + progress * 150f
        val startX = centerX + cos(angle).toFloat() * distance
        val startY = centerY + sin(angle).toFloat() * distance
        val endX = centerX + cos(angle).toFloat() * (distance + 120f)
        val endY = centerY + sin(angle).toFloat() * (distance + 120f)

        val alpha = 0.4f * (sin(rotation * PI / 180f + i * 2).toFloat().coerceAtLeast(0f))

        drawLine(
            color = if (i % 2 == 0) primaryColor.copy(alpha = alpha) else accentColor.copy(alpha = alpha),
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )
    }
}

fun DrawScope.drawFloatingParticles(
    width: Float,
    height: Float,
    time: Float,
    primaryColor: Color,
    secondaryColor: Color,
    accentColor: Color,
    sparkle: Float
) {
    val numParticles = 20
    repeat(numParticles) { i ->
        val x = (width * 0.1f + (width * 0.8f * ((i * 47) % 100) / 100f) +
                sin((time + i * 30) * PI / 180f).toFloat() * 30f)
        val y = (height * 0.1f + (height * 0.8f * ((i * 73) % 100) / 100f) +
                cos((time + i * 45) * PI / 180f).toFloat() * 20f)

        val size = 2f + sparkle * 6f
        val color = when (i % 3) {
            0 -> primaryColor.copy(alpha = sparkle * 0.9f)
            1 -> secondaryColor.copy(alpha = sparkle * 0.7f)
            else -> accentColor.copy(alpha = sparkle * 0.8f)
        }

        drawCircle(
            color = color,
            radius = size,
            center = Offset(x, y)
        )
    }
}

fun DrawScope.drawTechHexagons(
    centerX: Float,
    centerY: Float,
    width: Float,
    height: Float,
    rotation: Float,
    primaryColor: Color,
    secondaryColor: Color
) {
    repeat(10) { i ->
        val angle = (i * 36f + rotation * 0.3f) * (PI / 180f)
        val distance = 250f + i * 40f
        val hexX = centerX + cos(angle).toFloat() * distance
        val hexY = centerY + sin(angle).toFloat() * distance

        if (hexX > -50 && hexX < width + 50 && hexY > -50 && hexY < height + 50) {
            drawHexagon(
                center = Offset(hexX, hexY),
                radius = 30f + i * 4f,
                color = if (i % 2 == 0) primaryColor else secondaryColor,
                rotation = rotation + i * 20f
            )
        }
    }
}

fun DrawScope.drawHexagon(center: Offset, radius: Float, color: Color, rotation: Float) {
    val path = Path()
    val angles = (0..5).map { (it * 60f + rotation) * (PI / 180f) }

    angles.forEachIndexed { index, angle ->
        val x = center.x + radius * cos(angle).toFloat()
        val y = center.y + radius * sin(angle).toFloat()

        if (index == 0) path.moveTo(x, y)
        else path.lineTo(x, y)
    }
    path.close()

    drawPath(path, color, style = Stroke(width = 2f))
}

fun DrawScope.drawAuroraEffect(
    width: Float,
    height: Float,
    time: Float,
    primaryColor: Color,
    secondaryColor: Color,
    accentColor: Color
) {
    val numWaves = 4
    repeat(numWaves) { i ->
        val path = Path()
        val waveHeight = height * 0.12f
        val offsetY = height * 0.75f + i * 40f

        path.moveTo(0f, offsetY)

        for (x in 0..width.toInt() step 15) {
            val waveY = offsetY + sin((x * 0.008f + time * 0.015f + i * 3f) * PI).toFloat() * waveHeight
            path.lineTo(x.toFloat(), waveY)
        }

        val alpha = 0.15f + 0.15f * sin((time + i * 90f) * PI / 180f).toFloat().coerceAtLeast(0f)
        val waveColor = when (i % 3) {
            0 -> primaryColor.copy(alpha = alpha)
            1 -> secondaryColor.copy(alpha = alpha)
            else -> accentColor.copy(alpha = alpha)
        }

        drawPath(path, waveColor, style = Stroke(width = 4f, cap = StrokeCap.Round))
    }
}

@Composable
fun CustomCircularProgressIndicator(
    progress: Float,
    size: androidx.compose.ui.unit.Dp,
    strokeWidth: androidx.compose.ui.unit.Dp,
    primaryColor: Color,
    secondaryColor: Color,
    accentColor: Color,
    rotation: Float,
    glow: Float
) {
    Canvas(
        modifier = Modifier
            .size(size)
            .graphicsLayer(rotationZ = rotation)
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        val radius = (size.toPx() - strokeWidthPx) / 2
        val center = androidx.compose.ui.geometry.Offset(size.toPx() / 2, size.toPx() / 2)

        // Círculo de fondo
        drawCircle(
            color = primaryColor.copy(alpha = 0.2f),
            radius = radius,
            center = center,
            style = Stroke(strokeWidthPx)
        )

        // Gradiente para el progreso
        val brush = Brush.sweepGradient(
            colors = listOf(
                primaryColor,
                accentColor,
                secondaryColor,
                primaryColor.copy(alpha = 0.8f)
            ),
            center = center
        )

        // Arco de progreso principal
        drawArc(
            brush = brush,
            startAngle = -90f,
            sweepAngle = 360f * progress,
            useCenter = false,
            style = Stroke(
                width = strokeWidthPx,
                cap = StrokeCap.Round
            ),
            topLeft = androidx.compose.ui.geometry.Offset(strokeWidthPx / 2, strokeWidthPx / 2),
            size = androidx.compose.ui.geometry.Size(size.toPx() - strokeWidthPx, size.toPx() - strokeWidthPx)
        )

        // Efecto de brillo en el extremo
        if (progress > 0) {
            val angle = (-90f + 360f * progress) * (Math.PI / 180f)
            val endX = center.x + radius * cos(angle).toFloat()
            val endY = center.y + radius * sin(angle).toFloat()

            drawCircle(
                color = accentColor.copy(alpha = glow),
                radius = strokeWidthPx / 2 * glow * 1.5f,
                center = androidx.compose.ui.geometry.Offset(endX, endY)
            )
        }
    }
}