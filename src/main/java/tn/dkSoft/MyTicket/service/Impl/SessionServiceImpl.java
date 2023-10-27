package tn.dkSoft.MyTicket.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.model.Venue;
import tn.dkSoft.MyTicket.repository.SessionRepository;
import tn.dkSoft.MyTicket.repository.VenueRepository;
import tn.dkSoft.MyTicket.service.SessionService;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@Validated
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final VenueRepository venueRepository;

    @Override
    public SessionDto saveSession(@Valid SessionDto sessionDto) {
        log.info("Saving new Session: {}", sessionDto);
        Session session = EventMapperImpl.fromSessionDTO(sessionDto);

        Long venueId = sessionDto.getVenue().getVenueId();
        if (venueId != null) {
            Venue venue = venueRepository.findById(venueId)
                    .orElseThrow(() -> new NotFoundException("Venue not found"));
            session.setVenueId(venue);
        }
        Session savedSession = sessionRepository.save(session);
        SessionDto newSession = EventMapperImpl.fromSession(savedSession);
        VenueDto venueDto = EventMapperImpl.fromVenue(savedSession.getVenueId());
        newSession.setVenue(venueDto);

        log.info("Session saved: {}", newSession);
        return newSession;
    }

    @Override
    public List<SessionDto> listSessions() {
        log.info("Listing all sessions");
        List<Session> sessions = sessionRepository.findAll();

        return sessions.stream()
                .map(session -> {
                    SessionDto sessionDto = EventMapperImpl.fromSession(session);
                    sessionDto.setVenue(EventMapperImpl.fromVenue(session.getVenueId()));
                    return sessionDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SessionDto> searchSession(String keyword) {
        log.info("Searching for session with keyword: {}", keyword);
        List<Session> sessions = sessionRepository.searchSession(keyword);

        return sessions.stream()
                .map(session -> {
                    SessionDto sessionDto = EventMapperImpl.fromSession(session);
                    sessionDto.setVenue(EventMapperImpl.fromVenue(session.getVenueId()));
                    return sessionDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SessionDto getSession(Long id) throws NotFoundException {
        log.info("Fetching session with ID: {}", id);
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session not found"));
        SessionDto sessionDto = EventMapperImpl.fromSession(session);
        if (session.getVenueId() != null) {
            VenueDto venueDto = EventMapperImpl.fromVenue(session.getVenueId());
            sessionDto.setVenue(venueDto);
        }
        return sessionDto;
    }

/**    @Override
    public SessionDto updateSession(@Valid SessionDto sessionDto) {
        log.info("Updating Session: {}", sessionDto);

        Long sessionId = sessionDto.getSessionId();
        Session existingSession = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found"));
        existingSession.setSessionName(sessionDto.getSessionName());
        existingSession.setStartDate(sessionDto.getStartDate());
        existingSession.setFinishDate(sessionDto.getFinishDate());
        existingSession.setImgUrl(sessionDto.getImgUrl());

        if (sessionDto.getVenue() != null) {
            Long venueId = sessionDto.getVenue().getVenueId();
            Venue venue = venueRepository.findById(venueId)
                    .orElseThrow(() -> new NotFoundException("Venue not found"));
            existingSession.setVenueId(venue);
        } else {
            existingSession.setVenueId(null);
        }
        Session savedSession = sessionRepository.save(existingSession);
        SessionDto updatedSessionDto = EventMapperImpl.fromSession(savedSession);
        VenueDto venueDto = EventMapperImpl.fromVenue(savedSession.getVenueId());
        updatedSessionDto.setVenue(venueDto);

        log.info("Session updated: {}", savedSession);
        return updatedSessionDto;
    }*/



 @Override
    public void deleteSession(Long id) {
        log.info("Deleting session with ID: {}", id);
        sessionRepository.deleteById(id);
    }
}