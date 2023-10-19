package tn.dkSoft.MyTicket.dto;

import lombok.*;
import tn.dkSoft.MyTicket.model.Venue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SessionDto implements Serializable {
    private Long sessionId;
    private String sessionName;
    private Date startDate;
    private Date finishDate;
    private String imgUrl;
    private Long venueId;
    private List<EventDto>eventDtoList;





}
