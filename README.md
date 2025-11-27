# TCAssignment

A clean, scalable, offline-capable Android app built with **Kotlin**, **Jetpack Compose**, **MVVM**, and modern best practices.

# Download
[Download App (APK)](https://github.com/harsh21p/TCAssignment/raw/refs/heads/main/TCAssignment.apk)

## Overview

API - https://picsum.photos/

The primary goals while building this app Ire:

* Maintainability & Scalability
* Testability
* Offline capability
* lazy image loading and scrolling
* Modular responsibilities

---

## Architecture

The app follows a simplified version of **Clean Architecture + MVVM**, structured into distinct, modular layers:

### Why MVVM?
* Separates UI from business logic.
* **ViewModel** survives configuration changes (like rotation).
* Enables writing highly testable business logic.
* Clean flow of data: Data -> ViewModel -> UIState -> Composables.

### Why Repository Pattern?
The repository abstracts the data source, which is important for **offline mode**. The ViewModel doesn't need to know if data is being read from the API or the cached photos. The Repository handles it
* Two data sources

### Why Use Cases (Actions)?
Use cases wrap single, specific actions (e.g., `GetPhotosAction`, `SaveSelectedAuthorAction`).
* **Benefits:** Very testable, easy to replace in tests with fake lambdas, and makes dependency injection straightforward. This approach is the perfect middle ground betIen "too simple" and "overkill Clean Architecture."

---

## Library Choices (Justification)

| Technology | Justification |
| :--- | :--- |
| **Jetpack Compose** | Declarative UI, eliminates **RecyclerView boilerplate**, lazy layouts ensure smooth scrolling, and perfect for state driven UI (filters/sorting). |
| **Kotlin Coroutines & Flow** | Ensures non-blocking background tasks (caching, API). **Flow** is used to observe the offline toggle state and persisted values. |
| **Retrofit + OkHttp** | Standard, stable, and minimal configuration for API calls. Logging interceptor aids debugging. |
| **Moshi** | Efficient and reliable JSON parsing, mapping smoothly to Kotlin data classes. |
| **Coil** | Chosen specifically for Compose. It is memory efficient, uses Coroutines internally, and provides automatic disk/memory caching for superior performance in scrolling lists. |
| **DataStore (Preferences)** | Used for storing simple persisted UI settings (selected author, offline mode toggle). Fully **asynchronous** and safe against corruption, integrating nicely with Flow. |

---

## Offline Behavior

I implemented a offline data sync.

| Scenario | Offline | Behavior |
| :--- | :--- | :--- |
| **Online (Sync Enabled)** | **ON** | Fetches from API, saves the latest response in DataStore/cache, replacing old cache. |
| **Offline (Sync Enabled)** | **ON** | Tries API, fails, then loads **cached data**. If no cache exists, shows "No Internet" error. |
| **Online (Sync Disabled)** | **OFF** | Always fetches from API, but **never saves/caches** images. Old cache is cleared immediately. |
| **Offline (Sync Disabled)** | **OFF** | Always fails to fetch. Shows a definitive "**No Internet**" error. |

With this way we can test both the features mentioned offline as well as error handling.

---

## UI Logic & Composables
Images are loaded gradually using Compose's lazy list feature for smooth performance. The UI uses a **Masonry Grid** where left and right columns flow independently. This creates a natural photo wall aesthetic and prevents short images from forcing unnecessary padding on taller ones. A subtle black gradient overlay is applied to the bottom of every image to ensure the author name is always clearly readable against varying image colors.

### State Management in UI
* `remember`: Used to avoid unnecessary recalculations inside Composables.
* `rememberSaveable`: Used to restore values (like scroll position, search input) on configuration changes (e.g., screen rotation).
* `MutableStateFlow`: Used in the ViewModel to centralize state. This ensures UI auto updates and makes the business logic highly testable.

---

## Feature List

### Completed Core Features
* Fetch photos from API
* Error UI and retry
* loading state
* **Filter** by author

### Extra Features
* **Sort** by author
* Offline capablities
* Shimmer skeleton loader

---

## Conclusion

This code is built to be **maintainable**, **scalable**, **readable**. Every architectural choice (Flow, MVVM, Repository, Compose) and every library (Coil, Retrofit, DataStore, Hilt) was chosen practically.

It is **not overkill** and **not shortcut** it is balanced.


## Photos & Video
<div style="display: flex; flex-direction: row; gap: 5px; justify-content: space-between;">
  
  <img width="19%" alt="Screenshot 1: Image Gallery Home" src="https://github.com/user-attachments/assets/120c8f5e-b1a7-41f9-8dd1-371707b4198b" />
  <img width="19%" alt="Screenshot 5: App Loading Shimmer" src="https://github.com/user-attachments/assets/b1c06be3-39f0-41c8-9090-f7ec10bfb2b9" />
  <img width="19%" height="2856" alt="Screenshot_20251127_220855" src="https://github.com/user-attachments/assets/04ff0c78-24d4-4aae-8ba1-3040d174812d" />
  <img width="19%" alt="Screenshot 2: Filter Dropdown" src="https://github.com/user-attachments/assets/878fabc5-072e-4bb3-a7c6-5ec1daaf7a79" />
  <img width="19%" alt="Screenshot 3: Sorting Options" src="https://github.com/user-attachments/assets/bcd17e00-aaa1-4a8b-a2fc-61288ac7c092" />

</div>
 <div style="width: 50%; margin: 0 auto;">
    <video 
      width="19%" 
      src="https://github.com/user-attachments/assets/f31202af-3308-44cd-9b9c-e3ab6405d534" 
      controls 
      muted 
      autoplay 
      loop>
    </video>
</div>









