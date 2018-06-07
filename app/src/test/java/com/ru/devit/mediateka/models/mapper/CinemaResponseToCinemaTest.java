package com.ru.devit.mediateka.models.mapper;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.models.network.CinemaDetailResponse;
import com.ru.devit.mediateka.models.network.CinemaNetwork;
import com.ru.devit.mediateka.models.network.CinemaResponse;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CinemaResponseToCinemaTest extends UnitTest {

    @Mock private CinemaDetailResponse cinemaDetailResponseMock;
    @Mock private CinemaResponse cinemaResponseMock;
    @Mock private List<CinemaNetwork> cinemaNetworkListMock;
    @Mock private Iterator<CinemaNetwork> cinemaNetworkIteratorMock;

    private CinemaResponseToCinema mapper;

    @Override
    protected void onSetUp() {
        mapper = new CinemaResponseToCinema();
    }

    @Test
    public void shouldMapId(){
        doReturn(77).when(cinemaDetailResponseMock).getId();

        final Cinema cinema = mapper.map(cinemaDetailResponseMock);
        assertThat(cinema.getId() , is(cinemaDetailResponseMock.getId()));
    }

    @Test
    public void shouldMapListOfCinemas(){
        CinemaNetwork cinemaNetwork = Mockito.mock(CinemaNetwork.class);
        doReturn(cinemaNetwork).when(cinemaNetworkListMock).get(eq(0));
        doReturn(true).when(cinemaNetworkIteratorMock).hasNext();
        doReturn(1).when(cinemaNetworkListMock).size();
        doReturn(cinemaNetworkIteratorMock).when(cinemaNetworkListMock).iterator();
        doReturn(cinemaNetworkListMock).when(cinemaResponseMock).getCinemas();

        final List<Cinema> cinemaList = mapper.map(cinemaResponseMock);
        assertNotNull(cinemaList);
        assertEquals(cinemaNetworkListMock.size() , cinemaList.size());
        assertFalse(cinemaList.isEmpty());
    }
}