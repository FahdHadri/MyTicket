package tn.dkSoft.MyTicket.dto;

import lombok.*;

@Data
@Setter
@Getter
@RequiredArgsConstructor
public class VenueDto {
    private Long venueId;
    private String venueName;
    private long capacity;
}
