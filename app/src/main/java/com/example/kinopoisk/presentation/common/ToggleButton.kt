package com.example.kinopoisk.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.presentation.Dimens

@Composable
fun ToggleButton(
    text: String,
    isSelected: Boolean,
    onToggle: () -> Unit,
    shape: Shape = MaterialTheme.shapes.medium
) {
    Button(
        onClick = onToggle,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = shape,
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = Dimens.SmallFontSize1,
            )
        )
    }
}