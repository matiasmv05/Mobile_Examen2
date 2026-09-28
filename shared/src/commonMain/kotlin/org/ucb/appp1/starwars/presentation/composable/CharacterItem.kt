package org.ucb.appp1.starwars.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ucb.appp1.starwars.domain.model.CharacterModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CharacterItem(
    character: CharacterModel,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E2C)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFE81F)
            )

            Spacer(modifier = Modifier.height(10.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ChipInfo(label = "Altura", value = "${character.height} cm")
                ChipInfo(label = "Peso", value = "${character.mass} kg")
                ChipInfo(label = "Color de cabello", value = character.hairColor)
                ChipInfo(label = "Color de piel", value = character.skinColor)
                ChipInfo(label = "Color de ojos", value = character.eyeColor)
                ChipInfo(label = "Género", value = character.gender)
            }
        }
    }
}

@Composable
private fun ChipInfo(label: String, value: String) {
    SuggestionChip(
        onClick = {},
        label = {
            Text(
                text = "$label: $value",
                fontSize = 12.sp,
                color = Color.White
            )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = Color(0xFF2D2B3D)
        ),
        border = null,
        shape = RoundedCornerShape(8.dp)
    )
}
