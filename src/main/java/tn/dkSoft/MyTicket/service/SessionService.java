package tn.dkSoft.MyTicket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.SessionNotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.model.Venue;
import tn.dkSoft.MyTicket.repository.SessionRepository;
import tn.dkSoft.MyTicket.repository.VenueRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
public class SessionService implements SessionServiceInterface {

    private final SessionRepository sessionRepository;
    private final VenueRepository venueRepository;
    private EventMapperImpl dtoMapper;
    @Autowired
    public SessionService(SessionRepository sessionRepository, VenueRepository venueRepository, EventMapperImpl dtoMapper) {
        this.sessionRepository = sessionRepository;
        this.venueRepository = venueRepository;

        this.dtoMapper = dtoMapper;
    }
    @Override
    @Transactional
    public SessionDto saveSession(SessionDto sessionDto) {
        log.info("Saving new Session");

        // Convert SessionDto to Session
        Session session = dtoMapper.fromSessionDTO(sessionDto);

        if (sessionDto.getVenueId () != null) {
            // Convert VenueDto to Venue
            Venue venue = venueRepository.findById(sessionDto.getVenueId ())
                    .orElseThrow(() -> new SessionNotFoundException("Session not found"));

            // Associate the Session with the Venue
            session.setVenue(venue);
        }

        // Save the Session entity
        Session savedSession = sessionRepository.save(session);

        // Convert the saved Session back to SessionDto
        return dtoMapper.fromSession(savedSession);
    }




    @Override
    public List<SessionDto> listSessions() {
        List<Session> sessions = sessionRepository.findAll();
        List<SessionDto> sessionDtos = sessions.stream()
                .map(session -> dtoMapper.fromSession (session))
                .collect(Collectors.toList());
        /*
        List<SessionDto> sessionDtos=new ArrayList<>();
        for (Session session:sessions){
            SessionDto sessionDto=dtoMapper.fromSession(session);
            sessionDtos.add(sessionDto);
        }
        *
         */
        return sessionDtos;
    }

    @Override
    public List<SessionDto> searchSession(String keyword) {
        List<Session> sessions=sessionRepository.searchSession (keyword);
        List<SessionDto> sessionDtoList = sessions.stream().map(cust -> dtoMapper.fromSession (cust)).collect(Collectors.toList());
        return sessionDtoList;
    }

    @Override
    public SessionDto getSession(Long id) throws SessionNotFoundException {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new SessionNotFoundException("Session not found"));


        SessionDto sessionDto = dtoMapper.fromSession(session);

        if (session.getVenue() != null) {
            sessionDto.setVenueId(session.getVenue().getVenueId());
        }

        // Convert associated events to EventDto objects
        List<EventDto> eventDtos = session.getEvents()
                .stream()
                .map(event -> dtoMapper.fromEvent(event))
                .collect(Collectors.toList());

        // Set the list of associated EventDto objects in SessionDto
        sessionDto.setEventDtoList (eventDtos);

        return sessionDto;
    }

    @Override
    public SessionDto updateSession(SessionDto sessionDto) {
        log.info("Saving new Session");
        Session session=dtoMapper.fromSessionDTO (sessionDto);
        Session savedSession = sessionRepository.save(session);
        return dtoMapper.fromSession (savedSession);
    }
    @Override

    public void deleteSession(Long id){
        sessionRepository.deleteById(id);
    }
    }



