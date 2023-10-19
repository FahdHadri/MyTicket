package tn.dkSoft.MyTicket.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.exceptions.EventNotFoundException;
import tn.dkSoft.MyTicket.service.EventService;

@RestController
@RequestMapping("/event")
@Lazy
@RequiredArgsConstructor
@Service
public class EventController {
    private final EventService eventService;

    @PostMapping("/save")
    public EventDto saveEvent(@RequestBody EventDto eventDto) {
        return eventService.saveEvent(eventDto);
    }

    @GetMapping("/all")
    public List<EventDto> events() {
        return eventService.listEvent();
    }

    @GetMapping("/search")
    public List<EventDto> searchEvent(
            @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return eventService.searchEvent("%" + keyword + "%");
    }

    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable(name = "id") Long id) throws EventNotFoundException {
        return eventService.getEvent(id);
    }

    /**
     * @PutMapping("/{id}") public EventDto updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto){
     * eventDto.setEventId (id);
     * return eventService.updateEvent (eventDto);
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(
            @PathVariable Long id, @RequestBody EventDto updatedEventDto) {
        EventDto updatedEvent = eventService.updateEvent(id, updatedEventDto);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
