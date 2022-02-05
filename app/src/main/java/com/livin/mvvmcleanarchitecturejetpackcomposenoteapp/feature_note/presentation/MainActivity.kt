package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.R
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.ui.CurvedImageCard
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var sizeState by remember {
                mutableStateOf(200.dp)
            }

            val s by animateDpAsState(
                targetValue = sizeState,
                spring(dampingRatio = Spring.DampingRatioLowBouncy)
            )

            val infiniteTransition = rememberInfiniteTransition()
            val color by infiniteTransition.animateColor(
                initialValue = Color.Red,
                targetValue = Color.Gray,
                animationSpec = infiniteRepeatable(
                    tween(durationMillis = 5000),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val list by remember {
                mutableStateOf(mutableListOf("One"))
            }
            var updatedMessage by remember {
                mutableStateOf("")
            }
            val message = remember {
                mutableStateOf("")
            }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .size(s)
                            .background(color), contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = { sizeState += 50.dp }) {
                            Text(text = "TEXT")
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        ArcAnim(percentage = .5f, number = 10)
                    }

                    TextField(
                        value = updatedMessage,
                        label = { Text(text = "Name") },
                        onValueChange = {
                            updatedMessage = it
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        list.add(updatedMessage)
                        scope.launch {
                            // scaffoldState.snackbarHostState.showSnackbar(message = message.value)
                        }
                    }) {
                        Text(text = "ADD")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        if (message.value.isNotBlank()) {
                            Text(text = message.value)
                        }
                    }
                    Greeting(list) {
                        message.value = it
                    }
                }
            }

        }
    }
}

var i = 0

@Composable
fun Greeting(list: MutableList<String>, update: (String) -> Unit) {


    SideEffect {
        i++
    }


    val painter = painterResource(id = R.drawable.sample)
    LazyColumn() {
        items(list.size) { position ->
            CurvedImageCard(
                painter = painter,
                imageDescription = "Demo",
                title = list[position],
                modifier = Modifier.clickable {
                    update("Current position is $position")

                })
        }
    }
}

@Composable
fun TestDispo(onBackPressedDispatcher: OnBackPressedDispatcher) {
    val callback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }

        }
    }

    DisposableEffect(key1 = onBackPressedDispatcher) {
        onBackPressedDispatcher.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }

    val counter = produceState(initialValue = 0) {
        value = 10
    }


}


@Composable
fun ArcAnim(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 20.sp,
    radius: Dp = 30.dp,
    color: Color = Color.Red,
    strokeWidth: Dp = 3.dp,
    animDuration: Int = 5000,
    animDelay: Int = 300
) {

    var animPlayedState by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if (animPlayedState) percentage else 0f,
        animationSpec = tween(durationMillis = animDuration, delayMillis = animDelay)
    )

    LaunchedEffect(key1 = true) {
        animPlayedState = true
    }

    Box(modifier = Modifier.size(radius * 2), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(radius * 2)) {
            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value,
                true,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            (curPercentage.value * number).toInt().toString(),
            color = Color.Blue,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}


