package com.okdm.foxus.ui.theme

import com.okdm.foxus.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexVariableTitle = FontFamily(
    Font(
        resId = R.font.roboto_flex_variable, // 替换成你的字体文件名
        variationSettings = FontVariation.Settings(
            FontVariation.weight(900), // 示例：设置默认字重
            FontVariation.width(180f)      // 示例：设置默认宽度
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexVariableEsp_QuickFocusTitle = FontFamily(
    Font(
        resId = R.font.roboto_flex_variable, // 替换成你的字体文件名
        variationSettings = FontVariation.Settings(
            FontVariation.weight(800), // 示例：设置默认字重
            FontVariation.width(200f)      // 示例：设置默认宽度
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexVariableEsp_QuickFocusTime = FontFamily(
    Font(
        resId = R.font.roboto_flex_variable, // 替换成你的字体文件名
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700), // 示例：设置默认字重
            FontVariation.width(10f)      // 示例：设置默认宽度
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexVariableEsp_QuickFocus_min_ = FontFamily(
    Font(
        resId = R.font.roboto_flex_variable, // 替换成你的字体文件名
        variationSettings = FontVariation.Settings(
            FontVariation.weight(300), // 示例：设置默认字重
            FontVariation.width(10f)      // 示例：设置默认宽度
        )
    )
)

@OptIn(ExperimentalTextApi::class)
val RobotoFlexVariableEsp_Focusing = FontFamily(
    Font(
        resId = R.font.roboto_flex_variable, // 替换成你的字体文件名
        variationSettings = FontVariation.Settings(
            FontVariation.weight(900), // 示例：设置默认字重
            FontVariation.width(25f),// 示例：设置默认宽度
            FontVariation.Setting("xqpr", 90f),
            FontVariation.Setting("YOPQ", 25f),
            FontVariation.Setting("XTRA", 400f),
            FontVariation.Setting("YTUC", 760f),
            FontVariation.Setting("YTLC", 416f),
            FontVariation.Setting("YTAS", 750f),
            FontVariation.Setting("YTDE", -305f),
            FontVariation.Setting("opsz", 8f)
        )
    )
)