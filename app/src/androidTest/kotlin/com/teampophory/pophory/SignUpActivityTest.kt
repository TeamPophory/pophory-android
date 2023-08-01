package com.teampophory.pophory

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.teampophory.pophory.feature.signup.SignUpActivity
import de.mannodermaus.junit5.ActivityScenarioExtension
import org.hamcrest.CoreMatchers.not
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class SignUpActivityTest {

    @JvmField
    @RegisterExtension
    val activityScenario = ActivityScenarioExtension.launch<SignUpActivity>()

    @Test
    @DisplayName("TextInput 하단에는 항상 카운터가 표시된다.")
    fun testCheckTextCount() {
        onView(withId(R.id.tv_text_count))
            .check(matches(not(withText("(0/6)~"))))
    }

    @Test
    @DisplayName("2자 이하의 텍스트를 입력하면 에러가 발생한다")
    fun checkErrorMessageVisibilityAfterInputLongText() {
        // given
        val input = "0"

        // when
        onView(withId(R.id.edit_tv_name))
            .perform(typeText(input), closeSoftKeyboard())

        // then
        onView(withId(R.id.tv_error_message))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}
