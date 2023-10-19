package tn.dkSoft.MyTicket.service;

import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.exceptions.EventNotFoundException;

import java.util.List;

public interface TicketServiceInterface {
  TicketsDto saveTickets(TicketsDto ticketsDto);

        List<TicketsDto> listTickets();

        List<TicketsDto> searchTicket(String keyword);

        TicketsDto getTickets(Long id) throws EventNotFoundException;

        TicketsDto updateTickets(TicketsDto ticketsDto);

        void deleteTickets(Long id);
    }

