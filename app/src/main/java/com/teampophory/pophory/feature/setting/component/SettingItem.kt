package com.teampophory.pophory.feature.setting.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.teampophory.pophory.R
import com.teampophory.pophory.common.compose.DefaultPreview
import com.teampophory.pophory.design.PophoryTheme

@Composable
fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes right: Int = R.drawable.ic_chevron_right,
    onClick: () -> Unit = {},
) {
    val colors = PophoryTheme.colors
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable(onClick = onClick)
                .then(modifier),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = colors.onSurface40
            )
            Image(
                painter = painterResource(id = right),
                contentDescription = "Right Arrow"
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            thickness = Dp.Hairline,
            color = colors.onSurface20,
        )
    }
}


private fun Modifier.bottomBorder(borderWidth: Dp, color: Color): Modifier = drawBehind {
    val strokeWidth = borderWidth.value * density
    val y = size.height - strokeWidth / 2
    drawLine(
        color,
        Offset(0f, y),
        Offset(size.width, y),
        strokeWidth
    )
}

@DefaultPreview
@Composable
private fun SettingItemPreview() {
    PophoryTheme {
        SettingItem(title = "공지사항")
    }
}
