package id.shaderboi.cata.features_todo.ui.home

import android.content.Context
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import id.shaderboi.cata.R
import id.shaderboi.cata.di.AppModule
import id.shaderboi.cata.feature_todo.ui.common.rememberAppState
import id.shaderboi.cata.feature_todo.ui.common.util.LocalTheme
import id.shaderboi.cata.feature_todo.ui.home.HomeScreen
import id.shaderboi.cata.ui.MainActivity
import id.shaderboi.cata.ui.theme.Theme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class MiscScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()

        composeRule.setContent {
            val appState = rememberAppState()
            CompositionLocalProvider(LocalTheme provides Theme.Light) {
                HomeScreen(appState = appState)
            }
        }
    }

    @After
    fun deinit() {

    }

    @Test
    fun createToDo_addToDo() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_to_do_fab))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_title_text_field_test_tag))
            .performTextInput("abcabc")
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_description_text_field_test_tag))
            .performTextInput("blablabla")
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.select_priority))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithContentDescription("Priority Medium")
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_add_button_test_tag))
            .performClick()
    }

    @Test
    fun createToDo_priorityModalInvisible() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_to_do_fab))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.priority_select_dialog_test_tag))
            .assertDoesNotExist()
    }

    @Test
    fun createToDo_priorityModalVisible() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_to_do_fab))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.select_priority))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.priority_select_dialog_test_tag))
            .assertIsDisplayed()
    }

    @Test
    fun createToDo_formOnlyDescriptionNotEmpty() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_to_do_fab))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_description_text_field_test_tag))
            .performTextInput("blablabla")
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_add_button_test_tag))
            .assertIsNotEnabled()
    }

    @Test
    fun createToDo_formEmpty() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_to_do_fab))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_add_button_test_tag))
            .assertIsNotEnabled()
    }

    @Test
    fun toggleAddToDoOverlay_isInvisibleInitially() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_overlay_test_tag))
            .assertDoesNotExist()
    }

    @Test
    fun toggleAddToDoOverlay_isVisible() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_to_do_fab))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_overlay_test_tag))
            .assertIsDisplayed()
    }

    @Test
    fun toggleAddToDoOverlay_isInvisibleOnDismiss() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_to_do_fab))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_overlay_dismiss_test_tag))
            .performClick()
        composeRule
            .mainClock
            .advanceTimeBy(1000)
        composeRule
            .onNodeWithTag(context.getString(R.string.to_do_overlay_test_tag))
            .assertDoesNotExist()
    }
}