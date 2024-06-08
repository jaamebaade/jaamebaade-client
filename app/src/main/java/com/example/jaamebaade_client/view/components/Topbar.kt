package com.example.jaamebaade_client.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(innerPadding: PaddingValues) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary) //TODO color should be changed
            .padding(innerPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    )
    {
        Text(
            text = "جام باده",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        ) // TODO constants may need to be in another file
        Spacer(modifier = Modifier.width(16.dp)) // Add space
        Icon(
            Icons.Filled.Home,
            contentDescription = "Home",
            modifier = Modifier.size(48.dp)
        ) // TODO icon must be changed
    }
}

@Composable
@Preview(showBackground = true)
fun TopBarPreview() {
    TopBar(PaddingValues(16.dp, 16.dp, 16.dp, 16.dp))
}