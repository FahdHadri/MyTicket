package tn.dkSoft.MyTicket.service;

import java.util.List;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.VenueNotFoundException;

public interface VenueServiceInterface {
    VenueDto saveVenue(VenueDto venueDto);

    List<VenueDto> listVenue();

    VenueDto getVenue(Long id) throws VenueNotFoundException;

    VenueDto updateVenue(VenueDto venueDto);

    void deleteVenue(Long id);
}
