package com.teampophory.pophory.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))

@Stable
class PophoryTypography(
    headlineBold1: TextStyle,
    headlineMedium1: TextStyle,
    headline2: TextStyle,
    headline3: TextStyle,
    title1: TextStyle,
    text1: TextStyle,
    caption1: TextStyle,
    caption2: TextStyle,
    nav: TextStyle,
    popupTitle: TextStyle,
    popupText: TextStyle,
    popupButton1: TextStyle,
    popupButton2: TextStyle,
    modalTitle: TextStyle,
    modalText: TextStyle,
) {
    var headlineBold1: TextStyle by mutableStateOf(headlineBold1)
        private set
    var headlineMedium1: TextStyle by mutableStateOf(headlineMedium1)
        private set
    var headline2: TextStyle by mutableStateOf(headline2)
        private set
    var headline3: TextStyle by mutableStateOf(headline3)
        private set
    var title1: TextStyle by mutableStateOf(title1)
        private set
    var text1: TextStyle by mutableStateOf(text1)
        private set
    var caption1: TextStyle by mutableStateOf(caption1)
        private set
    var caption2: TextStyle by mutableStateOf(caption2)
        private set
    var nav: TextStyle by mutableStateOf(nav)
        private set
    var popupTitle: TextStyle by mutableStateOf(popupTitle)
        private set
    var popupText: TextStyle by mutableStateOf(popupText)
        private set
    var popupButton1: TextStyle by mutableStateOf(popupButton1)
        private set
    var popupButton2: TextStyle by mutableStateOf(popupButton2)
        private set
    var modalTitle: TextStyle by mutableStateOf(modalTitle)
        private set
    var modalText: TextStyle by mutableStateOf(modalText)
        private set

    fun update(other: PophoryTypography) {
        headlineBold1 = other.headlineBold1
        headlineMedium1 = other.headlineMedium1
        headline2 = other.headline2
        headline3 = other.headline3
        title1 = other.title1
        text1 = other.text1
        caption1 = other.caption1
        caption2 = other.caption2
        nav = other.nav
        popupTitle = other.popupTitle
        popupText = other.popupText
        popupButton1 = other.popupButton1
        popupButton2 = other.popupButton2
        modalTitle = other.modalTitle
        modalText = other.modalText
    }

    fun copy(
        headlineBold1: TextStyle = this.headlineBold1,
        headlineMedium1: TextStyle = this.headlineMedium1,
        headline2: TextStyle = this.headline2,
        headline3: TextStyle = this.headline3,
        title1: TextStyle = this.title1,
        text1: TextStyle = this.text1,
        caption1: TextStyle = this.caption1,
        caption2: TextStyle = this.caption2,
        nav: TextStyle = this.nav,
        popupTitle: TextStyle = this.popupTitle,
        popupText: TextStyle = this.popupText,
        popupButton1: TextStyle = this.popupButton1,
        popupButton2: TextStyle = this.popupButton2,
        modalTitle: TextStyle = this.modalTitle,
        modalText: TextStyle = this.modalText,
    ): PophoryTypography = PophoryTypography(
        headlineBold1,
        headlineMedium1,
        headline2,
        headline3,
        title1,
        text1,
        caption1,
        caption2,
        nav,
        popupTitle,
        popupText,
        popupButton1,
        popupButton2,
        modalTitle,
        modalText,
    )
}

@Composable
fun PophoryTypography(): PophoryTypography {
    return PophoryTypography(
        headlineBold1 = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 24.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.Bold,
        ),
        headlineMedium1 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 24.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.Medium,
        ),
        headline2 = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
        ),
        headline3 = TextStyle(
            fontFamily = PretendardSemiBold,
            fontSize = 18.sp,
            lineHeight = 17.sp,
        ),
        title1 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 17.sp,
            lineHeight = 24.sp,
        ),
        text1 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        caption1 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        caption2 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 12.sp,
            lineHeight = 17.sp,
        ),
        nav = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 12.sp,
            lineHeight = 14.sp,
        ),
        popupTitle = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 19.sp,
            lineHeight = 23.sp,
        ),
        popupText = TextStyle(
            fontFamily = PretendardRegular,
            fontSize = 16.sp,
            lineHeight = 23.sp,
        ),
        popupButton1 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 16.sp,
            lineHeight = 23.sp,
        ),
        popupButton2 = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 14.sp,
            lineHeight = 17.sp,
        ),
        modalTitle = TextStyle(
            fontFamily = PretendardBold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
        ),
        modalText = TextStyle(
            fontFamily = PretendardMedium,
            fontSize = 18.sp,
            lineHeight = 26.sp,
        ),
    )
}
