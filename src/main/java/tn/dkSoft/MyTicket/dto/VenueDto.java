package tn.dkSoft.MyTicket.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class VenueDto implements Serializable {
    private Long venueId;
    private String venueName;
    private long capacity;
    private boolean status;



}
