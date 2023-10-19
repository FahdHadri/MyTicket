package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Session;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    @Query("select e from Event e where e.description like :kw")
    List<Event> searchEvent(@Param("kw") String keyword);
}
