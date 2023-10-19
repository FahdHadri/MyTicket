package tn.dkSoft.MyTicket.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.exceptions.EventNotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Tickets;
import tn.dkSoft.MyTicket.repository.TicketRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TicketService implements TicketServiceInterface {

    private final TicketRepository ticketRepository;

    @Override
    public TicketsDto saveTickets(TicketsDto ticketsDto) {
        log.info("Saving new Tickets");
        Tickets tickets = EventMapperImpl.fromTicketDto(ticketsDto);
        if (ticketsDto.getEventId() != null) {
            Event event = EventMapperImpl.fromEventDto(ticketsDto.getEventId());
            tickets.setEvent(event);
        }
        Tickets savedTickets = ticketRepository.save(tickets);
        return EventMapperImpl.fromTicket(savedTickets);
    }

    @Override
    public List<TicketsDto> listTickets() {
        List<Tickets> tickets = ticketRepository.findAll();

        return tickets.stream().map(EventMapperImpl::fromTicket).collect(Collectors.toList());
    }

    @Override
    public List<TicketsDto> searchTicket(String keyword) {
        List<Tickets> tickets = ticketRepository.searchTickets(keyword);
        return tickets.stream().map(EventMapperImpl::fromTicket).collect(Collectors.toList());
    }

    @Override
    public TicketsDto getTickets(Long id) throws EventNotFoundException {
        Tickets tickets =
                ticketRepository
                        .findById(id)
                        .orElseThrow(() -> new EventNotFoundException("Tickets Not found"));
        TicketsDto ticketsDto = EventMapperImpl.fromTicket(tickets);
        if (ticketsDto.getEventId() != null) {
            EventDto eventDto = EventMapperImpl.fromEvent(tickets.getEvent());
            ticketsDto.setEventId(eventDto);
        }
        return EventMapperImpl.fromTicket(tickets);
    }

    @Override
    public TicketsDto updateTickets(TicketsDto ticketsDto) {
        log.info("Saving new Tickets");
        Tickets tickets = EventMapperImpl.fromTicketDto(ticketsDto);
        Tickets savedTickets = ticketRepository.save(tickets);
        return EventMapperImpl.fromTicket(savedTickets);
    }

    @Override
    public void deleteTickets(Long id) {
        ticketRepository.deleteById(id);
    }
}
