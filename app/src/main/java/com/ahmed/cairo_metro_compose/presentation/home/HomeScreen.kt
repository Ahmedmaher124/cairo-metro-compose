package com.ahmed.cairo_metro_compose.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.cairo_metro_compose.R
import com.ahmed.cairo_metro_compose.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetails: (startStation: String, endStation: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)) // Dark background for the whole screen
    ) {
        // Header
        HomeScreenHeader()

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Container for Dropdowns
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Start Station Dropdown
                StationDropdown(
                    label = "From",
                    selectedStation = uiState.startStation,
                    stations = uiState.stations.map { it.name },
                    isExpanded = uiState.isStartDropdownExpanded,
                    onToggle = { viewModel.toggleStartDropdown() },
                    onSelect = { station -> viewModel.onStartStationSelected(station) },
                    icon = Icons.Default.LocationOn
                )

                // End Station Dropdown
                StationDropdown(
                    label = "To",
                    selectedStation = uiState.endStation,
                    stations = uiState.stations.map { it.name },
                    isExpanded = uiState.isEndDropdownExpanded,
                    onToggle = { viewModel.toggleEndDropdown() },
                    onSelect = { station -> viewModel.onEndStationSelected(station) },
                    icon = Icons.Default.FmdGood
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Error Message
            if (uiState.error != null) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF321919)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint = Color(0xFFEF5350),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = uiState.error!!,
                            color = Color(0xFFEF5350),
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { viewModel.clearError() },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear error",
                                tint = Color(0xFFEF5350),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
            
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Line1Color)
                }
            }
        }

        // Action Buttons
        HomeScreenActionButtons(
            startStation = uiState.startStation,
            endStation = uiState.endStation,
            onFindRoute = { 
                if (uiState.startStation.isNotBlank() && uiState.endStation.isNotBlank()) {
                    onNavigateToDetails(uiState.startStation, uiState.endStation)
                }
            },
            onClear = { viewModel.clearSelection() }
        )
    }
}

@Composable
private fun HomeScreenHeader() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF1E1E1E) // Slightly lighter dark for header
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 24.dp, bottom = 32.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.metro),
                contentDescription = "Metro Logo",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Cairo Metro Route Finder",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Find your route instantly",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun StationDropdown(
    label: String,
    selectedStation: String,
    stations: List<String>,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onSelect: (String) -> Unit,
    icon: ImageVector
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = onToggle,
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF1E1E1E)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF333333))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFEF5350)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = selectedStation.ifBlank { "Select a station" },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedStation.isBlank()) Color.Gray else Color.White
                )
            }
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFEF5350)
            )
        }

        // Dropdown Menu
        if (isExpanded) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .shadow(12.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF252525)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 280.dp)
                ) {
                    items(stations.filter { it.isNotEmpty() }) { station ->
                        TextButton(
                            onClick = { onSelect(station) },
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Text(
                                text = station,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 14.sp,
                                color = Color.White,
                                textAlign = TextAlign.Start
                            )
                        }
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color(0xFF333333))
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeScreenActionButtons(
    startStation: String,
    endStation: String,
    onFindRoute: () -> Unit,
    onClear: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF121212)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onClear,
                modifier = Modifier.height(56.dp).weight(0.4f),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF333333)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear")
            }

            Button(
                onClick = onFindRoute,
                modifier = Modifier.height(56.dp).weight(0.6f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Line1Color,
                    disabledContainerColor = Line1Color.copy(alpha = 0.5f)
                ),
                enabled = startStation.isNotBlank() && endStation.isNotBlank()
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Find Route", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
