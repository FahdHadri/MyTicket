package tn.dkSoft.MyTicket.dto;

import lombok.*;
import tn.dkSoft.MyTicket.enums.TicketCategory;

@Data
public class TicketsDto {
    private Long id;
    private Long codeTicket;
    private TicketCategory ticketCat;
    private float price;
    private long quantity;
    private String imgUrlTicket;
    private Long eventId;
}
