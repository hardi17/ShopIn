package com.hardi.shopin.ui.screens

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hardi.shopin.R
import com.hardi.shopin.data.model.User
import com.hardi.shopin.ui.navigation.GlobalNavigation
import com.hardi.shopin.ui.navigation.RouteScreen
import com.hardi.shopin.utils.AppUtil

@Composable
fun ProfileScreen(
    modifier: Modifier
) {

    val user = remember {
        mutableStateOf(User())
    }

    var address by remember {
        mutableStateOf(user.value.address)
    }

    var context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(User::class.java)
                    if (result != null) {
                        user.value = result
                        address = user.value.address
                    }
                }
            }

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Text(
            text = stringResource(R.string.yourProfile),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile image",
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = user.value.name,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Email: ",
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
        )

        Text(
            text = user.value.email,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Address: ",
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
        )

        TextField(
            value = user.value.address,
            onValueChange = { it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                //update firestore
                if (address.isNotEmpty()) {
                    Firebase.firestore.collection("user")
                        .document(FirebaseAuth.getInstance().currentUser?.uid!!)
                        .update("address", address)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                AppUtil.showToast(context, "Address Updated")
                            }
                        }
                } else {
                    AppUtil.showToast(context, "Field is not empty")
                }
            }),
            singleLine = true,
            readOnly = true
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "View My Orders",
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray, shape = RectangleShape)
                .padding(vertical = 16.dp)
                .clickable {
                    GlobalNavigation.navController.navigate(RouteScreen.OrderScreen.name)
                }
        )

        Spacer(modifier = Modifier.height(25.dp))

        TextButton(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                val navController = GlobalNavigation.navController
                navController.popBackStack()
                GlobalNavigation.navController.navigate(RouteScreen.AuthScreen.name)
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.Black
            )
        ) {
            Text(
                text = "Logout",
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(12.dp).width(100.dp)
            )
        }
    }

}