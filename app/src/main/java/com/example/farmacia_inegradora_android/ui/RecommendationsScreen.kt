package com.example.farmacia_inegradora_android.ui


import android.R.attr.fontWeight
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.farmacia_inegradora_android.models.RestockItem
import com.example.farmacia_inegradora_android.ui_state.RestockUIState

data class RestockProjectionItem(
    val id: Int,
    val codigo: String,
    val name: String,
    val stockActual: Int,
    val stockMinimo: Int,
    val stockMaximo: Int,
    val movimientoUltimoMes: Int,
    val porcentaje: Int,
    val estado: String,
    val sugerencia: Int
)

data class RestockProjectionUiState(
    val isLoading: Boolean = false,
    val data: List<RestockProjectionItem> = emptyList(),
    val error: String = ""
)

enum class RestockFilterType(val label: String) {
    ALL("Todas"),
    LOW_ROTATION("Baja"),
    HIGH_ROTATION("Alta")
}

private val BluePrimary = Color(0xFF3F5FB7)
private val LightBackground = Color(0xFFF2F3F7)
private val FilterInactive = Color(0xFFD9D9D9)
private val GreenPrimary = Color(0xFF2E7D32)
private val OrangePrimary = Color(0xFFE67E22)
private val TextDark = Color(0xFF20222B)
private val TextGray = Color(0xFF7A7D85)
private val BorderGray = Color(0xFF8A8D96)
@Composable
fun RestockProjectionScreen(
    uiState: RestockUIState
) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error.isNotEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(uiState.error)
            }
        }

        uiState.data.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay productos con stock bajo")
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.data, key = { it.id }) { item ->
                    RestockProjectionCard(item)
                }
            }
        }
    }
}
@Composable
fun ProjectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.ExtraBold,
        color = TextDark
    )
}

@Composable
fun RestockFilterSection(
    selectedFilter: RestockFilterType,
    onFilterSelected: (RestockFilterType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ProjectionFilterButton(
            text = RestockFilterType.ALL.label,
            selected = selectedFilter == RestockFilterType.ALL,
            onClick = { onFilterSelected(RestockFilterType.ALL) },
            modifier = Modifier.weight(1f)
        )

        ProjectionFilterButton(
            text = RestockFilterType.LOW_ROTATION.label,
            selected = selectedFilter == RestockFilterType.LOW_ROTATION,
            onClick = { onFilterSelected(RestockFilterType.LOW_ROTATION) },
            modifier = Modifier.weight(1f)
        )

        ProjectionFilterButton(
            text = RestockFilterType.HIGH_ROTATION.label,
            selected = selectedFilter == RestockFilterType.HIGH_ROTATION,
            onClick = { onFilterSelected(RestockFilterType.HIGH_ROTATION) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ProjectionFilterButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(58.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) BluePrimary else FilterInactive,
            contentColor = if (selected) Color.White else Color.Black
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun ProjectionDateRangeSection(
    fromDate: String,
    toDate: String,
    onFromDateChange: (String) -> Unit,
    onToDateChange: (String) -> Unit,
    onFromDateClick: () -> Unit,
    onToDateClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        ProjectionDateField(
            label = "Desde",
            value = fromDate,
            placeholder = "YYYY-MM-DD",
            onClick = onFromDateClick,
            modifier = Modifier.weight(1f)
        )

        ProjectionDateField(
            label = "Hasta",
            value = toDate,
            placeholder = "YYYY-MM-DD",
            onClick = onToDateClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ProjectionDateField(
    label: String,
    value: String,
    placeholder: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(128.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = androidx.compose.foundation.BorderStroke(1.4.dp, BorderGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineSmall,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (value.isBlank()) placeholder else value,
                style = MaterialTheme.typography.headlineSmall,
                color = TextGray
            )
        }
    }
}

@Composable
fun RestockProjectionCard(item: RestockItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stock: ${item.stock_actual} / Mín: ${item.stock_minimo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextGray
                )
                Spacer(modifier = Modifier.height(4.dp))

            }

            Text(
                text = "+${item.sugerencia}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFD32F2F)
            )
        }
    }
}
@Composable
fun ProjectionLoadingBlock() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = BluePrimary)
    }
}

@Composable
fun ProjectionErrorBlock(
    message: String,
    onRetryClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRetryClick) {
                Text("Reintentar")
            }
        }
    }
}

@Composable
fun ProjectionEmptyBlock() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay productos para reabastecer",
                style = MaterialTheme.typography.bodyLarge,
                color = TextGray
            )
        }
    }
}