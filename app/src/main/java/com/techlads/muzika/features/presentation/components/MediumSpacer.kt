package com.techlads.muzika.features.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MediumSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun LargeSpacer() {
    Spacer(modifier = Modifier.height(24.dp))
}