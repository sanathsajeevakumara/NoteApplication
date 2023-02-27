package com.sanathcoding.noteapplication.feature_note.presentation

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.sanathcoding.noteapplication.R
import com.sanathcoding.noteapplication.core.util.TestTag.CONTENT_TEXT_FIELD
import com.sanathcoding.noteapplication.core.util.TestTag.NOTE_ITEM
import com.sanathcoding.noteapplication.core.util.TestTag.TITLE_TEXT_FIELD
import com.sanathcoding.noteapplication.feature_note.di.NoteAppModule
import com.sanathcoding.noteapplication.feature_note.di.RepositoryModule
import com.sanathcoding.noteapplication.feature_note.presentation.navigation.Navigation
import com.sanathcoding.noteapplication.ui.theme.NoteApplicationTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NoteAppModule::class, RepositoryModule::class)
class NotesEndToEndTest {

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
                Navigation(navController)
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // Click on the FAB button to create a note
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.add_note_desc))
            .performClick()

        // Enter the texts for Title and content text filed
        composeRule
            .onNodeWithTag(TITLE_TEXT_FIELD)
            .performTextInput("Title")
        composeRule
            .onNodeWithTag(CONTENT_TEXT_FIELD)
            .performTextInput("Content")
        // Click the FAB button to save the newly created note
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.save_note))
            .performClick()

        // Make sure there note with title call Text title displayed
        composeRule.onNodeWithText("Title").assertIsDisplayed()
        // Click the note to edit
        composeRule.onNodeWithText("Title").performClick()

        // Make sure title and content are displaying with "Title" and "Content"
        composeRule
            .onNodeWithTag(TITLE_TEXT_FIELD)
            .assertTextEquals("Title")
        composeRule
            .onNodeWithTag(CONTENT_TEXT_FIELD)
            .assertTextEquals("Content")
        // Enter the new texts for Title and content text filed
        composeRule
            .onNodeWithTag(TITLE_TEXT_FIELD)
            .performTextInput("2")
        // Click the FAB button to save the updated note
        composeRule
            .onNodeWithContentDescription(context.getString(R.string.save_note))
            .performClick()

        // Make sure there is note with title call "Title2" displayed
        composeRule.onNodeWithText("Title2").assertIsDisplayed()
        composeRule.onNodeWithText("Content").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        for (i in 1..3) {
            // Click on the FAB button to create a note
            composeRule
                .onNodeWithContentDescription(context.getString(R.string.add_note_desc))
                .performClick()

            // Enter the texts for Title and content text filed
            composeRule
                .onNodeWithTag(TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            // Click the FAB button to save the newly created note
            composeRule
                .onNodeWithContentDescription(context.getString(R.string.save_note))
                .performClick()
        }

            // Make sure there is note with title call "New Text title" displayed
            composeRule.onNodeWithText("1").assertIsDisplayed()
            composeRule.onNodeWithText("2").assertIsDisplayed()
            composeRule.onNodeWithText("3").assertIsDisplayed()

            composeRule
                .onNodeWithContentDescription(context.getString(R.string.sort_note))
                .performClick()
            composeRule
                .onNodeWithContentDescription(context.getString(R.string.orderby_title))
                .performClick()
            composeRule
                .onNodeWithContentDescription(context.getString(R.string.descending))
                .performClick()

            composeRule.onAllNodesWithTag(NOTE_ITEM)[0]
                .assertTextContains("3")
            composeRule.onAllNodesWithTag(NOTE_ITEM)[1]
                .assertTextContains("2")
            composeRule.onAllNodesWithTag(NOTE_ITEM)[2]
                .assertTextContains("1")
    }

}