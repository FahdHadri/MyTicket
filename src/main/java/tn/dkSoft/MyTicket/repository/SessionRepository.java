package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.dkSoft.MyTicket.model.Session;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
    @Query("select s from Session s where s.sessionName like :kw")
    List<Session> searchSession(@Param("kw") String keyword);
}
