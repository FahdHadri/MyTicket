package tn.dkSoft.MyTicket.dto;

import java.io.Serializable;
import lombok.*;
import tn.dkSoft.MyTicket.enums.TicketCategory;

@Data
public class TicketsDto implements Serializable {
    private Long id;
    private Long codeTicket;
    private TicketCategory ticketCat;
    private float price;
    private long quantity;
    private String imgUrlTicket;
    private EventDto eventId;
}
