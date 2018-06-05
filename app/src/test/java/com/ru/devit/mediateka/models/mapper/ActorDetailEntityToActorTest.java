package com.ru.devit.mediateka.models.mapper;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.models.db.ActorEntity;
import com.ru.devit.mediateka.models.db.CinemaEntity;
import com.ru.devit.mediateka.models.model.Actor;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ActorDetailEntityToActorTest extends UnitTest {

    @Mock private ActorEntity actorEntityMock;
    @Mock private Actor actorMock;
    @Mock private List<CinemaEntity> cinemaEntitiesMock;
    @Mock private Iterator<CinemaEntity> cinemaEntityIteratorMock;

    private ActorDetailEntityToActor mapper;

    @Override
    protected void onSetUp() {
        mapper = new ActorDetailEntityToActor();
    }

    @Test
    public void correctlyMapActorId(){
        int expectedActorId = 23;
        doReturn(expectedActorId).when(actorMock).getActorId();

        final ActorEntity actorEntity = mapper.map(actorMock);
        assertThat(actorEntity.getActorId() , is(expectedActorId));
    }

    @Test
    public void correctlyMapActorEntityToActor(){
        String expectedName = "John";
        when(actorEntityMock.getActorName()).thenReturn("John");

        final Actor actor = mapper.reverseMap(actorEntityMock);
        assertNotNull(actor);
        assertThat(actor.getName() , is(expectedName));
    }

    @Test
    public void correctlyMapListOfActorEntities(){
        int expectedCinemasCount = 3;
        doReturn(cinemaEntityIteratorMock).when(cinemaEntitiesMock).iterator();
        doReturn(expectedCinemasCount).when(cinemaEntitiesMock).size();
        doReturn(false).when(cinemaEntitiesMock).isEmpty();

        final List<CinemaEntity> entityList = mapper.mapCinemas(actorMock.getCinemas());
        entityList.add(new CinemaEntity());
        entityList.add(new CinemaEntity());
        entityList.add(new CinemaEntity());
        final Actor actor = mapper.mapDetailActor(actorEntityMock , entityList);
        assertNotNull(actor);
        assertNotNull(actor.getCinemas());
        assertFalse(actor.getCinemas().isEmpty());
        assertEquals(cinemaEntitiesMock.size() , actor.getCinemas().size());
    }

}
