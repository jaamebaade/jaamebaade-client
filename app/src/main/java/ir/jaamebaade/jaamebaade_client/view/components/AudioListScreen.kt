package ir.jaamebaade.jaamebaade_client.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.jaamebaade.jaamebaade_client.api.response.AudioData
import ir.jaamebaade.jaamebaade_client.model.Status

@Composable
fun AudioListScreen(
    audioDataList: List<AudioData>,
    expanded: Boolean,
    fetchStatus: Status,
    onDismiss: () -> Unit,
    onClick: (AudioData) -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .width(250.dp)
            .height(250.dp),
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        if (audioDataList.isEmpty() && fetchStatus == Status.SUCCESS) {
            Column(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "هیچ خوانشی یافت نشد",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
        when (fetchStatus) {
            Status.LOADING -> {
                Column(
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LoadingIndicator()
                }
            }

            Status.FAILED -> {
                Column(
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "خطا در دریافت اطلاعات",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            else -> {
                audioDataList.forEachIndexed { i, audioData ->
                    DropdownMenuItem(
                        onClick = {
                            onClick(audioData)
                        },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "audio for ${audioData.artist}"
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = audioData.artist,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    )
                    if (i != audioDataList.size - 1) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
