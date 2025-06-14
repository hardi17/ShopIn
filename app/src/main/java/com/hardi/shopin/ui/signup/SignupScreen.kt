package com.hardi.shopin.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hardi.shopin.R

@Composable
fun SignupScreen(
    modifier: Modifier
) {

    Column (
        modifier = modifier.fillMaxSize()
    ){
        Text(
            text = stringResource(R.string.singup)
        )
    }
}