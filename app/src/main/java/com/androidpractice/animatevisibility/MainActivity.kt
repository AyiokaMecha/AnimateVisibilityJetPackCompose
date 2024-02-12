package com.androidpractice.animatevisibility

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidpractice.animatevisibility.ui.theme.AnimateVisibilityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateVisibilityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    var boxVisible by remember{ mutableStateOf(true) }

    val state = remember { MutableTransitionState(false) }
    state.apply { targetState = true }


    val onClick = {
        newState : Boolean ->
        boxVisible = newState
    }
    Column(
        modifier = Modifier.padding(20.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Crossfade(targetState = boxVisible, animationSpec = tween(500), label = "showAndHideButtons") { boxVisible ->
                when(boxVisible){
                    false -> CustomButton(text = "Show", targetState = true, onClick
                    = onClick)
                    true -> CustomButton(text = "Hide", targetState = false, onClick
                    = onClick)
                }
            }

        }
        if (boxVisible) {
            AnimatedVisibility(
//                visibleState = state,
                visible = boxVisible,
                enter = fadeIn(tween(300, easing = LinearOutSlowInEasing)),
                exit = slideOutVertically(animationSpec = repeatable(10, animation = tween(200, easing = CubicBezierEasing(0f, 2.2f, 1.3f, 2.1f)), repeatMode = RepeatMode.Reverse))
            ) {
                Spacer(modifier = Modifier.width(20.dp).animateEnterExit(
                    enter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 500),
                    ),
                    exit = slideOutVertically(tween(durationMillis = 500))
                ))
                Box(modifier = Modifier
                    .size(height = 200.dp, width = 200.dp)
                    .background(Color.Blue)
                    .animateEnterExit(
                        enter = slideInVertically(animationSpec = tween(299)),
                        exit = slideOutVertically()
                    ))
            }

        }
    }

}

@Composable
fun CustomButton(text: String, targetState: Boolean, onClick: (Boolean) -> Unit, bgColor: Color = Color.Black) {
    Button(onClick = { onClick(targetState) }
    , colors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
        containerColor = bgColor
    )
    ) {
        Text(text = text)
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AnimateVisibilityTheme {
        MainScreen()
    }
}