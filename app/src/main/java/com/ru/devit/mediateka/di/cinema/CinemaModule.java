package com.ru.devit.mediateka.di.cinema;

import android.content.Context;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.db.CinemaDao;
import com.ru.devit.mediateka.data.datasource.network.CinemaApiService;
import com.ru.devit.mediateka.data.repository.cinema.CinemaLocalRepository;
import com.ru.devit.mediateka.data.repository.cinema.CinemaRemoteRepository;
import com.ru.devit.mediateka.data.repository.cinema.CinemaRepository;
import com.ru.devit.mediateka.di.cinema.CinemaScope;
import com.ru.devit.mediateka.models.mapper.CinemaEntityToCinema;
import com.ru.devit.mediateka.models.mapper.CinemaMapper;
import com.ru.devit.mediateka.models.mapper.CinemaResponseToCinema;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CinemaModule {

    @CinemaScope
    @Provides
    CinemaRepository provideRepository(CinemaLocalRepository cache ,
                                       CinemaApiService apiService ,
                                       CinemaMapper mapper ,
                                       CinemaActorJoinDao cinemaActorJoinDao){
        return new CinemaRemoteRepository(cache , apiService , mapper , cinemaActorJoinDao);
    }

    @CinemaScope
    @Provides
    CinemaLocalRepository provideCinemaLocalRepository(CinemaDao cinemaDao ,
                                                       CinemaMapper cinemaMapper ,
                                                       CinemaActorJoinDao cinemaActorJoinDao){
        return new CinemaLocalRepository(cinemaDao , cinemaMapper , cinemaActorJoinDao);
    }

    @CinemaScope
    @Provides
    CinemaDao providesCinemaDao(Context context){
        MediatekaApp mediatekaApp = (MediatekaApp) context;
        return mediatekaApp.getDatabaseInstance()
                .getCinemaDao();
    }

    @CinemaScope
    @Provides
    CinemaMapper provideCinemaMapper(CinemaResponseToCinema cinemaResponseToCinema , CinemaEntityToCinema cinemaEntityToCinema){
        return new CinemaMapper(cinemaResponseToCinema , cinemaEntityToCinema);
    }

    @CinemaScope
    @Provides
    CinemaResponseToCinema provideCinemaResponseToCinema(){
        return new CinemaResponseToCinema();
    }

    @CinemaScope
    @Provides
    CinemaEntityToCinema provideCinemaEntityToCinema(){
        return new CinemaEntityToCinema();
    }
}
