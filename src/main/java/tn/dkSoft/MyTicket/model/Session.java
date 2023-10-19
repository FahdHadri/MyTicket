package tn.dkSoft.MyTicket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Lazy
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "session_id", unique = true)

    private Long sessionId;

    @Column(nullable = false)
    private String sessionName;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date finishDate;

    @Column(nullable = false)
    private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue")  // Adjust the column name as needed
    private Venue venue;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
@OneToMany(mappedBy = "session")
    private List<Event>events;

    public Session(String sessionName, Date startDate, Date finishDate, String imgUrl, Venue venue, List<Event> events) {
        this.sessionName = sessionName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.imgUrl = imgUrl;
        this.venue = venue;
        this.events = events;
    }
}

