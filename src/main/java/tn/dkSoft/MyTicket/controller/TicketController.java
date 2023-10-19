package tn.dkSoft.MyTicket.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.service.TicketServiceInterface;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
@Lazy
@Service
public class TicketController {
    private final TicketServiceInterface ticketsService;

    @PostMapping("/save")
    public TicketsDto saveTicket(@RequestBody TicketsDto ticketsDto) {
        return ticketsService.saveTickets(ticketsDto);
    }

    @GetMapping("/all")
    public List<TicketsDto> tickets() {
        return ticketsService.listTickets();
    }

    @GetMapping("/search")
    public List<TicketsDto> searchTicket(
            @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return ticketsService.searchTicket("%" + keyword + "%");
    }

    @GetMapping("/{id}")
    public TicketsDto getTicket(@PathVariable(name = "id") Long id) throws NotFoundException {
        return ticketsService.getTickets(id);
    }

    @PutMapping("/{id}")
    public TicketsDto updateTicket(@PathVariable Long id, @RequestBody TicketsDto ticketsDto) {
        ticketsDto.setId(id);
        return ticketsService.updateTickets(ticketsDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketsService.deleteTickets(id);
    }
}
