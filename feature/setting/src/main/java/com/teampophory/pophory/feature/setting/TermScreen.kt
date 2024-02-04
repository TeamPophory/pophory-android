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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.teampophory.pophory.common.compose.DefaultPreview
import com.teampophory.pophory.common.compose.bottomBorder
import com.teampophory.pophory.designsystem.PophoryTheme
import com.teampophory.pophory.feature.setting.component.SettingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermScreen(
    navController: NavController,
    onNavigatePersonalTerms: () -> Unit = {},
    onNavigateTerm: () -> Unit = {},
    onNavigateOss: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "약관 및 정책",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = PophoryTheme.typography.headline2,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = com.teampophory.pophory.designsystem.R.drawable.ic_chevron_left),
                            contentDescription = "Back To Home",
                        )
                    }
                },
                modifier = Modifier.bottomBorder(Dp.Hairline, PophoryTheme.colors.onSurface30),
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            SettingItem(title = "개인정보 처리방침", onClick = onNavigatePersonalTerms)
            SettingItem(title = "이용 약관", onClick = onNavigateTerm)
            SettingItem(title = "오픈소스 라이선스", onClick = onNavigateOss)
        }
    }
}

@DefaultPreview
@Composable
private fun TermScreenPreview() {
    PophoryTheme {
        val navController = rememberNavController()
        TermScreen(navController)
    }
}
