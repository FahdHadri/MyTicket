package tn.dkSoft.MyTicket.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import tn.dkSoft.MyTicket.enums.EventCategorie;
import java.util.Date;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Lazy
@Table(name = "event")

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "eventId", unique = true)
    private Long eventId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date dateEvent;

    @Enumerated(EnumType.STRING)
    private EventCategorie cat;
    @JsonIgnore
    @ManyToOne
    private Session session;

    @Column(nullable = false)
    private boolean available;
@OneToMany(mappedBy = "event" , fetch = FetchType.LAZY)
    private List<Tickets>ticketsList;

    public Event(String description, Date dateEvent, EventCategorie cat, Session session, boolean available, List<Tickets> ticketsList) {
        this.description = description;
        this.dateEvent = dateEvent;
        this.cat = cat;
        this.session = session;
        this.available = available;
        this.ticketsList = ticketsList;
    }
}





