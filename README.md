# 🚇 Cairo Metro Compose

An Android application built with **Jetpack Compose** and **Clean Architecture** that helps users navigate the Cairo Metro system by finding optimal routes between stations, calculating fares, and estimating travel times.

---

## 📱 Screenshots

> Run the app on an emulator or device to see the UI in action.

---

## ✨ Features

- **Route Finding** – Select start and end stations to find the shortest path using BFS.
- **Fare Calculation** – Automatic ticket fare based on journey length:
  - Up to 9 stations → **10 EGP**
  - Up to 19 stations → **15 EGP**
  - 20+ stations → **20 EGP**
- **Travel Time Estimation** – Estimated travel time (3 minutes per station).
- **Line Transfer Support** – Seamlessly handles transfers between metro lines.
- **Color-Coded Lines** – Each metro line is visually distinguished by color.
- **Material 3 Design** – Modern UI with dark/light mode support.

---

## 🏗️ Architecture

The project follows **MVVM + Clean Architecture** with a strict separation of concerns across three layers:

```
app/
├── data/           # Data layer – JSON data source, DTOs, mappers, repository impl
├── domain/         # Domain layer – models, repository interface, use cases (BFS, fare, time)
└── presentation/   # Presentation layer – Jetpack Compose screens, ViewModels, UI state
```

### Key Components

| Layer        | Component              | Responsibility                                      |
|--------------|------------------------|-----------------------------------------------------|
| Data         | `MetroJsonDataSource`  | Loads metro station data from bundled JSON          |
| Data         | `RepoImpl`             | Implements `MetroRepo` interface                    |
| Domain       | `FindRouteUseCase`     | Orchestrates route finding                          |
| Domain       | `BFSUseCase`           | Breadth-First Search pathfinding algorithm          |
| Domain       | `CalculateFairUseCase` | Computes ticket fare from station count             |
| Domain       | `CalculateTimeUseCase` | Estimates travel duration                           |
| Presentation | `HomeScreen`           | Station selection UI                                |
| Presentation | `DetailsScreen`        | Route result display with expandable station cards  |
| Presentation | `AppNavHost`           | Navigation orchestration between screens            |

---

## 🛠️ Tech Stack

| Category        | Technology                          |
|-----------------|-------------------------------------|
| Language        | Kotlin 2.0.21                       |
| UI              | Jetpack Compose + Material 3        |
| Architecture    | MVVM + Clean Architecture           |
| Navigation      | Navigation Compose 2.8.5            |
| JSON Parsing    | Gson 2.11.0                         |
| Async / State   | Kotlin Coroutines + StateFlow       |
| Build           | Gradle 8.13.2 (Kotlin DSL)          |
| Min SDK         | 24 (Android 7.0)                    |
| Target SDK      | 36                                  |

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (Hedgehog or newer recommended)
- **Android SDK 36**
- **Java 11**

### Build & Run

```bash
# Clone the repository
git clone https://github.com/Ahmedmaher124/cairo-metro-compose.git
cd cairo-metro-compose

# Build the debug APK
./gradlew assembleDebug

# Install on a connected device / emulator
./gradlew installDebug
```

On Windows replace `./gradlew` with `gradlew.bat`.

### Run Tests

```bash
# Unit tests
./gradlew test

# Instrumented UI tests (requires a connected device or emulator)
./gradlew connectedAndroidTest
```

---

## 🗂️ Project Structure

```
app/src/main/
├── java/com/ahmed/cairo_metro_compose/
│   ├── data/
│   │   ├── datasource/       # MetroJsonDataSource
│   │   ├── mapper/           # DTO ↔ Domain model mapping
│   │   ├── model/            # StationDto, MetroDto
│   │   └── RepoImpl/         # MetroRepoImpl
│   ├── domain/
│   │   ├── model/            # Station, Lines enum, RouteResult
│   │   ├── repo/             # MetroRepo interface
│   │   └── usecase/          # FindRoute, BFS, CalculateFair, CalculateTime, …
│   ├── presentation/
│   │   ├── home/             # HomeScreen, HomeViewModel, HomeUiState
│   │   ├── details/          # DetailsScreen, DetailsViewModel, DetailsUiState
│   │   ├── AppNavHost.kt
│   │   └── MainActivity.kt
│   └── ui/theme/             # Material 3 colors, typography
└── res/
    └── raw/
        └── cairo_metro_structured.json   # Metro station data
```

---

## 🗺️ Cairo Metro Lines

| Line   | Name        | Route                    |
|--------|-------------|--------------------------|
| Line 1 | First Line  | Helwan ↔ El-Marg         |
| Line 2 | Second Line | Shubra ↔ El-Mounib       |
| Line 3 | Third Line  | Adly Mansour ↔ Kit Kat   |

Transfer stations (e.g., Tahrir Square, Attaba) allow passengers to switch between lines.

---

## 📄 Documentation

Additional documentation is available in the repository root:

- [`ARCHITECTURE_GUIDE.md`](ARCHITECTURE_GUIDE.md) – Detailed architecture overview
- [`DOCUMENTATION_INDEX.md`](DOCUMENTATION_INDEX.md) – Index of all documentation files

---

## 🤝 Contributing

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/my-feature`.
3. Commit your changes: `git commit -m "Add my feature"`.
4. Push to the branch: `git push origin feature/my-feature`.
5. Open a Pull Request.

---

## 📝 License

This project is open source. See the repository for license details.
