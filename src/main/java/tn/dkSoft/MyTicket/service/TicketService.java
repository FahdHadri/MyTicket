package tn.dkSoft.MyTicket.service;

import java.util.List;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.enums.TicketCategory;

public interface TicketService {
    TicketsDto saveTickets(TicketsDto ticketsDto);

    List<TicketsDto> listTickets();

    List<TicketsDto> searchTicketByCategory(TicketCategory category);

    List<TicketsDto> searchTicketsByEventIdAndCategory(Long eventId, TicketCategory category);

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
    void deleteTickets(Long id);


    //  TicketsDto getTickets(Long id) throws NotFoundException;

    //TicketsDto updateTickets(TicketsDto ticketsDto);


}
