package tn.dkSoft.MyTicket.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import tn.dkSoft.MyTicket.enums.EventCategorie;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class EventDto implements Serializable {
    private Long eventId;
    private String description;
    private Date dateEvent;
    private EventCategorie cat;
    private boolean available;
    private Long sessionId;


}
