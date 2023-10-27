package tn.dkSoft.MyTicket.service.Impl;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.dkSoft.MyTicket.dto.CartDto;
import tn.dkSoft.MyTicket.dto.CartItemDto;
import tn.dkSoft.MyTicket.exceptions.NotFoundException;
import tn.dkSoft.MyTicket.mappers.EventMapperImpl;
import tn.dkSoft.MyTicket.model.Cart;
import tn.dkSoft.MyTicket.model.CartItem;
import tn.dkSoft.MyTicket.model.Tickets;
import tn.dkSoft.MyTicket.repository.CartItemRepository;
import tn.dkSoft.MyTicket.repository.CartRepository;
import tn.dkSoft.MyTicket.repository.TicketRepository;
import tn.dkSoft.MyTicket.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Slf4j
@Transactional
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    public CartRepository cartRepository;
    @Autowired
    public CartItemRepository cartItemRepository;
    @Autowired
    public TicketRepository ticketRepository;
    @Override
    public CartDto addTicketToCart(Long cartId, Long ticketId, Integer quantity) throws Exception {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        Tickets tickets = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));

        if (tickets.getQuantity() == 0) {
            throw new Exception(tickets.getTicketCat() + " is not available");
        }

        if (tickets.getQuantity() < quantity) {
            throw new Exception("Please, make an order of the " + tickets.getTicketCat()
                    + " less than or equal to the quantity " + tickets.getQuantity() + ".");
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setTickets(tickets);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setTicketPrice(tickets.getPrice());

        cartItemRepository.save(newCartItem);

        tickets.setQuantity(tickets.getQuantity() - quantity);
        ticketRepository.save(tickets);

        // Update the total price in the cart
        cart.setTotalPrice(cart.getTotalPrice() + (tickets.getPrice() * quantity));
        cartRepository.save(cart);

        // Create the CartDto with updated cart information
        CartDto cartDto = EventMapperImpl.fromCart(cart);

        // Retrieve cart items, including the newly added item
        List<CartItemDto> cartItemDtos = cart.getCartItems().stream()
                .map(cartItemDto -> EventMapperImpl.fromCartItem(cartItemDto))
                .collect(Collectors.toList());

        // Add the newly added item to the list of cart items
        cartItemDtos.add(EventMapperImpl.fromCartItem(newCartItem));

        cartDto.setCartItemDtos(cartItemDtos);

        return cartDto;
    }
    @Override
    public List<CartDto> getAllCarts() throws Exception {
        List<Cart> carts = cartRepository.findAll();

        if (carts.isEmpty()) {
            throw new Exception("No cart exists");
        }

        List<CartDto> cartDTOs = new ArrayList<> ();

        for (Cart cart : carts) {
            CartDto cartDTO = EventMapperImpl.fromCart(cart);
            List<CartItemDto> cartItemDtos = cart.getCartItems().stream()
                    .map(cartItemDto -> EventMapperImpl.fromCartItem(cartItemDto))
                    .collect(Collectors.toList());
            cartDTO.setCartItemDtos(cartItemDtos);
            cartDTOs.add(cartDTO);

        }

        return cartDTOs;
    }

@Override
    public CartDto getCartById(Long cartId) throws Exception {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (!optionalCart.isPresent()) {
            throw new Exception("Cart not found with ID " + cartId);
        }

        Cart cart = optionalCart.get();
        CartDto cartDTO = EventMapperImpl.fromCart(cart);

        List<CartItemDto> cartItemDtos = cart.getCartItems().stream()
                .map(cartItemDto -> EventMapperImpl.fromCartItem(cartItemDto))
                .collect(Collectors.toList());
        cartDTO.setCartItemDtos(cartItemDtos);

        return cartDTO;
    }

    @Override
    public void updateTicketInCarts(Long cartId, Long ticketId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("CartId" + cartId));

        Tickets ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException ("Ticket ticketId" +ticketId));

        CartItem cartItem = cartItemRepository.findCartItemByTicketIdAndCartId (cartId, ticketId);

        if (cartItem == null) {
            throw new NotFoundException ("Ticket " + ticket.getTicketCat() + " not available in the cart!!!");
        }

        // Calculate the difference in prices before and after the update
        double previousCartItemPrice = cartItem.getTicketPrice() * cartItem.getQuantity();
        double newCartItemPrice = ticket.getPrice() * cartItem.getQuantity();
        double priceDifference = newCartItemPrice - previousCartItemPrice;

        // Update the ticket price in the cart item
        cartItem.setTicketPrice(ticket.getPrice());

        // Update the cart's total price by adding the price difference
        cart.setTotalPrice(cart.getTotalPrice() + priceDifference);

        // Save the updated cart item
        cartItem = cartItemRepository.save(cartItem);

        // Save the updated cart
        cartRepository.save(cart);
    }

    @Override
    public CartDto updateTicketQuantityInCart(Long cartId, Long ticketId, Integer quantity) throws Exception {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart Not found " + cartId));

        Tickets ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket not found " + ticketId));

        if (ticket.getQuantity() == 0) {
            throw new Exception(ticket.getTicketCat() + " is not available");
        }

        if (ticket.getQuantity() < quantity) {
            throw new Exception("Please, make an order of the " + ticket.getTicketCat()
                    + " less than or equal to the quantity " + ticket.getQuantity() + ".");
        }

        CartItem cartItem = cartItemRepository.findCartItemByTicketIdAndCartId (cartId, ticketId);

        if (cartItem == null) {
            throw new Exception("Ticket " + ticket.getTicketCat() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getTicketPrice() * cartItem.getQuantity());

        ticket.setQuantity(ticket.getQuantity() + cartItem.getQuantity() - quantity);

        cartItem.setTicketPrice(ticket.getPrice());
        cartItem.setQuantity(quantity);

        cart.setTotalPrice(cartPrice + (cartItem.getTicketPrice() * quantity));

        cartItem = cartItemRepository.save(cartItem);

        CartDto cartDto = EventMapperImpl.fromCart(cart);

        // Optionally, if you want to retrieve the updated cart items and set them in the CartDto:
        List<CartItemDto> cartItemDtos = cart.getCartItems().stream()
                .map(cartItemDto -> EventMapperImpl.fromCartItem(cartItemDto))
                .collect(Collectors.toList());

        cartDto.setCartItemDtos(cartItemDtos);

        return cartDto;
    }

    @Override
    public String deleteTicketFromCart(Long cartId, Long ticketId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException ("Cart cartId" +cartId));

        CartItem cartItem = cartItemRepository.findCartItemByTicketIdAndCartId(cartId, ticketId);

        if (cartItem == null) {
            throw new NotFoundException ("Ticket ticketId" +ticketId);
        }

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getTicketPrice() * cartItem.getQuantity()));

        Tickets ticket = cartItem.getTickets ();
        ticket.setQuantity(ticket.getQuantity() + cartItem.getQuantity());

        cartItemRepository.deleteCartItemByTicketIdAndCartId(cartId, ticketId);

        return "Ticket " + cartItem.getTickets ().getTicketCat() + " removed from the cart !!!";
    }

}






