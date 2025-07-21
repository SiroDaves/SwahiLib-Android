package com.swahilib.presentation.screens.home

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.swahilib.core.utils.Preferences
import com.swahilib.domain.entity.UiState
import com.swahilib.domain.entity.homeTabs
import com.swahilib.presentation.components.action.*
import com.swahilib.presentation.viewmodels.HomeViewModel
import androidx.core.content.edit

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController,
) {
    val context = LocalContext.current
    val prefs = remember {
        context.getSharedPreferences(Preferences.PREFERENCE_FILE, Context.MODE_PRIVATE)
    }

    var fetchData by rememberSaveable { mutableStateOf(false) }
    if (!fetchData) {
        viewModel.fetchData()
        fetchData = true
    }

    val lastTabIndex = prefs.getInt(Preferences.LAST_HOME_TAB, 0)
    var selectedTabIndex by rememberSaveable { mutableStateOf(lastTabIndex) }
    val selectedTab = homeTabs[selectedTabIndex]

    val uiState by viewModel.uiState.collectAsState()

    var isSearching by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedLetter by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            if (isSearching) {
                SearchTopBar(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                        selectedLetter = ""
                        viewModel.filterData(selectedTab, it)
                    },
                    onClose = {
                        isSearching = false
                        searchQuery = ""
                        viewModel.filterData(selectedTab, "")
                    }
                )
            } else {
                AppTopBar(
                    title = "SwahiLib - Kamusi ya Kiswahili",
                    actions = {
                        if (uiState != UiState.Loading) {
                            IconButton(onClick = { isSearching = true }) {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                            }
                        }
                    }
                )
            }
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            HomeContent(
                viewModel = viewModel,
                navController = navController,
                selectedTab = selectedTab,
                selectedLetter = selectedLetter,
                onTabSelected = { tab ->
                    val tabIndex = homeTabs.indexOf(tab)
                    prefs.edit { putInt(Preferences.LAST_HOME_TAB, tabIndex) }
                    selectedTabIndex = tabIndex
                    selectedLetter = ""
                    viewModel.filterData(homeTabs[tabIndex], "")
                },
                onLetterSelected = { letter ->
                    selectedLetter = letter
                    viewModel.filterData(selectedTab, letter)
                }
            )
        }
    }
}
