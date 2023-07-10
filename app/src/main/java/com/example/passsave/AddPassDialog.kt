package com.example.passsave

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPassDialog(
    state: PassState,
    onEvent: (PassEvent) -> Unit,
    modifier: Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(PassEvent.HideDialog)
        },
        title = {Text(text = "Add Password")},
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvent(PassEvent.SaveTitle(it))
                    },
                    placeholder = {
                        Text(text = "Add Title")
                    }
                )
                TextField(
                    value = state.password,
                    onValueChange = {
                        onEvent(PassEvent.SavePass(it))
                    },
                    placeholder = {
                        Text(text = "Add Password")
                    }
                )
            }
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ){
                Button(onClick = {
                    onEvent(PassEvent.Save)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}