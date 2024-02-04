package com.teampophory.pophory.feature.setting.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teampophory.pophory.common.compose.DefaultPreview
import com.teampophory.pophory.designsystem.PophoryTheme

@Composable
fun LogoutDialog(
    setDialogShow: (Boolean) -> Unit = {},
    onLogout: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { setDialogShow(false) },
    ) {
        Surface(
            modifier = Modifier
                .width(280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(20.dp),
            color = PophoryTheme.colors.white,
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 36.dp,
                    end = 16.dp,
                    bottom = 24.dp,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "로그아웃 하실 건가요?",
                    style = PophoryTheme.typography.popupTitle,
                    color = PophoryTheme.colors.onSurface100,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "다음에 꼭 다시 보길 바라요",
                    style = PophoryTheme.typography.popupText,
                    color = PophoryTheme.colors.onSurface50,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { setDialogShow(false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PophoryTheme.colors.onSurface100,
                    ),
                ) {
                    Text(
                        text = "돌아가기",
                        style = PophoryTheme.typography.popupButton1,
                        color = PophoryTheme.colors.white,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onLogout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PophoryTheme.colors.onSurface20,
                    ),
                ) {
                    Text(
                        text = "로그아웃하기",
                        style = PophoryTheme.typography.popupButton1,
                        color = PophoryTheme.colors.onSurface50,
                    )
                }
            }
        }
    }
}

@DefaultPreview
@Composable
private fun WithdrawDialogPreview() {
    PophoryTheme {
        LogoutDialog()
    }
}
