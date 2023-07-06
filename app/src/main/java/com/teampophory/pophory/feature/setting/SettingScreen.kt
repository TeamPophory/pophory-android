package com.teampophory.pophory.feature.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.teampophory.pophory.R
import com.teampophory.pophory.common.compose.DefaultPreview
import com.teampophory.pophory.common.compose.bottomBorder
import com.teampophory.pophory.design.PophoryTheme
import com.teampophory.pophory.feature.setting.component.WithdrawDialog
import com.teampophory.pophory.feature.setting.component.SettingItem
import com.teampophory.pophory.feature.setting.component.LogoutDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    message: String,
    onNavigateHome: () -> Unit = {},
    onNavigateNotice: () -> Unit = {},
    onNavigatePersonalTerms: () -> Unit = {},
    onNavigateTerm: () -> Unit = {},
    onLogout: () -> Unit = {},
    onWithdrawal: () -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isWithdrawalDialogVisible by remember { mutableStateOf(false) }
    var isLogoutDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "설정",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateHome) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "Back To Home"
                        )
                    }
                },
                modifier = Modifier.bottomBorder(Dp.Hairline, PophoryTheme.colors.onSurface30)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SettingItem(title = "공지사항", onClick = onNavigateNotice)
            SettingItem(title = "개인정보 처리방침", onClick = onNavigatePersonalTerms)
            SettingItem(title = "이용약관", onClick = onNavigateTerm)
            SettingItem(title = "로그아웃", onClick = { isLogoutDialogVisible = true })
            SettingItem(title = "탈퇴하기", onClick = { isWithdrawalDialogVisible = true })
        }
        if (isLogoutDialogVisible) {
            LogoutDialog(
                onLogout = {
                    onLogout()
                    isLogoutDialogVisible = false
                },
                setDialogShow = { visible ->
                    isLogoutDialogVisible = visible
                }
            )
        }
        if (isWithdrawalDialogVisible) {
            WithdrawDialog(
                onWithdraw = {
                    onWithdrawal()
                    isWithdrawalDialogVisible = false
                },
                setDialogShow = { visible ->
                    isWithdrawalDialogVisible = visible
                }
            )
        }
    }
}

@DefaultPreview
@Composable
private fun SettingScreenPreview() {
    PophoryTheme {
        SettingScreen("")
    }
}