package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.LightBlue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.book_error_2
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import kotlin.math.round

@Composable
fun BookListItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .clickable(onClick = onClick),
        color = LightBlue.copy(alpha = 0.2f),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BookListItemImageBox(book = book)

            BookListItemSummary(
                book = book,
                modifier = Modifier
                    .weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterVertically),
            )
        }
    }
}

@Composable
fun BookListItemImageBox(
    book: Book,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }

        val painter = rememberAsyncImagePainter(
            model = book.imageUrl,
            onSuccess = {
                imageLoadResult = if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception("Invalid image size"))
                }
            },
            onError = {
                it.result.throwable.printStackTrace()
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )

        when(val result = imageLoadResult) {
            null -> CircularProgressIndicator()
            else -> {
                Image(
                    painter = if(result.isSuccess) painter else painterResource(Res.drawable.book_error_2),
                    contentDescription = book.description,
                    contentScale = if(result.isSuccess) {
                        ContentScale.Crop
                    } else {
                        ContentScale.Fit
                    },
                    modifier = Modifier
                        .aspectRatio(
                            0.65f,
                            matchHeightConstraintsFirst = true
                        )
                )
            }
        }
    }
}

@Composable
fun BookListItemSummary(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = book.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        book.authors.firstOrNull()?.let { author ->
            Text(
                text = author,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        book.averageRating?.let { rating ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${round(rating * 10) / 10.0}",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = SandYellow
                )
            }
        }
    }
}