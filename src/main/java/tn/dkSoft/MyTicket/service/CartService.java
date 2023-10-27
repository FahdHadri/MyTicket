package tn.dkSoft.MyTicket.service;


import tn.dkSoft.MyTicket.dto.CartDto;

import java.util.List;

public interface CartService {

   CartDto addTicketToCart(Long cartId, Long ticketId, Integer quantity) throws Exception;

    List<CartDto> getAllCarts() throws Exception;

    CartDto getCartById(Long cartId) throws Exception;

    void updateTicketInCarts(Long cartId, Long ticketId);

    CartDto updateTicketQuantityInCart(Long cartId, Long ticketId, Integer quantity) throws Exception;

    String deleteTicketFromCart(Long cartId, Long ticketId);
}
