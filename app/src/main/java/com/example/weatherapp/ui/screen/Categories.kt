package com.example.weatherapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.MockData
import com.example.weatherapp.MockData.getTimeAgo
import com.example.weatherapp.R
import com.example.weatherapp.models.TopNewsArticle
import com.example.weatherapp.models.getAllArticleCategory
import com.example.weatherapp.network.NewsManager
import com.example.weatherapp.ui.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Categories(onFetchCategory: (String) -> Unit={}, viewModel: MainViewModel) {
    val tabsItem = getAllArticleCategory()

    Column {
        LazyRow {
            items(tabsItem.size){
                val category = tabsItem[it]
                CategoryTab(
                    category = category.categoryName,
                    onFetchCategory = onFetchCategory,
                    isSelected = viewModel.selectedCategory.collectAsState().value == category
                )
            }
        }

        ArticleContent(articles = viewModel.getArticlesByCategory.collectAsState().value.articles ?: listOf())
    }
}

@Composable
fun CategoryTab(category: String,
                isSelected: Boolean = false,
                onFetchCategory: (String)->Unit){

    val background = if (isSelected) colorResource(id = R.color.purple_200)
                        else colorResource(id = R.color.purple_700)
    
    Surface(modifier = Modifier
        .padding(horizontal = 4.dp, vertical = 16.dp)
        .clickable { onFetchCategory(category) },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(text = category,
            style = MaterialTheme.typography.body2,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Composable
fun ArticleContent(articles: List<TopNewsArticle>, modifier: Modifier = Modifier){
    LazyColumn{
        items(articles){
            article-> 
            Card(modifier.padding(8.dp),
                border = BorderStroke(2.dp, colorResource(id = R.color.purple_500))) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    CoilImage(
                        imageModel = article.urlToImage,
                        modifier = modifier.size(100.dp),
                        placeHolder = painterResource(id = R.drawable.breaking_news),
                        error = painterResource(id = R.drawable.breaking_news)
                    )
                    Column(Modifier.padding(8.dp)) {
                        Text(text = article.title ?: "not available",
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = article.author ?: "Not Available")
                            Text(text = MockData.stringToDate(article.publishedAt?: "2022-12-04T01:57:36Z")!!.getTimeAgo())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ArticleContentPreview(){
    ArticleContent(articles = listOf(
        TopNewsArticle(
            author = "Thomas Barrabi",
            title = "Sen. Murkowski slams Dems over 'show votes' on federal election bills - Fox News",
            description = "Sen. Lisa Murkowski, R-Alaska, slammed Senate Democrats for pursuing “show votes” on federal election bills on Wednesday as Republicans used the filibuster to block consideration the John Lewis Voting Rights Advancement Act.",
            publishedAt = "2008-12-04T01:57:36Z"
    )
    ))
}
