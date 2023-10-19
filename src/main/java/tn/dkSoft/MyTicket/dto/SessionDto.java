package tn.dkSoft.MyTicket.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.*;

@Data
public class SessionDto implements Serializable {
    private Long sessionId;
    private String sessionName;
    private Date startDate;
    private Date finishDate;
    private String imgUrl;
    private VenueDto venueDto;
    private List<EventDto>eventDtos;

}
