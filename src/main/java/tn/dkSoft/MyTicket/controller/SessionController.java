package tn.dkSoft.MyTicket.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.exceptions.SessionNotFoundException;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.repository.SessionRepository;
import tn.dkSoft.MyTicket.service.SessionService;
import tn.dkSoft.MyTicket.validator.SessionValidator;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/session")
@AllArgsConstructor
@Lazy
@Service
public class SessionController {
    private SessionService sessionService;


    @PostMapping("/save")
    public SessionDto saveSession(@RequestBody SessionDto sessionDto) {

        return sessionService.saveSession ( sessionDto );
    }

    @GetMapping("/all")
    public List<SessionDto> sessions() {
        return sessionService.listSessions ();
    }

    @GetMapping("/search")
    public List<SessionDto> searchSessions(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return sessionService.searchSession ( "%" + keyword + "%" );
    }

    @GetMapping("/{id}")

    public SessionDto getSession(@PathVariable(name = "id") Long id) throws SessionNotFoundException {
        return sessionService.getSession ( id );
    }

    @PutMapping("/{id}")
    public SessionDto updateSession(@PathVariable Long id, @RequestBody SessionDto sessionDto){
        sessionDto.setSessionId (id);
        return sessionService.updateSession (sessionDto);
    }
    @DeleteMapping("/{id}")

    public void deleteSession(@PathVariable Long id){
        sessionService.deleteSession (id);
    }
}

