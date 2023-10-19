package tn.dkSoft.MyTicket.mappers;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tn.dkSoft.MyTicket.dto.EventDto;
import tn.dkSoft.MyTicket.dto.SessionDto;
import tn.dkSoft.MyTicket.dto.TicketsDto;
import tn.dkSoft.MyTicket.dto.VenueDto;
import tn.dkSoft.MyTicket.model.Event;
import tn.dkSoft.MyTicket.model.Session;
import tn.dkSoft.MyTicket.model.Tickets;
import tn.dkSoft.MyTicket.model.Venue;

@Service
public class EventMapperImpl {

    public static Session fromSessionDTO(SessionDto sessionDto) {
        Session session = new Session();
        BeanUtils.copyProperties (sessionDto,session);
        return session;
    }

    public static SessionDto fromSession(Session session) {
        SessionDto sessionDto = new SessionDto ();
            BeanUtils.copyProperties(session, sessionDto);
        return sessionDto;
    }

    public static Venue fromVenueDTO(VenueDto venueDto) {
        Venue venue = new Venue();
        BeanUtils.copyProperties (venueDto,venue);
        return venue;
    }

    public static VenueDto fromVenue(Venue venue) {
        VenueDto venueDto = new VenueDto ();
        try {
            BeanUtils.copyProperties(venue, venueDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return venueDto;
    }

    public static Event fromEventDto(EventDto eventDto) {
        Event event = new Event();
        BeanUtils.copyProperties (eventDto,event);
        return event;
    }

    public static EventDto fromEvent(Event event) {
        EventDto eventDto = new EventDto ();

            BeanUtils.copyProperties(event, eventDto);

        return eventDto;
    }

    public static Tickets fromTicketDto(TicketsDto ticketsDto) {
        Tickets tickets = new Tickets ();
        BeanUtils.copyProperties (ticketsDto,tickets);
        return tickets;
    }

  public static TicketsDto fromTicket(Tickets tickets) {
        TicketsDto ticketsDto = new TicketsDto ();
   BeanUtils.copyProperties(tickets, ticketsDto);

        return ticketsDto;
    }


}
