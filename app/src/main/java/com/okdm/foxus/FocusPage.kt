import android.provider.CalendarContract.Colors
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.util.TableInfo
import com.okdm.foxus.ui.theme.RobotoFlexVariableEsp_Focusing
import com.okdm.foxus.ui.theme.RobotoFlexVariableEsp_QuickFocusTime
import com.okdm.foxus.ui.theme.RobotoFlexVariableEsp_QuickFocusTitle
import com.okdm.foxus.ui.theme.RobotoFlexVariableEsp_QuickFocus_min_
import com.okdm.foxus.ui.theme.RobotoFlexVariableTitle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration

// 主界面
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@ExperimentalMaterial3ExpressiveApi
fun FocusPage(
    onStartClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    var isExpanded by remember { mutableStateOf(false) }
    val quickFocusCardHeight by animateDpAsState(
        targetValue = if(isExpanded) 300.dp else 225.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )
    val spacerBetweenQuickFocusTimeAndButtons by animateDpAsState(
        targetValue = if(isExpanded) 85.dp else 10.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )
    val sharedKey = "Focusing"
    with(sharedTransitionScope){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(quickFocusCardHeight)
                    .clickable { onStartClick() }
                    .sharedElement(
                        rememberSharedContentState(key = sharedKey),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Quick Focus",
                        style = TextStyle(
                            fontFamily = RobotoFlexVariableEsp_QuickFocusTitle,
                            fontSize = 25.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ){
                        Text(
                            text = "25",
                            style = TextStyle(
                                fontFamily = RobotoFlexVariableEsp_QuickFocusTime,
                                fontSize = 80.sp
                            )
                        )
                        Text(
                            text = "min",
                            style = TextStyle(
                                fontFamily = RobotoFlexVariableEsp_QuickFocus_min_,
                                fontSize = 40.sp
                            ),
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(spacerBetweenQuickFocusTimeAndButtons))

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom,
                    ){
                        Button(
                            onClick = { isExpanded = !isExpanded },
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(
                                text = if(isExpanded) "Pause" else "Start Now",
                                style = TextStyle(
                                    fontFamily = RobotoFlexVariableEsp_QuickFocusTitle,
                                    fontSize = 20.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@ExperimentalMaterial3ExpressiveApi
fun Focusing(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){
    val sharedKey = "Focusing"
    var progress by remember { mutableFloatStateOf(0.1f) }
    val animatedProgress by
    animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    with(sharedTransitionScope){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .sharedElement(
                    rememberSharedContentState(key = sharedKey),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Focusing",
                style = TextStyle(
                fontFamily = RobotoFlexVariableEsp_Focusing,
                fontSize = 70.sp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 80.dp),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))
            CircularProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .height(200.dp)
                    .width(200.dp),
                strokeWidth = 10.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.requiredHeight(30.dp))
            Text("Set progress:")
            Slider(
                modifier = Modifier.width(300.dp),
                value = progress,
                valueRange = 0f..1f,
                onValueChange = { progress = it },
            )
            LinearWavyProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .padding(40.dp)
                    .height(20.dp)
                    .fillMaxWidth(),
                stroke = Stroke(width = 30f, cap = StrokeCap.Round),
                trackStroke = Stroke(width = 30f, cap = StrokeCap.Round),
                wavelength = 45.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

