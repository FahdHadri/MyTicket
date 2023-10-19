package tn.dkSoft.MyTicket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.dkSoft.MyTicket.enums.TicketCategory;

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
    private TicketCategory ticketCat;

    private float price;

    private long quantity;

    private String imgUrlTicket;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;
}
