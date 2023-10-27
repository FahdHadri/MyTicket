package tn.dkSoft.MyTicket.dto;

import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@RequiredArgsConstructor
public class CartItemDto  implements Serializable {

    private Long cartItemId;
    private CartDto cart;
    private TicketsDto ticketsDto;
    private Integer quantity;
    private double ticketPrice;
}
