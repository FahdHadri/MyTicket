package tn.dkSoft.MyTicket.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Venue;
import tn.dkSoft.MyTicket.repository.VenueRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class VenueService implements VenueServiceInterface {

    private final VenueRepository venueRepository;

    @Override
    public VenueDto saveVenue(VenueDto venueDto) {
        log.info("Saving new Venue");
        Venue venue = EventMapperImpl.fromVenueDTO(venueDto);
        Venue savedVenue = venueRepository.save(venue);
        return EventMapperImpl.fromVenue(savedVenue);
    }

    @Override
    public List<VenueDto> listVenue() {
        List<Venue> venues = venueRepository.findAll();
        return venues.stream().map(EventMapperImpl::fromVenue).collect(Collectors.toList());
    }

    @Override
    public VenueDto getVenue(Long id) throws NotFoundException {
        Venue venue =
                venueRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("Venue Not found"));
        return EventMapperImpl.fromVenue(venue);
    }

    @Override
    public VenueDto updateVenue(VenueDto venueDto) {
        log.info("Saving new Venue");
        Venue venue = EventMapperImpl.fromVenueDTO(venueDto);
        Venue savedVenue = venueRepository.save(venue);
        return EventMapperImpl.fromVenue(savedVenue);
    }

    @Override
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
}
