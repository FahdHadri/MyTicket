package tn.dkSoft.MyTicket.dto;

import lombok.*;
import tn.dkSoft.MyTicket.enums.TicketCategory;

import java.util.UUID;

@Data
public class TicketsDto {
    private Long id;
    private int codeTicket;
    private TicketCategory ticketCat;
    private float price;
    private long quantity;
    private String imgUrlTicket;
    private EventDto eventId;
}
