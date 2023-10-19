package tn.dkSoft.MyTicket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.exceptions.EventNotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Tickets;
import tn.dkSoft.MyTicket.repository.TicketRepository;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
@Transactional
public class TicketService implements TicketServiceInterface {
    private final TicketRepository ticketRepository;
    private EventMapperImpl dtoMapper;
    @Autowired
    public TicketService(TicketRepository ticketRepository, EventMapperImpl dtoMapper) {
        this.ticketRepository = ticketRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public TicketsDto saveTickets(TicketsDto ticketsDto) {
        log.info("Saving new Tickets");
        Tickets tickets=dtoMapper.fromTicketDto (ticketsDto);
        if (ticketsDto.getEventId () != null) {
            Event event = dtoMapper.fromEventDto (ticketsDto.getEventId ());
            tickets.setEvent (event);
        }
        Tickets savedTickets = ticketRepository.save(tickets);
        return dtoMapper.fromTicket (savedTickets);
    }


    @Override
    public List<TicketsDto> listTickets() {
        List<Tickets> tickets = ticketRepository.findAll();
        List<TicketsDto> ticketsDtos = tickets.stream()
                .map(ticket-> dtoMapper.fromTicket (ticket))
                .collect( Collectors.toList());

        return ticketsDtos;
    }

    @Override
    public List<TicketsDto> searchTicket(String keyword) {
        List<Tickets> tickets=ticketRepository.searchTickets (keyword);
        List<TicketsDto> ticketsDtoList = tickets.stream().map(tick -> dtoMapper.fromTicket (tick)).collect(Collectors.toList());
        return ticketsDtoList;
    }

    @Override
    public TicketsDto getTickets(Long id) throws EventNotFoundException {
        Tickets tickets = ticketRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException ("Tickets Not found"));
        TicketsDto ticketsDto = dtoMapper.fromTicket (tickets);
        if (ticketsDto.getEventId () != null) {
            EventDto eventDto = dtoMapper.fromEvent (tickets.getEvent ());
            ticketsDto.setEventId (eventDto);
        }
        return dtoMapper.fromTicket (tickets);
    }

    @Override
    public TicketsDto updateTickets(TicketsDto ticketsDto) {
        log.info("Saving new Tickets");
        Tickets tickets=dtoMapper.fromTicketDto (ticketsDto);
        Tickets savedTickets = ticketRepository.save(tickets);
        return dtoMapper.fromTicket (savedTickets);
    }

    @Override
    public void deleteTickets(Long id){
        ticketRepository.deleteById(id);
    }
}



