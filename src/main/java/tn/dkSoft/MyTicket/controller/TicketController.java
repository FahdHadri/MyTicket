package tn.dkSoft.MyTicket.controller;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.enums.TicketCategory;
import tn.dkSoft.MyTicket.service.TicketService;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
@Lazy
@Service
@Slf4j
public class TicketController {
    private final TicketService ticketsService;
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @PostMapping("/save")
    public ResponseEntity<TicketsDto> saveTicket(@RequestBody TicketsDto ticketsDto) {
        try {
            logger.info("Saving ticket: {}", ticketsDto);
            TicketsDto savedTicket = ticketsService.saveTickets(ticketsDto);
            logger.info("Ticket saved: {}", savedTicket);
            return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving ticket: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving ticket: " + e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketsDto>> tickets() {
        try {
            logger.info("Fetching all tickets");
            List<TicketsDto> ticketList = ticketsService.listTickets();
            logger.info("Fetched {} tickets", ticketList.size());
            return new ResponseEntity<>(ticketList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching tickets: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching tickets: " + e.getMessage(), e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TicketsDto>> searchTicket(
            @RequestParam(name = "category", required = false) TicketCategory category) {
        try {
            logger.info("Searching for tickets by category: {}", category);
            List<TicketsDto> searchResults;

            if (category != null) {
                searchResults = ticketsService.searchTicketByCategory(category);
            } else {
                searchResults = Collections.emptyList();
            }

            logger.info("Found {} tickets", searchResults.size());
            return new ResponseEntity<>(searchResults, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error searching for tickets: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error searching for tickets: " + e.getMessage(), e);
        }
    }

    @GetMapping("/search2")
    public ResponseEntity<List<TicketsDto>> searchTicketByEvent(
            @RequestParam(name = "category", required = false) TicketCategory category,
            @RequestParam(name = "eventId", required = false) Long eventId) {
        try {
            logger.info("Searching for tickets by event: Event ID: {}, Category: {}", eventId, category);
            List<TicketsDto> searchResults;

            if (category != null && eventId != null) {
                searchResults = ticketsService.searchTicketsByEventIdAndCategory(eventId, category);
            } else {
                searchResults = Collections.emptyList();
            }

            logger.info("Found {} tickets", searchResults.size());
            return new ResponseEntity<>(searchResults, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error searching for tickets by event: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error searching for tickets by event: " + e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketsDto> updateTicket(@PathVariable Long id, @RequestBody TicketsDto ticketsDto) {
        try {
            logger.info("Updating ticket: ID: {}, Ticket: {}", id, ticketsDto);
            ticketsDto.setId(id);
            TicketsDto updatedTicket = ticketsService.saveTickets(ticketsDto);
            logger.info("Ticket updated: ID: {}, Ticket: {}", id, updatedTicket);
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating ticket: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating ticket: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        try {
            logger.info("Deleting ticket: ID: {}", id);
            ticketsService.deleteTickets(id);
            logger.info("Ticket deleted: ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting ticket: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting ticket: " + e.getMessage(), e);
        }
    }
}