package tn.dkSoft.MyTicket.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tn.dkSoft.MyTicket.dto.*;
import tn.dkSoft.MyTicket.model.*;

@Service
public class EventMapperImpl {

    public static Session fromSessionDTO(SessionDto sessionDto) {
        Session session = new Session();
        BeanUtils.copyProperties(sessionDto, session);
        return session;
    }

    public static SessionDto fromSession(Session session) {
        SessionDto sessionDto = new SessionDto();
        BeanUtils.copyProperties(session, sessionDto);
        return sessionDto;
    }

    public static Venue fromVenueDTO(VenueDto venueDto) {
        Venue venue = new Venue();
        BeanUtils.copyProperties(venueDto, venue);
        return venue;
    }

    public static VenueDto fromVenue(Venue venue) {
        VenueDto venueDto = new VenueDto();
        try {
            BeanUtils.copyProperties(venue, venueDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return venueDto;
    }

    public static Event fromEventDto(EventDto eventDto) {
        Event event = new Event();
        BeanUtils.copyProperties(eventDto, event);
        return event;
    }

    public static EventDto fromEvent(Event event) {
        EventDto eventDto = new EventDto();

        BeanUtils.copyProperties(event, eventDto);

        return eventDto;
    }

    public static Tickets fromTicketDto(TicketsDto ticketsDto) {
        Tickets tickets = new Tickets();
        BeanUtils.copyProperties(ticketsDto, tickets);
        return tickets;
    }

    public static TicketsDto fromTicket(Tickets tickets) {
        TicketsDto ticketsDto = new TicketsDto();
        BeanUtils.copyProperties(tickets, ticketsDto);

        return ticketsDto;
    }

    public static Cart fromCartDto(CartDto cartDto) {
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartDto, cart);
        return cart;
    }

    public static CartDto fromCart(Cart cart) {
        CartDto cartDto = new CartDto ();
        BeanUtils.copyProperties(cart, cartDto);
        return cartDto;
    }

    public static CartItem fromCartItemDto(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(cartItemDto, cartItem);
        return cartItem;
    }

    public static CartItemDto fromCartItem(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto ();
        BeanUtils.copyProperties(cartItem, cartItemDto);
        return cartItemDto;
    }

    public static User fromUserDto(UserDto userDto) {
        User user = new User ();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto ();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }




}
