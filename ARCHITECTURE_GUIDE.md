# Cairo Metro Compose - Presentation Layer Architecture Improvements

## Overview
The presentation layer has been refactored to follow a modern, modular architecture with clear separation of concerns. Each feature (Home and Details) now has its own dedicated Screen, ViewModel, and UIState.

## Architecture Structure

### 1. **Home Feature**
Location: `presentation/home/`

#### HomeUiState.kt
- **Purpose**: Manages the state of the Home screen
- **Key Properties**:
  - `stations`: List of all available metro stations
  - `startStation`: Selected starting station
  - `endStation`: Selected destination station
  - `isLoading`: Loading indicator for station data
  - `error`: Error message display
  - `isStartDropdownExpanded`: Control for start station dropdown
  - `isEndDropdownExpanded`: Control for end station dropdown

#### HomeViewModel.kt
- **Purpose**: Handles business logic for the Home screen
- **Key Responsibilities**:
  - Loading stations from repository
  - Managing station selection
  - Dropdown state management
  - Error handling and clearing
  - Clearing user selections

#### HomeScreen.kt
- **Purpose**: Composable UI for the Home screen
- **Features**:
  - Station selection with dropdowns
  - Swap button to interchange start/end stations
  - Loading states
  - Error message display with dismiss option
  - Find Route button
  - Clear selection button
  - Beautiful Material 3 design with shadows and animations

#### HomeViewModelFactory.kt
- **Purpose**: Factory for creating HomeViewModel instances
- **Handles**: Dependency injection for repository and data sources

### 2. **Details Feature**
Location: `presentation/details/`

#### DetailsUiState.kt
- **Purpose**: Manages the state of the Details screen
- **Key Properties**:
  - `startStation`: Starting station for route display
  - `endStation`: Destination station for route display
  - `path`: List of stations in the complete route
  - `fare`: Total fare for the route in EGP
  - `time`: Total travel time in minutes
  - `isLoading`: Loading indicator for route search
  - `error`: Error message if route not found
  - `expandedStationIndex`: Track which station detail card is expanded

#### DetailsViewModel.kt
- **Purpose**: Handles business logic for the Details screen
- **Key Responsibilities**:
  - Finding routes between stations using FindRouteUseCase
  - Managing loading states during route search
  - Handling route not found errors
  - Toggling station detail expansion
  - Clearing routes for new searches

#### DetailsScreen.kt
- **Purpose**: Composable UI for the Details screen
- **Features**:
  - Route information card showing fare and time
  - Animated station path with expandable details
  - Station badges with color coding by line
  - Connection lines between stations
  - Journey summary with icons
  - Expandable station details (ID, Line number)
  - Error states and empty states
  - Loading animation
  - Back button and new search button

#### DetailsViewModelFactory.kt
- **Purpose**: Factory for creating DetailsViewModel instances
- **Handles**: Complex dependency injection including use cases

### 3. **Navigation & Main App**
Location: `presentation/`

#### MainApp.kt
- **Purpose**: Orchestrates navigation between screens
- **Features**:
  - Splash screen with animated Cairo Metro branding
  - Screen state management (Home/Details)
  - Route transition with data passing
  - Consistent theming

#### MainActivity.kt
- **Purpose**: Android Activity entry point
- **Responsibilities**:
  - Initializing ViewModels with factories
  - Setting up composition
  - Edge-to-edge display
  - Theme application

## Key Improvements

### 1. **Separation of Concerns**
- Each screen has its own ViewModel and UIState
- Clear responsibilities for each component
- Easier to test and maintain

### 2. **State Management**
- Using MutableStateFlow for reactive state
- Proper data class for immutable state representation
- Clear state transitions

### 3. **UI/UX Enhancements**
- Material 3 design system
- Smooth animations and transitions
- Loading states with spinners
- Error states with dismissable messages
- Empty states with helpful messages
- Expandable cards for more information
- Color-coded stations by metro line

### 4. **Code Organization**
- Feature-based folder structure
- Factory pattern for dependency injection
- Modular composables with single responsibilities
- Clear naming conventions

### 5. **Error Handling**
- Graceful error display
- User-friendly error messages
- Dismissable error notifications
- Proper loading state management

### 6. **Navigation**
- Simple state-based navigation
- Data passing between screens
- Proper back navigation

## Usage Example

```kotlin
// In MainActivity
val homeViewModel: HomeViewModel = viewModel(
    factory = HomeViewModelFactory(applicationContext)
)

val detailsViewModel: DetailsViewModel = viewModel(
    factory = DetailsViewModelFactory(applicationContext)
)

Cairo_Metro_ComposeTheme(isDarkMode = null) {
    MainApp(
        homeViewModel = homeViewModel,
        detailsViewModel = detailsViewModel
    )
}
```

## File Structure
```
presentation/
├── MainActivity.kt (Entry point)
├── MainApp.kt (Navigation orchestration)
├── home/
│   ├── HomeUiState.kt
│   ├── HomeViewModel.kt
│   ├── HomeScreen.kt
│   └── HomeViewModelFactory.kt
└── details/
    ├── DetailsUiState.kt
    ├── DetailsViewModel.kt
    ├── DetailsScreen.kt
    └── DetailsViewModelFactory.kt
```

## Benefits

1. **Testability**: Each ViewModel can be tested independently
2. **Reusability**: Screens and ViewModels can be reused in different contexts
3. **Maintainability**: Clear structure makes it easy to add features
4. **Scalability**: New features can be added following the same pattern
5. **Debugging**: State management makes debugging easier
6. **Performance**: Proper state management prevents unnecessary recompositions

## Next Steps

The architecture is now ready for:
- Unit tests for ViewModels
- Instrumented tests for UI composables
- Addition of new features following the same pattern
- Integration with dependency injection frameworks (Hilt)
- Adding preference/settings screens
- Implementing favorites or history features

