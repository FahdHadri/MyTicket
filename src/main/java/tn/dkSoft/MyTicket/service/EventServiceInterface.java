package tn.dkSoft.MyTicket.service;

import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;

import java.util.List;

public interface EventServiceInterface {

    EventDto saveEvent(EventDto eventDto);

    List<EventDto> listEvent();

    List<EventDto> searchEvent(String keyword);

    EventDto getEvent(Long id) throws NotFoundException;

    EventDto updateEvent(Long eventId, EventDto eventDto);

    void deleteEvent(Long id);
}
