package tn.dkSoft.MyTicket.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "venue_id", unique = true)
    private Long venueId;
    private String venueName;
    private long capacity;
    private boolean status;
    @OneToOne(mappedBy = "venue")
    private Session session;

    public Venue(String venueName, long capacity, boolean status, Session session) {
        this.venueName = venueName;
        this.capacity = capacity;
        this.status = status;
        this.session = session;
    }
}

