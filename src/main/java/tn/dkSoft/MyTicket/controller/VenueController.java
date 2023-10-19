package tn.dkSoft.MyTicket.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.VenueNotFoundException;
import tn.dkSoft.MyTicket.service.VenueService;

import java.util.List;

@RestController
@RequestMapping("/venue")
@Lazy
@Service
public class VenueController {

    private VenueService venueService;

    @PostMapping("/save")
    public VenueDto saveVenue(@RequestBody VenueDto venueDto) {

        return venueService.saveVenue ( venueDto );
    }

    @GetMapping("/all")
    public List<VenueDto> venues() {
        return venueService.listVenue ();
    }



    @GetMapping("/{id}")
    public VenueDto getVenue(@PathVariable(name = "id") Long id) throws VenueNotFoundException {
        return venueService.getVenue ( id );
    }

    @PutMapping("/{id}")
    public VenueDto updateVenue(@PathVariable Long id, @RequestBody VenueDto venueDto){
        venueDto.setVenueId (id);
        return venueService.updateVenue (venueDto);
    }
    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id){
        venueService.deleteVenue (id);
    }
}

