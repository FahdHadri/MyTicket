package tn.dkSoft.MyTicket.dto;

import java.io.Serializable;
import lombok.*;

@Data
public class VenueDto {
    private Long venueId;
    private String venueName;
    private long capacity;
}
