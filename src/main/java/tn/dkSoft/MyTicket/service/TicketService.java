package tn.dkSoft.MyTicket.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Tickets;
import tn.dkSoft.MyTicket.repository.EventRepository;
import tn.dkSoft.MyTicket.repository.TicketRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TicketService implements TicketServiceInterface {
    private final EventRepository eventRepository;

    private final TicketRepository ticketRepository;

    @Override
    public TicketsDto saveTickets(TicketsDto ticketsDto) {
        log.info("Saving new Ticket");
        Tickets tickets = EventMapperImpl.fromTicketDto(ticketsDto);
        if (ticketsDto.getEventId() != null) {
            Event event =
                    eventRepository
                            .findById(ticketsDto.getEventId())
                            .orElseThrow(() -> new NotFoundException("Event not found"));
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
    public TicketsDto getTickets(Long id) throws NotFoundException {
        Tickets tickets =
                ticketRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("Tickets Not found"));
        TicketsDto ticketsDto = EventMapperImpl.fromTicket(tickets);
        if (tickets.getEvent() != null) {
            EventDto eventDto = EventMapperImpl.fromEvent(tickets.getEvent());
            ticketsDto.setEventId(eventDto.getEventId());
        }
        return ticketsDto;
    }

    @Override
    public TicketsDto updateTickets(TicketsDto ticketsDto) throws NotFoundException {
        log.info("Updating Tickets");
        Tickets ticket =
                ticketRepository
                        .findById(ticketsDto.getId())
                        .orElseThrow(() -> new NotFoundException("Ticket Not found"));
        ticket.setCode_ticket(ticketsDto.getCodeTicket());
        ticket.setTicketCat(ticketsDto.getTicketCat());
        ticket.setPrice(ticketsDto.getPrice());
        ticket.setQuantity(ticketsDto.getQuantity());
        ticket.setImgUrlTicket(ticketsDto.getImgUrlTicket());
        Tickets updatedTicket = ticketRepository.save(ticket);
        return EventMapperImpl.fromTicket(updatedTicket);
    }

    @Override
    public void deleteTickets(Long id) {
        ticketRepository.deleteById(id);
    }
}
