package com.ru.devit.mediateka.data.cinema;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.db.CinemaDao;
import com.ru.devit.mediateka.data.repository.cinema.CinemaLocalRepository;
import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.models.db.CinemaEntity;
import com.ru.devit.mediateka.models.mapper.CinemaMapper;
import com.ru.devit.mediateka.models.model.Cinema;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CinemaLocalRepositoryTest extends UnitTest {

    @Mock private CinemaDao cinemaDaoMock;
    @Mock private CinemaMapper mapper;
    @Mock private CinemaActorJoinDao cinemaActorJoinDao;

    private CinemaRepository repository;

    private static final int TEST_CINEMA_ID = 569;
    private static final int TEST_PAGE = 2;

    @Override
    protected void onSetUp() {
        repository = new CinemaLocalRepository(cinemaDaoMock ,
                mapper ,
                cinemaActorJoinDao);
    }

    @Test
    public void shouldGetCinemaList(){
        List<CinemaEntity> cinemaEntityList = new ArrayList<>();
        doReturn(Single.just(cinemaEntityList)).when(cinemaDaoMock).getCinemas(anyInt());

        repository.getCinemas(TEST_PAGE);

        verify(cinemaDaoMock).getCinemas(eq(TEST_PAGE));
    }

    @Test
    public void shouldGetTopRatedCinemaList(){
        List<CinemaEntity> cinemaEntityList = new ArrayList<>();
        doReturn(Single.just(cinemaEntityList)).when(cinemaDaoMock).getTopRatedCinemas(TEST_PAGE);

        repository.getTopRatedCinemas(TEST_PAGE);

        verify(cinemaDaoMock).getTopRatedCinemas(TEST_PAGE);
    }

    @Test
    public void shouldGetUpComingCinemaList(){
        List<CinemaEntity> cinemaEntityList = new ArrayList<>();
        doReturn(Single.just(cinemaEntityList)).when(cinemaDaoMock).getUpComingCinemas(TEST_PAGE , Calendar.getInstance().get(Calendar.YEAR));

        repository.getUpComingCinemas(TEST_PAGE);

        verify(cinemaDaoMock).getUpComingCinemas(TEST_PAGE , Calendar.getInstance().get(Calendar.YEAR));
    }

    @Test
    public void shouldGetCinemaById(){
        Cinema cinema = new Cinema();
        doReturn(Single.just(cinema)).when(cinemaDaoMock).getCinemaById(TEST_CINEMA_ID);

        repository.getCinemaById(TEST_CINEMA_ID);

        verify(cinemaDaoMock).getCinemaById(TEST_CINEMA_ID);
    }
}
