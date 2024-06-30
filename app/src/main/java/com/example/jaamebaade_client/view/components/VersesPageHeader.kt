package com.example.jaamebaade_client.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.jaamebaade_client.viewmodel.VersesViewModel

@Composable
fun VersePageHeader(
    poetName: String,
    poemTitle: String,
    modifier: Modifier = Modifier,
    versesViewModel: VersesViewModel,
    showVerseNumbers: Boolean,
    onToggleVerseNumbers: () -> Unit
) {
    val isBookmarked by versesViewModel.isBookmarked.collectAsState()

    val bookmarkIconColor = if (isBookmarked) Color.Red else Color.Gray
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Yellow) // TODO change header color
    ) {
        Text(
            text = poetName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(end = 3.dp)
                .weight(0.5f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Text(
            text = poemTitle,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(end = 3.dp)
                .weight(0.5f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Spacer(modifier = Modifier.weight(0.1f))

        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More Options")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = if (showVerseNumbers) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (showVerseNumbers) "Hide Verse Numbers" else "Show Verse Numbers",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(text = if (showVerseNumbers) "مخفی‌سازی شماره بیت" else "نمایش شماره بیت", style = MaterialTheme.typography.headlineMedium)
                        }
                    },
                    onClick = {
                        onToggleVerseNumbers()
                    },
                )
                DropdownMenuItem(text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Bookmark",
                            tint = bookmarkIconColor,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = "علاقه‌مندی", style = MaterialTheme.typography.headlineMedium)
                    }
                }, onClick = {
                    versesViewModel.onBookmarkClicked()
                })
                DropdownMenuItem(text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(text = "اشتراک‌گذاری", style = MaterialTheme.typography.headlineMedium)
                    }
                }, onClick = {
                    versesViewModel.share(versesViewModel.verses.value, context)
                })
            }
        }


    }
}

