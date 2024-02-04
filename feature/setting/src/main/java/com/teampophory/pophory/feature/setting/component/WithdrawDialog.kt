package com.teampophory.pophory.feature.setting.component

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teampophory.pophory.common.compose.DefaultPreview
import com.teampophory.pophory.designsystem.PophoryTheme

@Composable
fun WithdrawDialog(
    setDialogShow: (Boolean) -> Unit = {},
    onWithdraw: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { setDialogShow(false) },
    ) {
        Surface(
            modifier = Modifier
                .width(280.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 36.dp,
                        end = 16.dp,
                        bottom = 24.dp,
                    )
                    .background(PophoryTheme.colors.white, RoundedCornerShape(20.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "정말 탈퇴하실 건가요?",
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
                        containerColor = PophoryTheme.colors.onSurface100,
                    ),
                ) {
                    Text(
                        text = "돌아가기",
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = PophoryTheme.colors.white,
                        style = PophoryTheme.typography.popupButton1,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "아쉽지만, 탈퇴하기",
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
private fun WithdrawDialogPreview() {
    PophoryTheme {
        WithdrawDialog()
    }
}
