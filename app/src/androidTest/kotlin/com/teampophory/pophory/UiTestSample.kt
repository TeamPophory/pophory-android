package com.teampophory.pophory

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.teampophory.pophory.feature.signup.SignUpActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTestSample {
    @get:Rule var activityScenarioRule = activityScenarioRule<SignUpActivity>()

    @Test
    @DisplayName("입력한 텍스트가 tv_text_count에 표시되는지 확인합니다.")
    fun testCheckTextCount() {
        // tv_text_count가 보이는지 확인합니다.
        onView(withId(R.id.tv_text_count))
            .check(matches(not(withText("(0/6)~"))))
    }

    @Test
    @DisplayName("입력한 텍스트가 에러메세지를 발생시키는지 체크합니다.")
    fun checkErrorMessageVisibilityAfterInputLongText() {
        // 2자 이하의 텍스트를 입력합니다.
        onView(withId(R.id.edit_tv_name))
            .perform(typeText("0"), closeSoftKeyboard())

        // tv_error_message가 보이는지 확인합니다.
        onView(withId(R.id.tv_error_message))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}