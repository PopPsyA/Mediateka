package com.ru.devit.mediateka.models.mapper;

import com.ru.devit.mediateka.UnitTest;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.models.network.ActorDetailResponse;
import com.ru.devit.mediateka.models.network.ActorNetwork;
import com.ru.devit.mediateka.models.network.ActorResponse;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ActorDetailResponseToActorTest extends UnitTest {

    @Mock private ActorDetailResponse responseDetailMock;
    @Mock private ActorResponse responseMock;
    @Mock private List<ActorNetwork> actorNetworkListMock;

    private ActorDetailResponseToActor mapper;

    @Override
    protected void onSetUp() {
        mapper = new ActorDetailResponseToActor();
    }

    @Test
    public void shouldMapId() {
        when(responseDetailMock.getId()).thenReturn(22);

        Actor actor = mapper.map(responseDetailMock);
        assertThat(actor.getActorId() , is(22));
    }

    @Test
    public void shouldMapListOfActor() {
        List<ActorNetwork> spyListOfActors = spy(new ArrayList<>());
        spyListOfActors.add(new ActorNetwork());
        doReturn(1).when(actorNetworkListMock).size();
        doReturn(spyListOfActors).when(responseMock).getActors();

        final List<Actor> actorList = mapper.map(responseMock);
        assertNotNull(actorList);
        assertFalse(actorList.isEmpty());
        assertEquals(responseMock.getActors().size() , actorList.size());
    }
}