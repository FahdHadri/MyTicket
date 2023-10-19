package tn.dkSoft.MyTicket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.VenueNotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Venue;
import tn.dkSoft.MyTicket.repository.VenueRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class VenueService implements VenueServiceInterface {

    private final VenueRepository venueRepository;
    private EventMapperImpl dtoMapper;



    @Autowired
    public VenueService(VenueRepository venueRepository, EventMapperImpl dtoMapper) {
        this.venueRepository = venueRepository;
        this.dtoMapper = dtoMapper;
    }


    @Override
    public VenueDto saveVenue(VenueDto venueDto) {
        log.info("Saving new Venue");
        Venue venue=dtoMapper.fromVenueDTO (venueDto);
        Venue savedVenue = venueRepository.save(venue);
        return dtoMapper.fromVenue (savedVenue);
    }


    @Override
    public List<VenueDto> listVenue() {
        List<Venue> venues = venueRepository.findAll();
        List<VenueDto> venueDtos = venues.stream()
                .map(venue -> dtoMapper.fromVenue (venue))
                .collect( Collectors.toList());
        return venueDtos;
    }



    @Override
    public VenueDto getVenue(Long id) throws VenueNotFoundException {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException("Venue Not found"));
        return dtoMapper.fromVenue (venue);
    }

    @Override
    public VenueDto updateVenue(VenueDto venueDto) {
        log.info("Saving new Venue");
        Venue venue=dtoMapper.fromVenueDTO (venueDto);
        Venue savedVenue = venueRepository.save(venue);
        return dtoMapper.fromVenue (savedVenue);
    }

    @Override
    public void deleteVenue(Long id){
        venueRepository.deleteById(id);
    }
}
