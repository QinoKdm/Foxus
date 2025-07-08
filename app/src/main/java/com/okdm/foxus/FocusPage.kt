package com.okdm.foxus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

// 计时器数据模型
data class FOCUS_TIMER(
    val id: String,
    val name: String,
    val focusTime: Int = 25,
    val restTime: Int = 5
)

// 计时器卡片组件
@Composable
fun FocusTimerCard(
    card: FOCUS_TIMER,
    onStart: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(15.dp),
        shape = RoundedCornerShape(24),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 顶部标题和时间信息
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.name,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(5.dp)
                )

                OutlinedCard(
                    shape = RoundedCornerShape(45.dp),
                    border = BorderStroke(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Focus Time: ${card.focusTime} min",
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // 底部按钮区域
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(horizontal = 5.dp),
                    onClick = onStart
                ) {
                    Text("Start Focus", fontSize = 17.sp)
                }

                Button(
                    modifier = Modifier
                        .weight(0.7f)
                        .height(50.dp)
                        .padding(horizontal = 5.dp),
                    onClick = onEdit
                ) {
                    Text("Edit", fontSize = 17.sp)
                }

                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .padding(horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = onDelete
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    }
}

@Composable
fun NoFocusTimerCard(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "暂无计时器",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "点击下方按钮添加",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

// 添加计时器的 FAB 组件
@Composable
fun AddFocusTimerCardFAB(onAdd: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()){
        ExtendedFloatingActionButton(
            onClick = onAdd,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            text = { Text("Add a New Timer") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(15.dp),
            containerColor = MaterialTheme.colorScheme.primary
        )
    }
}

// 主页面
@Composable
fun FocusPage() {
    Box(modifier = Modifier.fillMaxSize()) {


        // 添加 FAB 按钮
        AddFocusTimerCardFAB {

        }
    }
}

// 专注计时页面（占位）
@Composable
fun Focusing() {

}