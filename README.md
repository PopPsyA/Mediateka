[![Open Source Love svg1](https://badges.frapsoft.com/os/v1/open-source.png?v=103)](https://github.com/Devit951/Mediateka)
![Travis-ci](https://api.travis-ci.org/Devit951/Mediateka.svg)
[![GitHub license](https://img.shields.io/github/license/dcendents/android-maven-gradle-plugin.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) 


![192px](https://user-images.githubusercontent.com/34313493/40320871-f2835e0e-5d4e-11e8-9fb0-5f0482d410e3.png)


# Mediateka
## About
The app demonstrates principles of Clean Architecture in Android.

Based on data from https://www.themoviedb.org/

## Simple illustrate demo app https://www.youtube.com/watch?v=mIP0qA8URfE

## Current functuonality : 
* First tab (ActualCinemas) : filter cinemas and show actual cinemas 
* Second tab (TopRatedCinemas) : filter cinemas and show top rated cinemas
* Third tab (UpComingCinemas) : filter cinemas and show future cinemas
* Each [CinemaDetail's](https://github.com/Devit951/Mediateka/blob/master/app/src/main/java/com/ru/devit/mediateka/presentation/cinemadetail/CinemaDetailsActivity.java) screen contain cinema info and all cast's to this cinema
* Each [ActorDetail's](https://github.com/Devit951/Mediateka/blob/master/app/src/main/java/com/ru/devit/mediateka/presentation/actordetail/ActorDetailActivity.java) screen contain actor info and all cinema's to this actor
* Mediateka perfectly work without internet connection(Need first time to download data)
* [Realized search functionality (cinemas , actors)](https://github.com/Devit951/Mediateka/blob/master/app/src/main/java/com/ru/devit/mediateka/presentation/search/SearchActivity.java)
* Realzied database with many-to-many relationship's
* Realized Transtition animation's between screen's
* All screen's have progress bar for responsiveness
* Support android version from 4.4
* Presintation layer work with [MVP](https://android.jlelse.eu/android-mvp-for-beginners-25889c500443) pattern

## Coming soon 
1. Add the service which handle new cinams and create notification to show user
2. Create watch list.
3. The CinemaDetail's screen gives a possibility to set schedule time.
4. Add PopularActors screen.
5. Add Advanced Search screen.
6. Support landscape orientation and tablets.
7. Simple info screen , that contains app info.
8. And more , more some beatiful features...

## Technology
1. [Retrofit 2](https://github.com/square/retrofit) is the #1 library for network calls at the moment.
2. [RxJava 2](https://github.com/ReactiveX/RxJava) brings the possibility to work with streams of data and helps to connect different Clean Architecture layers with each other.
3. [Dagger 2](https://github.com/google/dagger) helps to achieve Dependency Inversion principle through Dependency Injection mechanisms.
4. [Room](https://developer.android.com/topic/libraries/architecture/room) persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
5. [Picasso](https://github.com/square/picasso) a powerful image downloading and caching library for Android
6. [Pallete](https://developer.android.com/training/material/palette-colors) beatiful support libary for dynamic change background view color

## Testing
Coming soon...
