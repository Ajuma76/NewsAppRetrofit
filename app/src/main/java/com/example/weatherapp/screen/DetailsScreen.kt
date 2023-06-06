package com.example.weatherapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.MockData
import com.example.weatherapp.MockData.getTimeAgo
import com.example.weatherapp.NewsData
import com.example.weatherapp.R
import com.example.weatherapp.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(article: TopNewsArticle, scrollState : ScrollState, navController: NavController){

    Scaffold(topBar =
    { DetailTopAppBar(onBackPressed = {navController.popBackStack()} )}) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally, ) {
            Text(text = "Details Screen", fontWeight = FontWeight.SemiBold)

            CoilImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(R.drawable.breaking_news),
                placeHolder = ImageBitmap.imageResource(R.drawable.breaking_news)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconWithInfo(icon = Icons.Default.Edit, info = article.author?:"Not Available")
                IconWithInfo(icon = Icons.Default.DateRange,
                    info = MockData.stringToDate(article.publishedAt!!)!!.getTimeAgo())
            }
            Text(text = article.title?:"Not Available", fontWeight = FontWeight.Bold)
            Text(text = article.description?:"Not Available", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun DetailTopAppBar(onBackPressed: ()-> Unit = {}){
    TopAppBar(title = {
        Text(text = "Details Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed()}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        }
        )
}



@Composable
fun IconWithInfo(icon: ImageVector, info: String){
     Icon(icon, contentDescription = "Author",
         modifier = Modifier.padding(end = 8.dp),
         colorResource(id = R.color.purple_500))
    Text(text = info)
}



@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview(){
    DetailScreen(
        TopNewsArticle(
            author = "Michael Schneider",
            title = "‘The Masked Singer’ Reveals Identity of the Beach Ball: Here Are the Stars Under the Mask - Variety",
            description = "SPOILER ALERT: Do not read ahead if you have not watched “The Masked Singer” Season 6, Episode 8, “Giving Thanks,” which aired November 3 on Fox. Honey Boo Boo, we hardly knew you. Reality TV mother and daughter stars June Edith “Mama June” Shannon and Alana …",
            publishedAt = "2021-11-04T02:00:00Z"
        )
        , rememberScrollState(), rememberNavController())
}