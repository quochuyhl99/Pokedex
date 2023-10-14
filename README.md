# Project 4 - Udacity android kotlin

A simple quiz game about Pokemon knowledge.

Library in project:
- Retrofit: Make API call
- Moshi: parse JSON to Kotlin Object
- Glide: load and cache images by URL.
- Room: for local database storage.

It leverages the following components from the Jetpack library:

- ViewModel
- LiveData
- Data Binding with binding adapters
- Navigation: in-app navigation between different fragments and activities.
- RecyclerView: Makes it easy and efficient to display large datasets.
- MotionLayout: Adds engaging animations to improve user experience.

# Setting up the Repository
To get started with this project, simply pull the repository and import the project into Android Studio. From there, deploy the project to an emulator or device.
The project use free API from: https://pokeapi.co/
No need to get API key. For more detail please visit to: https://pokeapi.co/docs/v2#pokemon

# Pokedex App Design Document

## How to use app?
The app will display a hidden image of a Pokemon, and you'll choose one of the four provided answers. If your answer is correct, that Pokemon will be added to your Pokedex, and a notification will inform you of the correct response. Tapping on the notification will take you to the detailed information screen for that Pokemon. You can also access this detailed screen directly from the home page.

## Pokemon Class
The Pokemon class is a data class representing a Pokemon entity. It holds essential information about a Pokemon.

## Gameplay Flow

### Main Fragment

- When open app, the homepage is the MainFragment.
- The MainFragment show a list of caught Pokemon names and a "Search Pokemon" button.
- When clicks the "Search Pokemon" button, it will navigate to the SearchPokemonFragment.
- When clicks on a Pokemon in the list, they can view the Pokemon details.

### Search Pokemon Fragment

- The SearchPokemonFragment show a hidden image of a Pokemon and four answers containing Pokemon names, only one answer is correct.
- If click the correct answer, the chosen answer turns green, and the user can catch the Pokemon.
- If click the incorrect answer, the user can try again.

### Catching Pokemon

- When the user catches a Pokemon, its data is saved locally using Room for offline access.
- The notification will appear every time the user successfully catches a Pokemon.
- If click on the notification, it will navigate to the Pokemon details fragment showing the caught Pokemon data.

### Pokemon Details Fragment

- The Pokemon details fragment shows the Pokemon name, an image, and other Pokemon data.

### Motion Layout

- A Motion layout is used for transition effect when switch between fragments

## Architecture and Navigation

The app follows the MVVM architecture for clean separation of concerns and better maintainability.
Navigation between fragments is implemented using the Navigation component to navigate between activities and pass bundle data between them.

## Data Fetching and Storage

The project use free API from: https://pokeapi.co/
No need to get API key. For more detail please visit to: https://pokeapi.co/docs/v2#pokemon

Retrofit and Moshi are used to fetch Pokemon data from the PokeAPI to show data in the "Search Pokemon" fragment.
Room is used for local data storage to save the Pokemon data after answer correct.



