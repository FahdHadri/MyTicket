package tn.dkSoft.MyTicket.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.service.SessionServiceInterface;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Lazy
@Service
public class SessionController {
    private final SessionServiceInterface sessionService;

    @PostMapping("/save")
    public SessionDto saveSession(@RequestBody SessionDto sessionDto) {
        return sessionService.saveSession(sessionDto);
    }

    @GetMapping("/all")
    public List<SessionDto> sessions() {
        return sessionService.listSessions();
    }

    @GetMapping("/search")
    public List<SessionDto> searchSessions(
            @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return sessionService.searchSession("%" + keyword + "%");
    }

    @GetMapping("/{id}")
    public SessionDto getSession(@PathVariable(name = "id") Long id)
            throws NotFoundException {
        return sessionService.getSession(id);
    }

    @PutMapping("/{id}")
    public SessionDto updateSession(@PathVariable Long id, @RequestBody SessionDto sessionDto) {
        sessionDto.setSessionId(id);
        return sessionService.updateSession(sessionDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
    }
}
