package com.example.jaamebaade_client.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun VerseTextField(
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onSelectionChange: (TextFieldValue) -> Unit,
) {
    var lastSelection by remember { mutableStateOf(textFieldValue.selection) }
    var job by remember { mutableStateOf<Job?>(null) }

    TextField(
        value = textFieldValue,
        onValueChange = {
            onValueChange(it)
            if (it.selection != lastSelection) {
                lastSelection = it.selection
                job?.cancel()
                job = CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    if (lastSelection == it.selection) {
                        onSelectionChange(it)
                    }
                }
            }
        },
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Center
        ),
        readOnly = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
    )
}
