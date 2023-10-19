package tn.dkSoft.MyTicket.service;

import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.exceptions.SessionNotFoundException;

import java.util.List;

public interface SessionServiceInterface {
 SessionDto saveSession(SessionDto sessionDto);

        List<SessionDto> listSessions();

        List<SessionDto> searchSession(String keyword);

        SessionDto getSession(Long id) throws SessionNotFoundException;

        SessionDto updateSession(SessionDto sessionDto);

        void deleteSession(Long id);
    }
