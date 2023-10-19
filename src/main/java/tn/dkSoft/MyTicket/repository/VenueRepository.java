package tn.dkSoft.MyTicket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.model.Venue;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
