package com.ru.devit.mediateka.data.cinema;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.data.datasource.db.CinemaActorJoinDao;
import com.ru.devit.mediateka.data.datasource.db.CinemaDao;
import com.ru.devit.mediateka.data.datasource.network.CinemaApiService;
import com.ru.devit.mediateka.data.repository.cinema.CinemaLocalRepository;
import com.ru.devit.mediateka.data.repository.cinema.CinemaRemoteRepository;
import com.ru.devit.mediateka.domain.CinemaRepository;
import com.ru.devit.mediateka.models.mapper.CinemaEntityToCinema;
import com.ru.devit.mediateka.models.mapper.CinemaMapper;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.models.network.CinemaDetailResponse;
import com.ru.devit.mediateka.models.network.CinemaNetwork;
import com.ru.devit.mediateka.models.network.CinemaResponse;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Iterator;
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
    @Mock private CinemaEntityToCinema cinemaEntityToCinema;
    @Mock private CinemaActorJoinDao cinemaActorJoinDao;
    @Mock private Cinema cinemaMock;
    @Mock private CinemaDetailResponse cinemaDetailResponseMock;
    @Mock private CinemaResponse cinemaResponseMock;
    @Mock private List<CinemaNetwork> cinemaNetworkListMock;
    @Mock private Iterator<CinemaNetwork> cinemaNetworkIteratorMock;

    private CinemaRepository repository;

    @Override
    protected void onSetUp() {
        repository = new CinemaLocalRepository(cinemaDaoMock ,
                mapper ,
                cinemaActorJoinDao);
    }

    @Test
    public void shouldGetCinemas(){
//        doReturn(cinemaNetworkListMock).when(cinemaResponseMock).getCinemas();
//        doReturn(cinemaEntityToCinema).when(mapper).getCinemaEntityToCinema();
//        doReturn(cinemaNetworkIteratorMock).when(cinemaNetworkListMock).iterator();
//        doReturn(true).when(cinemaNetworkIteratorMock).hasNext();
//        doReturn(2).when(cinemaNetworkListMock).size();
//        doReturn(Single.just(cinemaResponseMock)).when(cinemaDaoMock).getCinemas(23);
//
//        repository.getCinemas(11);
//
//        verify(cinemaDaoMock).getCinemaById(eq(anyInt()));
//        verify(mapper).map(eq(cinemaDetailResponseMock));

        //TODO fix this test
    }
}
