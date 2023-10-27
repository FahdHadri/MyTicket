package tn.dkSoft.MyTicket.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.service.VenueService;

@RestController
@RequestMapping("/venue")
@RequiredArgsConstructor
@Validated
@Service
@Slf4j
public class VenueController {
    private final VenueService venueService;
    private static final Logger logger = LoggerFactory.getLogger(VenueController.class);

    @PostMapping("/save")
    public ResponseEntity<VenueDto> saveVenue(@RequestBody @Valid VenueDto venueDto) {
        try {
            logger.info("Saving new venue: {}", venueDto);
            VenueDto savedVenue = venueService.saveVenue(venueDto);
            return new ResponseEntity<>(savedVenue, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving venue: " + e.getMessage(), e);
            throw new ResponseStatusException (HttpStatus.INTERNAL_SERVER_ERROR, "Error saving venue: " + e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<VenueDto>> venues() {
        try {
            logger.info("Fetching all venues");
            List<VenueDto> venueList = venueService.listVenue();
            return new ResponseEntity<>(venueList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching venues: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching venues: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDto> getVenue(@PathVariable(name = "id") Long id) {
        try {
            logger.info("Fetching venue with ID: {}", id);
            VenueDto venue = venueService.getVenue(id);
            if (venue != null) {
                return new ResponseEntity<>(venue, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching venue: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching venue: " + e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueDto> updateVenue(@PathVariable Long id, @RequestBody @Valid VenueDto venueDto) {
        try {
            logger.info("Updating venue with ID: {}", id);
            venueDto.setVenueId(id);
            VenueDto updatedVenue = venueService.updateVenue(venueDto);
            return new ResponseEntity<>(updatedVenue, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating venue: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating venue: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        try {
            logger.info("Deleting venue with ID: {}", id);
            venueService.deleteVenue(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting venue: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting venue: " + e.getMessage(), e);
        }
    }
}
