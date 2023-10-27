package tn.dkSoft.MyTicket.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.repository.EventRepository;
import tn.dkSoft.MyTicket.repository.SessionRepository;
import tn.dkSoft.MyTicket.repository.VenueRepository;
import tn.dkSoft.MyTicket.service.EventService;

@Service
@Slf4j
@Transactional
@Validated
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final VenueRepository venueRepository;
    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;


    @Override
    public EventDto saveEvent(@Valid EventDto eventDto) {
        log.info("Saving new Event: {}", eventDto);
        Event event = EventMapperImpl.fromEventDto(eventDto);
        Long sessionId = eventDto.getSessionDto().getSessionId ();
        if (sessionId != null) {
            Session session = sessionRepository
                    .findById(sessionId)
                    .orElseThrow(() -> new NotFoundException("Session not found"));
            event.setSession(session);
        }
        Event savedEvent = eventRepository.save(event);
        EventDto newEvent = EventMapperImpl.fromEvent(savedEvent);
        SessionDto sessionDto = EventMapperImpl.fromSession (savedEvent.getSession ());
        VenueDto venueDto = EventMapperImpl.fromVenue(savedEvent.getSession ().getVenueId () );
        sessionDto.setVenue(venueDto);
        newEvent.setSessionDto ( sessionDto );


        log.info("Event saved: {}", newEvent);
        return newEvent;
    }

    @Override
    public List<EventDto> listEvent() {
        log.info("Listing all events");
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(event -> {
                    EventDto eventDto = EventMapperImpl.fromEvent(event);
                    SessionDto sessionDto = EventMapperImpl.fromSession(event.getSession());
                    if (event.getSession() != null && event.getSession().getVenueId () != null) {
                        VenueDto venueDto = EventMapperImpl.fromVenue(event.getSession().getVenueId ());
                        sessionDto.setVenue(venueDto);
                    }
                    eventDto.setSessionDto(sessionDto);
                    return eventDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<EventDto> searchEvent(String keyword) {
        log.info("Searching for events with keyword: {}", keyword);
        List<Event> events = eventRepository.searchEvent(keyword);
         return events.stream()
                .map(event -> {
                    EventDto eventDto = EventMapperImpl.fromEvent(event);
                    SessionDto sessionDto = EventMapperImpl.fromSession(event.getSession());
                    if (event.getSession() != null && event.getSession().getVenueId () != null) {
                        VenueDto venueDto = EventMapperImpl.fromVenue(event.getSession().getVenueId ());
                        sessionDto.setVenue(venueDto);
                    }
                    eventDto.setSessionDto(sessionDto);

                    return eventDto;
                })
                .collect(Collectors.toList());
    }



    @Override
    public EventDto getEvent(Long id) throws NotFoundException {
        log.info("Fetching event with ID: {}", id);
        Event event = eventRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        EventDto eventDto = EventMapperImpl.fromEvent(event);
        SessionDto sessionDto = EventMapperImpl.fromSession(event.getSession());
        if (event.getSession() != null && event.getSession().getVenueId () != null) {
            VenueDto venueDto = EventMapperImpl.fromVenue(event.getSession().getVenueId ());
            sessionDto.setVenue(venueDto);
        }
        eventDto.setSessionDto(sessionDto);

        return eventDto;
    }

  /*  @Override
    public EventDto updateEvent(@Valid EventDto eventDto) {
        log.info("Updating Event with ID: {}", eventDto);
        Event event = EventMapperImpl.fromEventDto(eventDto);

        Long sessionId = eventDto.getSessionId();
        if (sessionId != null) {
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new NotFoundException("Session not found"));
            event.setSession(session);
            eventDto.setSessionId(session.getSessionId());
        } else {
            event.setSession(null);
        }

        Event savedEvent = eventRepository.save(event);
        EventDto savedEventDto = EventMapperImpl.fromEvent(savedEvent);

        log.info("Event updated: {}", savedEventDto);
        return savedEventDto;
    }*/

    @Override
    public void deleteEvent(Long id) {
        log.info("Deleting event with ID: {}", id);
        eventRepository.deleteById(id);
    }
}
