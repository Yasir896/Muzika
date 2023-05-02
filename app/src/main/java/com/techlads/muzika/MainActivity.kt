package com.techlads.muzika

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.techlads.muzika.featu.AudioViewModel
import com.techlads.muzika.features.presentation.home.HomeScreenNew
import com.techlads.muzika.ui.theme.MuzikaTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            MuzikaTheme {

                val permissionSatat = rememberPermissionState(
                    permission = Manifest.permission.READ_EXTERNAL_STORAGE
                )

                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(key1 = lifecycleOwner) {
                    val observer = LifecycleEventObserver{_, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            permissionSatat.launchPermissionRequest()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if(permissionSatat.hasPermission) {
                        val audioViewModel = viewModel(
                            modelClass = AudioViewModel::class.java
                        )
                        val audioList = audioViewModel.audioList
                        HomeScreenNew(
                            progress = audioViewModel.currentAudioProgress.value,
                            onProgressChange = {
                                               audioViewModel.seekTo(it)
                            },
                            isAudioPlaying = audioViewModel.isAudioPlaying,
                            audioList = audioList,
                            currentAudioPlaying = audioViewModel.currentPlayingAudio.value,
                            onStart = { audioViewModel.playAudio(it) },
                            onItemClicked = {
                                audioViewModel.playAudio(it)
                            },
                            onNext = {
                                audioViewModel.skipToNext()
                            }
                        )
                        //AppNavigation(navController = navController)
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "Grant Permission first to use this app")
                        }
                    }

                }
            }
        }
    }
}
