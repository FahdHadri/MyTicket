package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.dkSoft.MyTicket.model.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {}
