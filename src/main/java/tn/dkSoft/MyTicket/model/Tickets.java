package tn.dkSoft.MyTicket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import tn.dkSoft.MyTicket.enums.TicketCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Table(name = "tickets")
public class Tickets implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "code_ticket", unique = true, nullable = false)
    private int codeTicket;

    @Enumerated(EnumType.STRING)
    private TicketCategory ticketCat;

    private float price;

    private long quantity;

    private String imgUrlTicket;

    @OneToMany(mappedBy = "tickets", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private List<CartItem> products = new ArrayList<> ();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event")
    private Event event;


}