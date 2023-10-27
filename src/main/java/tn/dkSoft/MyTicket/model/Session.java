package tn.dkSoft.MyTicket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.*;
import org.springframework.context.annotation.Lazy;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Lazy
@Table(name = "session")
public class Session implements Serializable {

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

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "session")
    private Venue venueId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "session")
    private List<Event> events;


}
