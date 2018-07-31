package com.ru.devit.mediateka.presentation.favouritelistcinema;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.data.SharedPreferenceManager;
import com.ru.devit.mediateka.data.repository.cinema.CinemaLocalRepository;
import com.ru.devit.mediateka.domain.cinemausecases.GetFavouriteListCinema;
import com.ru.devit.mediateka.models.model.Cinema;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class FavouriteListCinemaPresenterTest extends UnitTest {

    @Mock private FavouriteListCinemaPresenter.View view;
    @Mock private CinemaLocalRepository repository;
    @Mock private SharedPreferenceManager sharedPreferenceManager;

    private TestScheduler testScheduler;
    private GetFavouriteListCinema useCaseGetFavouriteListCinema;
    private FavouriteListCinemaPresenter presenter;

    private static final int TEST_CINEMA_ID = 65;

    @Override
    protected void onSetUp() {
        testScheduler = new TestScheduler();
        useCaseGetFavouriteListCinema = new GetFavouriteListCinema(testScheduler , testScheduler , repository);
        presenter = new FavouriteListCinemaPresenter(useCaseGetFavouriteListCinema , sharedPreferenceManager);
    }

    @Test
    public void shouldShowAllFavouriteCinemaListWhenInitialize(){
        presenter.setView(view);
        List<Cinema> cinemaList = new ArrayList<>();
        doReturn(Maybe.just(cinemaList)).when(repository).getFavouriteListCinema();
        presenter.initialize();
        testScheduler.triggerActions();

        verify(view).showLoading();
        verify(view).showFavouriteListCinema(cinemaList);
    }

    @Test
    public void shouldClearAllFavouriteCinemaListWhenOnClearBtnClicked(){
        presenter.setView(view);
        doReturn(Completable.complete()).when(repository).clearFavouriteListCinema();
        presenter.onMenuClearFavouriteListClicked();
        testScheduler.triggerActions();

        useCaseGetFavouriteListCinema.clearFavouriteList()
                .test()
                .assertSubscribed()
                .onComplete();
        verify(view).showSuccessfullyFavouriteListCleared();
    }

    @Test
    public void shouldRemoveCinemaWhenSwiped(){
        Cinema deletedCinema = new Cinema();
        deletedCinema.setId(TEST_CINEMA_ID);
        deletedCinema.setTitle("Lala land");
        List<Cinema> cinemaList = new ArrayList<>();
        cinemaList.add(deletedCinema);
        presenter.setView(view);
        doReturn(Completable.complete()).when(repository).removeFromDatabaseFavouriteCinema(TEST_CINEMA_ID);
        presenter.setCinemaList(cinemaList);
        presenter.onCinemaSwiped(0);
        testScheduler.triggerActions();

        useCaseGetFavouriteListCinema.removeFavouriteCinema(TEST_CINEMA_ID)
                .test()
                .assertSubscribed()
                .onComplete();

        verify(view).showUndoAction("Lala land" , deletedCinema , 0);
    }

    @Test
    public void shouldUndoWhenUndoPressed(){
        Cinema restoredCinema = new Cinema();
        restoredCinema.setId(TEST_CINEMA_ID);
        List<Cinema> cinemaList = new ArrayList<>();
        cinemaList.add(restoredCinema);
        presenter.setCinemaList(cinemaList);
        doReturn(Completable.complete()).when(repository).saveIntoDatabaseFavouriteCinema(TEST_CINEMA_ID);
        presenter.onUndoClicked(restoredCinema , 0);

        useCaseGetFavouriteListCinema.saveFavouriteCinema(TEST_CINEMA_ID)
                .test()
                .assertSubscribed()
                .onComplete();
    }
}