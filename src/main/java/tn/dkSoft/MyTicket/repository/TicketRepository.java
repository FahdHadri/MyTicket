package tn.dkSoft.MyTicket.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.dkSoft.MyTicket.enums.TicketCategory;
import tn.dkSoft.MyTicket.model.Tickets;

public interface TicketRepository extends JpaRepository<Tickets, Long> {
    @Query("SELECT t FROM Tickets t WHERE t.ticketCat = :category")
    List<Tickets> searchTicketsByCategory(@Param("category") TicketCategory category);

    @Query("SELECT t FROM Tickets t WHERE t.ticketCat = :category AND t.event.eventId = :eventId")
    List<Tickets> searchTicketsByEventIdAndCategory(@Param("eventId") Long eventId, @Param("category") TicketCategory category);

}


