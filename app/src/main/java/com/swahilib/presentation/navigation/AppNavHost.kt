package com.swahilib.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.*
import com.swahilib.data.models.*
import com.swahilib.presentation.screens.home.HomeScreen
import com.swahilib.presentation.screens.init.InitScreen
import com.swahilib.presentation.screens.splash.SplashScreen
import com.swahilib.presentation.screens.viewer.idiom.IdiomScreen
import com.swahilib.presentation.screens.viewer.proverb.ProverbScreen
import com.swahilib.presentation.screens.viewer.saying.SayingScreen
import com.swahilib.presentation.screens.viewer.word.WordScreen
import com.swahilib.presentation.viewmodels.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }

        composable(Routes.INIT) {
            val viewModel: InitViewModel = hiltViewModel()
            InitScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        composable(Routes.HOME) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                navController = navController,
            )
        }

        composable(route = Routes.IDIOM) {
            val idiom = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Idiom>("idiom")

            val viewModel: IdiomViewModel = hiltViewModel()

            IdiomScreen(
                onBackPressed = { navController.popBackStack() },
                viewModel = viewModel,
                idiom = idiom,
            )
        }

        composable(route = Routes.PROVERB) {
            val proverb = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Proverb>("proverb")

            val viewModel: ProverbViewModel = hiltViewModel()

            ProverbScreen(
                onBackPressed = { navController.popBackStack() },
                viewModel = viewModel,
                proverb = proverb,
            )
        }

        composable(route = Routes.SAYING) {
            val saying = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Saying>("saying")

            val viewModel: SayingViewModel = hiltViewModel()

            SayingScreen(
                onBackPressed = { navController.popBackStack() },
                viewModel = viewModel,
                saying = saying,
            )
        }

        composable(route = Routes.WORD) {
            val word = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Word>("word")

            val viewModel: WordViewModel = hiltViewModel()

            WordScreen(
                onBackPressed = { navController.popBackStack() },
                viewModel = viewModel,
                word = word,
            )
        }
    }
}