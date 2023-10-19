package tn.dkSoft.MyTicket.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.beans.BeanUtils;
import tn.dkSoft.MyTicket.enums.TicketCategorie;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.model.Tickets;

import java.io.Serializable;

@Data
public class TicketsDto implements Serializable {
    private Long id;
    private Long codeTicket;
    private TicketCategorie ticketCat;
    private float price;
    private long quantity;
    private String imgUrlTicket;
    private EventDto eventId;

}
