package tn.dkSoft.MyTicket.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.*;
import tn.dkSoft.MyTicket.enums.EventCategory;

@Data
public class EventDto implements Serializable {
    private Long eventId;
    private String description;
    private Date dateEvent;
    private EventCategory cat;
    private boolean available;
    private Long sessionId;
}
