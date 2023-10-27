package tn.dkSoft.MyTicket.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.service.SessionService;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Lazy
@Service
@Validated
@Slf4j
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/save")
    public ResponseEntity<SessionDto> saveSession(@RequestBody @Valid SessionDto sessionDto) {
        try {
            log.info("Saving new session: {}", sessionDto);
            SessionDto savedSession = sessionService.saveSession(sessionDto);
            return new ResponseEntity<>(savedSession, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException (HttpStatus.INTERNAL_SERVER_ERROR, "Error saving session: " + e.getMessage(), e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<SessionDto>> sessions() {
        try {
            log.info("Fetching all sessions");
            List<SessionDto> sessionList = sessionService.listSessions();
            return new ResponseEntity<>(sessionList, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching sessions: " + e.getMessage(), e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<SessionDto>> searchSessions(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        try {
            log.info("Searching for sessions with keyword: {}", keyword);
            List<SessionDto> searchResults = sessionService.searchSession("%" + keyword + "%");
            return new ResponseEntity<>(searchResults, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error searching for sessions: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSession(@PathVariable(name = "id") Long id) {
        try {
            log.info("Fetching session with ID: {}", id);
            SessionDto session = sessionService.getSession(id);
            if (session != null) {
                return new ResponseEntity<>(session, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching session: " + e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionDto> UpdateSession(@PathVariable Long id, @RequestBody @Valid SessionDto sessionDto) {
        try {
            log.info("Updating session with ID: {}", id);
            sessionDto.setSessionId(id);
            SessionDto updatedSession = sessionService.saveSession(sessionDto);
            return new ResponseEntity<>(updatedSession, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating session: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        try {
            log.info("Deleting session with ID: {}", id);
            sessionService.deleteSession(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting session: " + e.getMessage(), e);
        }
    }
}