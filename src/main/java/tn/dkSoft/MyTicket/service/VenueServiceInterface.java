package tn.dkSoft.MyTicket.service;

import java.util.List;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;

public interface VenueServiceInterface {
    VenueDto saveVenue(VenueDto venueDto);

    List<VenueDto> listVenue();

    VenueDto getVenue(Long id) throws NotFoundException;

    VenueDto updateVenue(VenueDto venueDto);

    void deleteVenue(Long id);
}
