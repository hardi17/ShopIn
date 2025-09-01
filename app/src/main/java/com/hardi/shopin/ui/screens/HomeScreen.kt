package com.hardi.shopin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardi.shopin.R
import com.hardi.shopin.ui.commonview.BannerView
import com.hardi.shopin.ui.commonview.CategoriesView
import com.hardi.shopin.ui.commonview.HeaderView

@Composable
fun HomeScreen(
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        HeaderView()
        Spacer(modifier = Modifier.padding(10.dp))
        BannerView()
        Text(
            text = stringResource(R.string.categories),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        CategoriesView()
    }

}






