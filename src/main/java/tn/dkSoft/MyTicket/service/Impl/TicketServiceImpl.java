package tn.dkSoft.MyTicket.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.enums.TicketCategory;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.model.Tickets;
import tn.dkSoft.MyTicket.model.Venue;
import tn.dkSoft.MyTicket.repository.EventRepository;
import tn.dkSoft.MyTicket.repository.TicketRepository;
import tn.dkSoft.MyTicket.service.TicketService;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {
    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    public TicketsDto saveTickets(@Valid TicketsDto ticketsDto) {
        log.info("Saving new Ticket: {}", ticketsDto);
        Tickets tickets = EventMapperImpl.fromTicketDto(ticketsDto);
        Long eventId = ticketsDto.getEventId().getEventId();
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        tickets.setEvent(event);
        Tickets savedTickets = ticketRepository.save(tickets);
        TicketsDto newTicket = EventMapperImpl.fromTicket(savedTickets);
        EventDto eventDto = EventMapperImpl.fromEvent(savedTickets.getEvent());
        SessionDto sessionDto = new SessionDto();
        Session session = savedTickets.getEvent().getSession();
        if (session != null) {
            sessionDto = EventMapperImpl.fromSession(session);
            Venue venue = session.getVenueId ();
            if (venue != null) {
                VenueDto venueDto = EventMapperImpl.fromVenue(venue);
                sessionDto.setVenue(venueDto);
            }
        }
        eventDto.setSessionDto(sessionDto);
        newTicket.setEventId(eventDto);
        log.info("Ticket saved: {}", newTicket);
        return newTicket;
    }




    public List<TicketsDto> listTickets() {
        log.info("Listing all tickets with details");

        List<Tickets> ticketsList = ticketRepository.findAll();
        List<TicketsDto> ticketsDtoList = ticketsList.stream()
                .map(ticket -> {
                    TicketsDto ticketsDto = EventMapperImpl.fromTicket(ticket);
                    EventDto eventDto = EventMapperImpl.fromEvent(ticket.getEvent());
                    SessionDto sessionDto = EventMapperImpl.fromSession(ticket.getEvent().getSession());
                    Venue venue = ticket.getEvent().getSession().getVenueId ();
                    if (venue != null) {
                        VenueDto venueDto = EventMapperImpl.fromVenue(venue);
                        sessionDto.setVenue(venueDto);
                    }
                    eventDto.setSessionDto(sessionDto);
                    ticketsDto.setEventId(eventDto);
                    return ticketsDto;
                })
                .collect(Collectors.toList());

        return ticketsDtoList;
    }



@Override
    public List<TicketsDto> searchTicketByCategory(TicketCategory category) {
        log.info("Searching for tickets with category: {}", category);
        List<Tickets> tickets = ticketRepository.searchTicketsByCategory(category);

        List<TicketsDto> ticketsDtoList = tickets.stream()
                .map(ticket -> {
                    TicketsDto ticketsDto = EventMapperImpl.fromTicket(ticket);
                    EventDto eventDto = EventMapperImpl.fromEvent(ticket.getEvent());
                    SessionDto sessionDto = EventMapperImpl.fromSession(ticket.getEvent().getSession());
                    Venue venue = ticket.getEvent().getSession().getVenueId();
                    if (venue != null) {
                        VenueDto venueDto = EventMapperImpl.fromVenue(venue);
                        sessionDto.setVenue(venueDto);
                    }
                    eventDto.setSessionDto(sessionDto);
                    ticketsDto.setEventId(eventDto);
                    return ticketsDto;
                })
                .collect(Collectors.toList());

        return ticketsDtoList;
    }

    @Override
    public List<TicketsDto> searchTicketsByEventIdAndCategory(Long eventId, TicketCategory category) {
        log.info("Searching for tickets with eventId: {} and category: {}", eventId, category);
        List<Tickets> tickets = ticketRepository.searchTicketsByEventIdAndCategory(eventId, category);

        List<TicketsDto> ticketsDtoList = tickets.stream()
                .map(ticket -> {
                    TicketsDto ticketsDto = EventMapperImpl.fromTicket(ticket);
                    EventDto eventDto = EventMapperImpl.fromEvent(ticket.getEvent());
                    SessionDto sessionDto = EventMapperImpl.fromSession(ticket.getEvent().getSession());
                    Venue venue = ticket.getEvent().getSession().getVenueId();
                    if (venue != null) {
                        VenueDto venueDto = EventMapperImpl.fromVenue(venue);
                        sessionDto.setVenue(venueDto);
                    }
                    eventDto.setSessionDto(sessionDto);
                    ticketsDto.setEventId(eventDto);
                    return ticketsDto;
                })
                .collect(Collectors.toList());

        return ticketsDtoList;
    }

/*
    public TicketsDto getTickets(Long id) throws NotFoundException {
        log.info ( "Fetching ticket with ID: {}", id );
        Tickets tickets = ticketRepository.findById ( id )
                .orElseThrow ( () -> new NotFoundException ( "Tickets not found" ) );
        TicketsDto ticketsDto = EventMapperImpl.fromTicket ( tickets );
        EventDto eventDto = EventMapperImpl.fromEvent ( tickets.getEvent () );
        SessionDto sessionDto = EventMapperImpl.fromSession ( tickets.getEvent ().getSession () );
        if (tickets.getEvent () != null
                && tickets.getEvent ().getSession ()!= null
                && tickets.getEvent ().getSession ().getVenueId () != null ) {
            VenueDto venueDto = EventMapperImpl.fromVenue ( tickets.getEvent ().getSession ().getVenueId () );
            eventDto.getSessionDto ().setVenue ( venueDto );
        }
        ticketsDto.setEventId ( eventDto );
        return ticketsDto;
    }



    public TicketsDto updateTickets(@Valid TicketsDto ticketsDto) throws NotFoundException {
        log.info ( "Updating Tickets: {}", ticketsDto.getId () );
        Tickets ticket = ticketRepository.findById ( ticketsDto.getId () )
                .orElseThrow ( () -> new NotFoundException ( "Ticket not found" ) );
        BeanUtils.copyProperties ( ticketsDto, ticket );
        Tickets updatedTicket = ticketRepository.save ( ticket );
        TicketsDto updatedTicketsDto = EventMapperImpl.fromTicket ( updatedTicket );
        log.info ( "Ticket updated: {}", updatedTicketsDto );
        return updatedTicketsDto;
    }
*/
    @Override
    public void deleteTickets(Long id) {
        log.info ( "Deleting ticket with ID: {}", id );
        ticketRepository.deleteById ( id );
    }
}
