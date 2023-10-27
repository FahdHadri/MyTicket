package tn.dkSoft.MyTicket.dto;

import java.util.Date;
import lombok.*;
import tn.dkSoft.MyTicket.enums.EventCategory;

@Data
@Setter
@Getter
@RequiredArgsConstructor
public class EventDto {
    private Long eventId;
    private String description;
    private Date dateEvent;
    private EventCategory cat;
    private boolean available;
    private SessionDto sessionDto;
}
