package tn.dkSoft.MyTicket.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.repository.EventRepository;
import tn.dkSoft.MyTicket.repository.SessionRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EventService implements EventServiceInterface {
    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public EventDto saveEvent(EventDto eventDto) {
        log.info("Saving new Event");
        Event event = EventMapperImpl.fromEventDto(eventDto);
        if (eventDto.getSessionId() != null) {
            Session session =
                    sessionRepository
                            .findById(eventDto.getSessionId())
                            .orElseThrow(() -> new NotFoundException("Session not found"));
            event.setSession(session);
            session.getEvents().add(event);
            event = eventRepository.save(event);
        } else {
            event.setSession(null);
            event = eventRepository.save(event);
        }

        // Convert the updated Event back to EventDto
        EventDto newEventDto = EventMapperImpl.fromEvent(event);
        return newEventDto;
    }

    @Override
    public List<EventDto> listEvent() {
        List<Event> events = eventRepository.findAll();

        return events.stream().map(EventMapperImpl::fromEvent).collect(Collectors.toList());
    }

    @Override
    public List<EventDto> searchEvent(String keyword) {
        List<Event> events = eventRepository.searchEvent(keyword);
        return events.stream().map(EventMapperImpl::fromEvent).collect(Collectors.toList());
    }

    @Override
    public EventDto getEvent(Long id) throws NotFoundException {
        Event event =
                eventRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("Event Not found"));
        EventDto eventDto = EventMapperImpl.fromEvent(event);
        if (event.getSession() != null) {
            Long sessionId = event.getSession().getSessionId();
            eventDto.setSessionId(sessionId);
        } else {
            eventDto.setSessionId(null);
        }
        return eventDto;
    }

    /**
     * public EventDto updateEvent(EventDto eventDto) {
     * log.info("Saving new Event");
     * Event event=EventMapperImpl.fromEventDto (eventDto);
     * Event savedEvent = eventRepository.save(event);
     * return EventMapperImpl.fromEvent (savedEvent);
     * }
     */
    @Override
    public EventDto updateEvent(Long eventId, EventDto eventDto) {
        log.info("Updating Event with ID: " + eventId);
        Event event =
                eventRepository
                        .findById(eventId)
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                "Event with ID " + eventId + " not found"));

        Event updatedEvent = EventMapperImpl.fromEventDto(eventDto);

        if (eventDto.getSessionId() != null) {
            Session session =
                    sessionRepository
                            .findById(eventDto.getSessionId())
                            .orElseThrow(() -> new NotFoundException("Session not found"));
            updatedEvent.setSession(session);
        }
        updatedEvent.setEventId(event.getEventId());
        BeanUtils.copyProperties(updatedEvent, event, "eventId");
        Event savedEvent = eventRepository.save(event);
        return EventMapperImpl.fromEvent(savedEvent);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
