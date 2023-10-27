package tn.dkSoft.MyTicket.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.service.EventService;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Service
@Validated
@Slf4j
public class EventController {
    private final EventService eventService;

    @PostMapping("/save")
    public ResponseEntity<EventDto> saveEvent(@RequestBody @Valid EventDto eventDto) {
        try {
            log.info("Saving new session: {}", eventDto);
            EventDto savedEvent = eventService.saveEvent(eventDto);
            return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException ( HttpStatus.INTERNAL_SERVER_ERROR, "Error saving event: " + e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventDto>> events() {
        try {
            log.info("Fetching all events");
            List<EventDto> eventList = eventService.listEvent();
            return new ResponseEntity<>(eventList, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching events: " + e.getMessage(), e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<EventDto>> searchEvent(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        try {
            log.info("Searching for events with keyword: {}", keyword);
            List<EventDto> searchResults = eventService.searchEvent("%" + keyword + "%");
            return new ResponseEntity<>(searchResults, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error searching for events: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable(name = "id") Long id) {
        try {
            log.info("Fetching event with ID: {}", id);
            EventDto event = eventService.getEvent(id);
            if (event != null) {
                return new ResponseEntity<>(event, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching event: " + e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody @Valid EventDto eventDto) {
        try {
            log.info("Updating event with ID: {}", id);
            eventDto.setEventId(id);
            EventDto updatedEvent = eventService.saveEvent(eventDto);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating event: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        try {
            log.info("Deleting event with ID: {}", id);
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting event: " + e.getMessage(), e);
        }
    }
}