package com.sanathcoding.noteapplication.feature_note.presentation.notes

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.sanathcoding.noteapplication.R
import com.sanathcoding.noteapplication.core.util.TestTag.ORDER_SECTION
import com.sanathcoding.noteapplication.feature_note.di.NoteAppModule
import com.sanathcoding.noteapplication.feature_note.di.RepositoryModule
import com.sanathcoding.noteapplication.feature_note.presentation.MainActivity
import com.sanathcoding.noteapplication.feature_note.presentation.navigation.Screen
import com.sanathcoding.noteapplication.ui.theme.NoteApplicationTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NoteAppModule::class, RepositoryModule::class)
class NotesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NoteApplicationTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NoteScreen.route
                ) {
                    composable(route = Screen.NoteScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithTag(ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_note))
            .performClick()
        composeRule.onNodeWithContentDescription(context.getString(R.string.sort_note))
            .assertIsDisplayed()
    }

}