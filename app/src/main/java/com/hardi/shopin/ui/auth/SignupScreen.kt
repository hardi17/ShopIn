package com.hardi.shopin.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hardi.shopin.R
import com.hardi.shopin.utils.AppUtil
import com.hardi.shopin.viewmodel.AuthViewModel

@Composable
fun SignupScreen(
    modifier: Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    onSuccessSignUp: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(R.color.light_yellow))
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.hello_there),
            color = Color.Black,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text(
            text = stringResource(R.string.signup_to_continue),
            color = Color.Black,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.enterEmail)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = stringResource(R.string.enterName)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text(text = stringResource(R.string.enterAddress)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.enterPassword)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                authViewModel.signUp(email, name, password) { success, message ->
                    if (success) {
                        AppUtil.showToast(context, message)
                        onSuccessSignUp()
                    } else {
                        AppUtil.showToast(context, message)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.signup),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontFamily = FontFamily.Default
            )
        }

    }
}

