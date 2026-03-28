package com.ahmed.cairo_metro_compose.presentation.details

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.cairo_metro_compose.domain.model.station
import com.ahmed.cairo_metro_compose.domain.model.lines
import com.ahmed.cairo_metro_compose.ui.theme.Line1Color
import com.ahmed.cairo_metro_compose.ui.theme.Line2Color
import com.ahmed.cairo_metro_compose.ui.theme.Line3Color

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            DetailsScreenHeader(
                onBackClick = onNavigateBack,
                startStation = uiState.startStation,
                endStation = uiState.endStation
            )
        },
        bottomBar = {
            DetailsScreenActionButton(
                onNewSearch = {
                    viewModel.clearRoute()
                    onNavigateToHome()
                },
                isVisible = uiState.path.isNotEmpty() || uiState.error != null
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.isLoading) {
                LoadingState()
            } else if (uiState.error != null) {
                ErrorState(uiState.error!!)
            } else if (uiState.path.isNotEmpty()) {
                RouteContent(uiState, viewModel)
            } else {
                EmptyState()
            }
        }
    }
}

@Composable
private fun DetailsScreenHeader(
    onBackClick: () -> Unit,
    startStation: String,
    endStation: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Route Found",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (startStation.isNotEmpty() && endStation.isNotEmpty()) {
                    Text(
                        text = "$startStation → $endStation",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun RouteContent(uiState: DetailsUiState, viewModel: DetailsViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        item {
            RouteInfoCard(
                fare = uiState.fare,
                time = uiState.time,
                stationCount = uiState.path.size
            )
        }

        item {
            Text(
                text = "Journey Path",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        itemsIndexed(uiState.path) { index, station ->
            StationTimelineItem(
                station = station,
                index = index,
                isFirst = index == 0,
                isLast = index == uiState.path.size - 1,
                isExpanded = uiState.expandedStationIndex == index,
                onToggleExpand = { viewModel.toggleStationExpanded(index) }
            )
        }
    }
}

@Composable
private fun RouteInfoCard(
    fare: Int?,
    time: Int?,
    stationCount: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(
                icon = Icons.Outlined.Payments,
                label = "Fare",
                value = "${fare ?: 0} EGP",
                color = Line2Color
            )
            InfoItem(
                icon = Icons.Outlined.Timer,
                label = "Time",
                value = "${time ?: 0} min",
                color = Line3Color
            )
            InfoItem(
                icon = Icons.Outlined.Route,
                label = "Stations",
                value = "$stationCount",
                color = Line1Color
            )
        }
    }
}

@Composable
private fun InfoItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
private fun StationTimelineItem(
    station: station,
    index: Int,
    isFirst: Boolean,
    isLast: Boolean,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    val lineColor = when (station.line) {
        lines.Line1 -> Line1Color
        lines.Line2 -> Line2Color
        lines.Line3 -> Line3Color
        else -> Line1Color
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Timeline Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            // Line above circle
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .weight(1f)
                    .background(
                        if (isFirst) Color.Transparent else lineColor
                    )
            )

            // Circle
            Box(
                modifier = Modifier
                    .size(if (isFirst || isLast) 20.dp else 14.dp)
                    .border(
                        width = 3.dp,
                        color = lineColor,
                        shape = CircleShape
                    )
                    .background(
                        if (isFirst || isLast) lineColor else Color.White,
                        CircleShape
                    )
            )

            // Line below circle
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .weight(1f)
                    .background(
                        if (isLast) Color.Transparent else lineColor
                    )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content Section
        Card(
            onClick = onToggleExpand,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isFirst || isLast) 
                    lineColor.copy(alpha = 0.08f) 
                else 
                    MaterialTheme.colorScheme.surface
            ),
            border = if (isFirst || isLast) 
                androidx.compose.foundation.BorderStroke(1.dp, lineColor.copy(alpha = 0.3f)) 
            else null,
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = station.name,
                            fontSize = 16.sp,
                            fontWeight = if (isFirst || isLast) FontWeight.ExtraBold else FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (station.is_transfer) {
                            Surface(
                                color = Color(0xFFFF9800).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    text = "TRANSFER STATION",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFE65100),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    
                    if (isFirst) {
                        Badge(containerColor = lineColor) { Text("START", color = Color.White) }
                    } else if (isLast) {
                        Badge(containerColor = lineColor) { Text("END", color = Color.White) }
                    }
                }

                AnimatedVisibility(visible = isExpanded) {
                    Column(modifier = Modifier.padding(top = 12.dp)) {
                        HorizontalDivider(thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        DetailRow("Line", station.line.name)
                        DetailRow("Platform", (station.order + 1).toString())
                        if (station.is_transfer) {
                            DetailRow("Connects to", station.transfer_lines.joinToString { it.name })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(strokeWidth = 5.dp, color = Line1Color)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Calculating best route...", fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.ErrorOutline, null, tint = Color.Red, modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Error Occurred", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(message, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("No path found between these stations.")
    }
}

@Composable
private fun DetailsScreenActionButton(onNewSearch: () -> Unit, isVisible: Boolean) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Button(
                onClick = onNewSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Refresh, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("NEW SEARCH", fontWeight = FontWeight.Bold)
            }
        }
    }
}
