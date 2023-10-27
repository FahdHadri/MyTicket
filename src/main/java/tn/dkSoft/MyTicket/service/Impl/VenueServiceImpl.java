package tn.dkSoft.MyTicket.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Venue;
import tn.dkSoft.MyTicket.repository.VenueRepository;
import tn.dkSoft.MyTicket.service.VenueService;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@Validated
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    public VenueDto saveVenue(@Valid VenueDto venueDto) {
        log.info("Saving new Venue: {}", venueDto);
        Venue venue = EventMapperImpl.fromVenueDTO(venueDto);
        Venue savedVenue = venueRepository.save(venue);
        return EventMapperImpl.fromVenue(savedVenue);
    }

    @Override
    public List<VenueDto> listVenue() {
        log.info("Listing all venues");
        List<Venue> venues = venueRepository.findAll();
        return venues.stream().map(EventMapperImpl::fromVenue).collect(Collectors.toList());
    }

    @Override
    public VenueDto getVenue(Long id) throws NotFoundException {
        log.info("Fetching venue with ID: {}", id);
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue Not found"));
        return EventMapperImpl.fromVenue(venue);
    }

    @Override
    public VenueDto updateVenue(@Valid VenueDto venueDto) {
        log.info("Updating Venue: {}", venueDto);
        Venue venue = EventMapperImpl.fromVenueDTO(venueDto);
        Venue savedVenue = venueRepository.save(venue);
        return EventMapperImpl.fromVenue(savedVenue);
    }

    @Override
    public void deleteVenue(Long id) {
        log.info("Deleting venue with ID: {}", id);
        venueRepository.deleteById(id);
    }
}