# Movies

## Project Overview
This app allows users to search movies and view their overview, cast, reviews and trailers. The movies can also be filtered to show users
top rated, upcoming and popular movies.

This app uses the API from [themoviedb.org](https://www.themoviedb.org/)

## Screenshots
![movies_list](https://user-images.githubusercontent.com/51857962/74320888-856b5c80-4dd5-11ea-8a6e-4dc962b278c6.png)
![movie_categories](https://user-images.githubusercontent.com/51857962/74320949-9ddb7700-4dd5-11ea-9721-56529159faad.png)
![movie_search](https://user-images.githubusercontent.com/51857962/74321079-cf544280-4dd5-11ea-8d92-4a07300931d8.png)
![movie_info](https://user-images.githubusercontent.com/51857962/74321090-d2e7c980-4dd5-11ea-925a-87c1a6045709.png)
![movie_trailer](https://user-images.githubusercontent.com/51857962/74321095-d67b5080-4dd5-11ea-829f-9fa4f86bcb30.png)
![movie_review](https://user-images.githubusercontent.com/51857962/74322992-e8aabe00-4dd8-11ea-9998-e85e5ee264c8.png)
![movie_casts](https://user-images.githubusercontent.com/51857962/74321102-da0ed780-4dd5-11ea-9bcd-c9b30175b420.png)





## API Key Note

**Define key in build.gradle**

In your Android studio root directory, locate the `gradle.properties` under `.gradle` folder and add the following: 
Add `PopularMoviesApp_ApiKey = "YOUR-API-KEY"`.

## Language

[Kotlin](https://kotlinlang.org/)


## Features

*   **Offline support:** the App caches any visited list (categories and search) into the room database.
*   Discover the most popular, most rated & upcoming movies
*   Search for movies by title
*   User can view and play trailers on youtube 
*   Shows a list of reviews for each movie
*   Shows general info for each movie
*   MVVM with Android Architecture Components(Room, LiveData, ViewModel)
*   Databinding,BindingAdapters
*   Pagination and endless scrolling using custom pagination.
*   Handle network status and network failures
*   ConstraintLayout(guidelines, barriers... etc)
*   ViewPager2
*   Material design.


## Libraries

-   [AndroidX](https://developer.android.com/jetpack/androidx/) - Previously known as 'Android support Library'
-   [Glide](https://github.com/bumptech/glide) - for loading and caching images 
-   [Retrofit 2](https://github.com/square/retrofit) - Type-safe HTTP client for Android and Java by Square, Inc. 
-   [Gson](https://github.com/google/gson) - for serialization/deserialization Java Objects into JSON and back
-   [Koin](https://insert-koin.io/) - for dependency injection
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) 
    * [Room](https://developer.android.com/topic/libraries/architecture/room)
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Android Data Binding](https://developer.android.com/topic/libraries/data-binding/) 
- [OkHttp](https://github.com/square/okhttp)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)
- [Material Design](https://material.io/develop/)

## External Resources
- [stackoverflow](https://stackoverflow.com/questions/37741872/how-to-make-custom-dotted-progress-bar-in-android) - for custom dotted progress bar
- [github/android](https://github.com/android/architecture-components-samples) - Proper use of Architecture Components
- [CodingWithMitch/courses](https://codingwithmitch.com/courses/) 

**Popular Movies uses the TMDb API but is not endorsed or certified by TMDb.**

