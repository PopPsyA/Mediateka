package com.ru.devit.mediateka.models.mapper;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.models.db.CinemaEntity;
import com.ru.devit.mediateka.models.model.Cinema;

import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CinemaEntityToCinemaTest extends UnitTest {

    @Mock private Cinema cinemaMock;
    @Mock private CinemaEntity cinemaEntityMock;

    private CinemaEntityToCinema mapper;

    @Override
    protected void onSetUp() {
        mapper = new CinemaEntityToCinema();
    }

    @Test
    public void testMap() {
        doReturn(62).when(cinemaMock).getId();
        doReturn("Star wars").when(cinemaMock).getTitle();
        doReturn("Best movie").when(cinemaMock).getDescription();
        doReturn(200000000).when(cinemaMock).getBudget();

        final CinemaEntity cinemaEntity = mapper.map(cinemaMock);
        assertThat(cinemaEntity.getCinemaId() , is(62));
        assertThat(cinemaEntity.getTitle() , is("Star wars"));
        assertThat(cinemaEntity.getDescription() , is("Best movie"));
        assertThat(cinemaEntity.getBudget() , is(200000000));
    }

    @Test
    public void testReverseMap() {
        doReturn(20).when(cinemaEntityMock).getCinemaId();
        doReturn("Avengers").when(cinemaEntityMock).getTitle();
        doReturn("Avengers:description").when(cinemaEntityMock).getDescription();
        doReturn("Stan Lee").when(cinemaEntityMock).getDirectorName();
        doReturn("2004.12.03").when(cinemaEntityMock).getReleaseDate();

        final Cinema cinema = mapper.reverseMap(cinemaEntityMock);
        assertThat(cinema.getId() , is(20));
        assertThat(cinema.getTitle() , is("Avengers"));
        assertThat(cinema.getDescription() , is("Avengers:description"));
        assertThat(cinema.getDirectorName() , is("Stan Lee"));
        assertThat(cinema.getReleaseDate() , is("2004.12.03"));
    }
}