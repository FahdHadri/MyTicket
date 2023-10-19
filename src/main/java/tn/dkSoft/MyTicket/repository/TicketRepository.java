package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.model.Tickets;

import java.util.List;

public interface TicketRepository extends JpaRepository<Tickets,Long> {
    @Query("select t from Tickets t where t.ticketCat like :kw")
    List<Tickets> searchTickets(@Param("kw") String keyword);


}
