package tn.dkSoft.MyTicket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;
import org.springframework.context.annotation.Lazy;
import tn.dkSoft.MyTicket.enums.EventCategory;

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
    private EventCategory cat;

    @JsonIgnore @ManyToOne private Session session;

    @Column(nullable = false)
    private boolean available;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Tickets> ticketsList;
}
