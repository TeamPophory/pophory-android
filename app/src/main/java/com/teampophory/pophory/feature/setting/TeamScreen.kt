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
import com.teampophory.pophory.R
import com.teampophory.pophory.common.compose.DefaultPreview
import com.teampophory.pophory.common.compose.bottomBorder
import com.teampophory.pophory.design.PophoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "포포리팀",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = PophoryTheme.typography.headline2
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
            /* TODO by Nunu 팀 소개 사진 넣기 */
        }
    }
}

@DefaultPreview
@Composable
private fun TeamScreenPreview() {
    PophoryTheme {
        val navController = rememberNavController()
        TeamScreen(navController)
    }
}