package com.teampophory.pophory.feature.setting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teampophory.pophory.common.compose.DefaultPreview
import com.teampophory.pophory.design.PophoryTheme

@Composable
fun LogoutDialog(
    setDialogShow: (Boolean) -> Unit = {},
    onWithdraw: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { setDialogShow(false) }
    ) {
        Surface(
            modifier = Modifier
                .width(280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            color = PophoryTheme.colors.white
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 36.dp,
                    end = 16.dp,
                    bottom = 24.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "잠깐! 정말 탈퇴하려는거야?",
                    style = PophoryTheme.typography.popupTitle,
                    color = PophoryTheme.colors.onSurface100,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "지금 탈퇴하면 여러분의 앨범을\n다시 찾을 수 없어요",
                    textAlign = TextAlign.Center,
                    style = PophoryTheme.typography.popupText,
                    color = PophoryTheme.colors.onSurface50,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { setDialogShow(false) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PophoryTheme.colors.onSurface100
                    )
                ) {
                    Text(
                        text = "포포리 계속 이용할래",
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = PophoryTheme.colors.white,
                        style = PophoryTheme.typography.popupButton1,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "아쉽지만, 탈퇴할래",
                    color = PophoryTheme.colors.onSurface50,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onWithdraw() },
                    style = PophoryTheme.typography.popupButton2,
                )
            }
        }
    }
}

@DefaultPreview
@Composable
private fun LogoutDialogPreview() {
    PophoryTheme {
        LogoutDialog()
    }
}
