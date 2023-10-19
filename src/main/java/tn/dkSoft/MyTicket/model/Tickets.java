package tn.dkSoft.MyTicket.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.dkSoft.MyTicket.enums.TicketCategorie;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "tickets")
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    private Long code_ticket;

    @Enumerated(EnumType.STRING)
    private TicketCategorie ticketCat;

    private float price;

    private long quantity;

    private String imgUrlTicket;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;

    public Tickets(Long code_ticket, TicketCategorie ticketCat, float price, long quantity, String imgUrlTicket, Event event) {
        this.code_ticket = code_ticket;
        this.ticketCat = ticketCat;
        this.price = price;
        this.quantity = quantity;
        this.imgUrlTicket = imgUrlTicket;
        this.event = event;
    }
}

