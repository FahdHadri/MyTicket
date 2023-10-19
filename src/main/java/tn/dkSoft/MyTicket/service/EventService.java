package tn.dkSoft.MyTicket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.exceptions.EventNotFoundException;
import tn.dkSoft.MyTicket.exceptions.SessionNotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.repository.EventRepository;
import tn.dkSoft.MyTicket.repository.SessionRepository;


import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
@Transactional
public class EventService implements EventServiceInterface {
    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;
    private final EventService eventService;
    private EventMapperImpl dtoMapper;
@Autowired
    public EventService(EventService eventService,
                        EventRepository eventRepository,
                        SessionRepository sessionRepository, EventMapperImpl dtoMapper) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
    this.sessionRepository = sessionRepository;
    this.dtoMapper = dtoMapper;
}

    @Override
    @Transactional
    public EventDto saveEvent(EventDto eventDto) {
        log.info("Saving new Event");

        // Convert EventDto to Event
        Event event = dtoMapper.fromEventDto(eventDto);

        if (eventDto.getSessionId() != null) {
            // Retrieve the Session entity based on the provided sessionId
            Session session = sessionRepository.findById(eventDto.getSessionId())
                    .orElseThrow(() -> new SessionNotFoundException("Session not found"));

            // Associate the Event with the retrieved Session
            event.setSession(session);
        }

        // Save the Event entity
        Event savedEvent = eventRepository.save(event);

        return dtoMapper.fromEvent(savedEvent);
    }



    @Override
    public List<EventDto> listEvent() {
        List<Event> events = eventRepository.findAll();
        List<EventDto> eventDtos = events.stream()
                .map(event -> dtoMapper.fromEvent (event))
                .collect( Collectors.toList());

        return eventDtos;
    }

    @Override
    public List<EventDto> searchEvent(String keyword) {
        List<Event> events=eventRepository.searchEvent (keyword);
        List<EventDto> eventDtoList = events.stream().map(ev -> dtoMapper.fromEvent (ev)).collect(Collectors.toList());
        return eventDtoList;
    }

    @Override
    public EventDto getEvent(Long id) throws EventNotFoundException {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event Not found"));
        EventDto eventDto = dtoMapper.fromEvent (event);
        if (eventDto.getSessionId () != null) {
            SessionDto sessionDto = dtoMapper.fromSession (event.getSession ());
            eventDto.getSessionId ();
        }
        return dtoMapper.fromEvent (event);
    }

    /**public EventDto updateEvent(EventDto eventDto) {
        log.info("Saving new Event");
        Event event=dtoMapper.fromEventDto (eventDto);
        Event savedEvent = eventRepository.save(event);
        return dtoMapper.fromEvent (savedEvent);
    }*/
    @Override
    public EventDto updateEvent(Long eventId, EventDto eventDto) {
        log.info("Updating Event with ID: " + eventId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + eventId + " not found"));

        Event updatedEvent = dtoMapper.fromEventDto(eventDto);

        if (eventDto.getSessionId () != null) {
            Session session = sessionRepository.findById(eventDto.getSessionId())
                    .orElseThrow(() -> new SessionNotFoundException("Session not found"));
            updatedEvent.setSession(session);
        }
        updatedEvent.setEventId(event.getEventId());
        BeanUtils.copyProperties(updatedEvent, event, "eventId");
        Event savedEvent = eventRepository.save(event);
        return dtoMapper.fromEvent(savedEvent);
    }
    @Override
    public void deleteEvent(Long id){
        eventRepository.deleteById(id);
    }
}



