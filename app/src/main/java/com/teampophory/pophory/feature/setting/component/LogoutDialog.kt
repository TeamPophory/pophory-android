package com.teampophory.pophory.feature.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.teampophory.pophory.R
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
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { setDialogShow(false) }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "Close Dialog"
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Withdraw"
                )
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    text = "잠깐! 정말 탈퇴하려는거야?",
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "지금 탈퇴하면 저장했던\n추억이 모두 사라져요",
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { setDialogShow(false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PophoryTheme.colors.onSurface40
                    )
                ) {
                    Text(
                        text = "포포리 계속 이용할래",
                        color = PophoryTheme.colors.white
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "아쉽지만, 탈퇴할래",
                    color = PophoryTheme.colors.onSurface40,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { onWithdraw() }
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
