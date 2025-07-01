package com.hardi.shopin.ui.commonview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hardi.shopin.data.model.Category
import com.hardi.shopin.ui.navigation.GlobalNavigation
import com.hardi.shopin.ui.navigation.RouteScreen
import com.hardi.shopin.utils.AppConstant.CATEGORY_ID

@Composable
fun CategoriesView() {

    var categoryList = remember {
        mutableStateOf<List<Category>>(emptyList())
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("stock")
            .collection("categories")
            .get().addOnCompleteListener() {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { doc ->
                        doc.toObject(Category::class.java)
                    }
                    categoryList.value = resultList
                }
            }
    }

    LazyRow(
        modifier = Modifier.padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categoryList.value) { item ->
            CategoryItem(item)
        }
    }
}

@Composable
fun CategoryItem(item: Category) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .clickable {
                GlobalNavigation.navController.navigate(
                    RouteScreen.CategoryProductScreen.passArg(item.id)
                )
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = "Category image",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }

}