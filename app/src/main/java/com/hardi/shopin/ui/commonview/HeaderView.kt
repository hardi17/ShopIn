package com.hardi.shopin.ui.commonview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.hardi.shopin.R

@Composable
fun HeaderView() {

    var name by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("user")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener() {
                name = it.result.get("name").toString()
            }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.welcome_back),
                fontFamily = FontFamily.Default,
                fontSize = 20.sp
            )
            Text(
                text = name,
                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        IconButton(onClick = { /* todo */ }) {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "Search")
        }
    }

}