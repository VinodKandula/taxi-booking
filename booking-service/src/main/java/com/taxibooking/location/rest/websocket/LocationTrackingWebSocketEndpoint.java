package com.taxibooking.location.rest.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taxibooking.booking.util.DataMapper;
import com.taxibooking.location.track.LocationTrackingObserver;
import com.taxibooking.location.track.Subject;
import com.taxibooking.location.track.TaxiLocationEventDto;

/**
 * Web socket (bidirectional endpoint) for broadcasting location events to all
 * connected clients.
 *
 * @author vinodkandula
 */
@Singleton
@ServerEndpoint(value = "/ws/v1/locations/taxi/all")
public class LocationTrackingWebSocketEndpoint implements LocationTrackingObserver {

    private static final Logger LOGGER = Logger.getLogger(
            LocationTrackingWebSocketEndpoint.class.getName());

    private static final Set<Session> observers = Collections.synchronizedSet(new HashSet<Session>());

    @Inject
    private Subject locationTrackingService;

    @PostConstruct
    void init() {
        this.locationTrackingService.registerObserver(this);
    }

    @OnOpen
    public void onOpen(Session session) {
        observers.add(session);
    }

    @OnClose
    public void onClose(Session session) {

        LOGGER.log(Level.FINE, "Removing client {0}", session.getId());

        Iterator<Session> iterator = observers.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(session.getId())) {
                iterator.remove();
                break;
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // Empty - communication one way. Server to client.
    }

    @OnError
    public void onError(Throwable t) {
        LOGGER.log(Level.WARNING, t.getMessage());
    }

    @Override
    public void update(Object obj) {
        for (Session o : LocationTrackingWebSocketEndpoint.observers) {
            if (o.isOpen()) {
                if (obj instanceof TaxiLocationEventDto) {
                    try {
                        TaxiLocationEventDto e = (TaxiLocationEventDto) obj;
                        o.getAsyncRemote().sendObject(DataMapper.getInstance().writeValueAsString(e));
                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(LocationTrackingWebSocketEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
