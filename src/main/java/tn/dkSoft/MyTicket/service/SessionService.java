package tn.dkSoft.MyTicket.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.model.Venue;
import tn.dkSoft.MyTicket.repository.SessionRepository;
import tn.dkSoft.MyTicket.repository.VenueRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SessionService implements SessionServiceInterface{

    private final SessionRepository sessionRepository;
    private final VenueRepository venueRepository;

    @Override
    @Transactional
    public SessionDto saveSession(SessionDto sessionDto) {
        log.info("Saving new Session");
        Session session = EventMapperImpl.fromSessionDTO(sessionDto);

        if (sessionDto.getVenueDto() != null) {

            Long venueId = sessionDto.getVenueDto().getVenueId();

            Venue venue =
                    venueRepository
                            .findById(venueId)
                            .orElseThrow(() -> new NotFoundException("Venue not found"));
            session.setVenue(venue);
        }

        Session savedSession = sessionRepository.save(session);

        return EventMapperImpl.fromSession(savedSession);
    }

    @Override
    public List<SessionDto> listSessions() {
        List<Session> sessions = sessionRepository.findAll();
        List<SessionDto> sessionDtoList =
                sessions.stream().map(EventMapperImpl::fromSession).collect(Collectors.toList());
        /*
        List<SessionDto> sessionDtos=new ArrayList<>();
        for (Session session:sessions){
            SessionDto sessionDto=dtoMapper.fromSession(session);
            sessionDtos.add(sessionDto);
        }
        *
         */
        return sessionDtoList;
    }

    @Override
    public List<SessionDto> searchSession(String keyword) {
        List<Session> sessions = sessionRepository.searchSession(keyword);
        List<SessionDto> sessionDtoList =
                sessions.stream().map(EventMapperImpl::fromSession).collect(Collectors.toList());
        return sessionDtoList;
    }

    @Override
    public SessionDto getSession(Long id) throws NotFoundException {
        Session session =
                sessionRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("Session Not found"));
        SessionDto sessionDto = EventMapperImpl.fromSession(session);
        if (session.getVenue() != null) {
            VenueDto venueDto = EventMapperImpl.fromVenue(session.getVenue());
            sessionDto.setVenueDto(venueDto);
        }
        if (session.getEvents() != null) {
            List<EventDto> eventDtos =
                    session.getEvents().stream()
                            .map(EventMapperImpl::fromEvent)
                            .collect(Collectors.toList());
            sessionDto.setEventDtos(eventDtos);
        }
        return sessionDto;
    }
@Override
    public SessionDto updateSession(SessionDto sessionDto) {
        log.info("Updating Session");
        Session session = EventMapperImpl.fromSessionDTO(sessionDto);
        Session savedSession = sessionRepository.save(session);
        return EventMapperImpl.fromSession(savedSession);
    }


  /* @Override
   @Transactional
   public SessionDto updateSession(SessionDto sessionDto) throws NotFoundException {
       log.info("Updating Session");
       Long sessionId = sessionDto.getSessionId();
       Session exist = sessionRepository.findById(sessionId)
               .orElseThrow(() -> new NotFoundException("Session Not found"));
       Session updatedSession = EventMapperImpl.fromSessionDTO(sessionDto);
       if (sessionDto.getVenueDto() != null) {
           Long venueId = sessionDto.getVenueDto().getVenueId();
           Venue venue = venueRepository.findById(venueId)
                   .orElseThrow(() -> new NotFoundException("Venue not found"));
           updatedSession.setVenue(venue);
       } else {
           updatedSession.setVenue(null);
       }
       Session savedSession = sessionRepository.save(updatedSession);
       return EventMapperImpl.fromSession(savedSession);
   }*/


    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
