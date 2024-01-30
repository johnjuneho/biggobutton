package com.example.biggobutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.biggobutton.ui.theme.BigGoButtonTheme
import kotlin.random.Random
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.draw.scale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BigGoButtonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BigGoButton()
                }
            }
        }
    }
}

@Composable
fun getButtonText(clickCount: Int): String {
    return when (clickCount) {
        0 -> stringResource(id = R.string.push_me)
        1 -> stringResource(id = R.string.pushed_once)
        else -> stringResource(id = R.string.pushed_times, clickCount)
    }
}

@Composable
fun BigGoButton() {
    val context = LocalContext.current
    var clickCount by remember { mutableStateOf(0) }
    var backgroundColor by remember { mutableStateOf(Color(context.getColor(R.color.initial_background_color))) }
    var textColor by remember { mutableStateOf(Color(context.getColor(R.color.initial_text_color))) }
    val scale = remember { Animatable(1f) } // Starting scale
    var animationPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(animationPlaying) {
        if (animationPlaying) {
            scale.animateTo(targetValue = 1.2f, animationSpec = tween(durationMillis = 600))
            scale.animateTo(targetValue = 1f, animationSpec = tween(durationMillis = 600))
        } else {
            scale.snapTo(1f)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Button(
            onClick = {
                clickCount++
                backgroundColor = randomColor()
                textColor = randomColor()
                animationPlaying = !animationPlaying
            },
            modifier = Modifier.scale(scale.value)
        ) {
            Text(
                text = getButtonText(clickCount),
                color = textColor,
                fontSize = 40.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

fun randomColor(): Color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BigGoButtonTheme {
        BigGoButton()
    }
}
