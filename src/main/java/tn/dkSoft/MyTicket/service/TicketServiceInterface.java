package tn.dkSoft.MyTicket.service;

import java.util.List;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.exceptions.EventNotFoundException;

public interface TicketServiceInterface {
    TicketsDto saveTickets(TicketsDto ticketsDto);

    List<TicketsDto> listTickets();

    List<TicketsDto> searchTicket(String keyword);

    TicketsDto getTickets(Long id) throws EventNotFoundException;

    TicketsDto updateTickets(TicketsDto ticketsDto);

    void deleteTickets(Long id);
}
