package tn.dkSoft.MyTicket.dto;

import java.io.Serializable;
import lombok.*;

@Data
public class VenueDto implements Serializable {
    private Long venueId;
    private String venueName;
    private long capacity;
    private boolean status;
}
